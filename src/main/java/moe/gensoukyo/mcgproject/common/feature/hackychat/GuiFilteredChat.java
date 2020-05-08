package moe.gensoukyo.mcgproject.common.feature.hackychat;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 带有信息过滤的原版聊天窗口
 * @author drzzm32
 * @date 2020/5/7
 */
public class GuiFilteredChat extends GuiNewChat {

    public final Minecraft mc;

    public boolean canFilter = false;

    public static List<Pattern> patterns = new ArrayList<>();

    static { // 这里添加正则
        patterns.add(Pattern.compile("(.*\\[.*@.*].*)"));
    }

    public static GuiFilteredChat INSTANCE = null;

    public static void replaceChatGui(Minecraft mc) {
        Field field = ObfuscationReflectionHelper.findField(GuiIngame.class, "field_73840_e");
        try {
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            GuiFilteredChat gui = new GuiFilteredChat(mc);
            field.set(mc.ingameGUI, gui);
            MCGProject.logger.info("[GFC] GUI has replaced!");
            INSTANCE = gui;
        } catch (Exception ex) {
            MCGProject.logger.info(ex.toString());
        }
    }

    public GuiFilteredChat(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    public List<ChatLine> chatLines() {
        Field field = ObfuscationReflectionHelper.findField(GuiNewChat.class, "field_146252_h");
        try {
            Object obj = field.get(this);
            return (List<ChatLine>) obj;
        } catch (Exception ex) {
            MCGProject.logger.info(ex.toString());
        }
        return null;
    }

    public List<ChatLine> drawnChatLines() {
        Field field = ObfuscationReflectionHelper.findField(GuiNewChat.class, "field_146253_i");
        try {
            Object obj = field.get(this);
            return (List<ChatLine>) obj;
        } catch (Exception ex) {
            MCGProject.logger.info(ex.toString());
        }
        return null;
    }

    public int scrollPos() {
        Field field = ObfuscationReflectionHelper.findField(GuiNewChat.class, "field_146250_j");
        try {
            return field.getInt(this);
        } catch (Exception ex) {
            MCGProject.logger.info(ex.toString());
        }
        return 0;
    }

    public void isScrolled(boolean val) {
        ObfuscationReflectionHelper.setPrivateValue(GuiNewChat.class, this, val, "field_146251_k");
    }

    @Override
    public void printChatMessage(@Nonnull ITextComponent component) {
        printChatMessageWithOptionalDeletion(component, 0);
    }

    @Override
    public void printChatMessageWithOptionalDeletion(@Nonnull ITextComponent component, int chatLineID) {
        this.setChatLine(component, chatLineID, this.mc.ingameGUI.getUpdateCounter(), false);
    }

    @Override
    public void refreshChat() {
        this.drawnChatLines().clear();
        this.resetScroll();

        for(int i = this.chatLines().size() - 1; i >= 0; --i) {
            ChatLine line = this.chatLines().get(i);
            this.setChatLine(line.getChatComponent(), line.getChatLineID(), line.getUpdatedCounter(), true);
        }
    }

    public void setChatLine(ITextComponent component, int chatLineID, int counter, boolean control) {
        if (chatLineID != 0) {
            this.deleteChatLine(chatLineID);
        }

        if (canFilter) {
            for (Pattern p : patterns)
                if (p.matcher(component.getUnformattedText()).matches())
                    return;
        }

        int width = MathHelper.floor((float)this.getChatWidth() / this.getChatScale());
        List<ITextComponent> list = GuiUtilRenderComponents.splitText(component, width, this.mc.fontRenderer, false, false);
        boolean isOpen = this.getChatOpen();

        ITextComponent itc;
        for(Iterator<ITextComponent> it = list.iterator(); it.hasNext(); this.drawnChatLines().add(0, new ChatLine(counter, itc, chatLineID))) {
            itc = it.next();
            if (isOpen && this.scrollPos() > 0) {
                this.isScrolled(true);
                this.scroll(1);
            }
        }

        while(this.drawnChatLines().size() > 100) {
            this.drawnChatLines().remove(this.drawnChatLines().size() - 1);
        }

        if (!control) {
            this.chatLines().add(0, new ChatLine(counter, component, chatLineID));

            while(this.chatLines().size() > 100) {
                this.chatLines().remove(this.chatLines().size() - 1);
            }
        }

    }

}
