package moe.gensoukyo.mcgproject.common.tileentity;

import com.google.common.collect.Lists;
import moe.gensoukyo.mcgproject.common.network.NetworkWrapper;
import moe.gensoukyo.mcgproject.common.network.StickerRefreshPacket;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author drzzm32
 * @date 2020/4/12
 */
public class TileSticker extends AbstractTileEntity implements ITickable {

    public static class RefreshCommand extends CommandBase {

        public RefreshCommand(){
            aliases = Lists.newArrayList("stickerRefresh");
        }

        private final List<String> aliases;

        @Override
        @Nonnull
        public String getName() {
            return "stickerRefresh";
        }

        @Override
        @Nonnull
        public String getUsage(@Nonnull ICommandSender sender) {
            return " execute \"/stickerRefresh\" to refresh all of the stickers";
        }

        @Override
        @Nonnull
        public List<String> getAliases() {
            return aliases;
        }

        @Override
        public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
            NetworkWrapper.INSTANCE.sendToAll(new StickerRefreshPacket(true));
            sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Sticker refreshed"));
        }

        @Override
        public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                if (server.isDedicatedServer()) {
                    UserListOps listOps = server.getPlayerList().getOppedPlayers();
                    ArrayList<String> list = Lists.newArrayList(listOps.getKeys());
                    return list.contains(player.getName());
                } else {
                    return player.isCreative();
                }
            }

            return false;
        }

        @Override
        @Nonnull
        public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
            return Collections.emptyList();
        }

    }

    public static final int MODEL_SINGLE = 0;
    public static final int MODEL_DOUBLE = 1;

    public static final int ANIMATE_NONE = 0;
    public static final int ANIMATE_LOOP = 1;           // 垂直长条帧动画
    public static final int ANIMATE_LOOP_INV = 2;       // 垂直长条帧动画（反向）
    public static final int ANIMATE_SCROLL_V = 3;       // 垂直滚动动画
    public static final int ANIMATE_SCROLL_V_INV = 4;   // 垂直滚动动画（反向）
    public static final int ANIMATE_SCROLL_H = 5;       // 水平滚动动画
    public static final int ANIMATE_SCROLL_H_INV = 6;   // 水平滚动动画（反向）

    @SideOnly(Side.CLIENT)
    public Object texture;

    public boolean shouldReload = false;

    public int frameCounter = 0;
    public double nowFrames = 0;
    public double x = 0, y = 0, u = 1, v = 1;
    public double px = 0, py = 0, pu = 1, pv = 1;

    public int model = MODEL_SINGLE;
    public int animate = ANIMATE_NONE;
    public String url = "null";
    public long color = 0xFFFFFFFFL;
    public double offsetX = 0, offsetY = 0, offsetZ = 0;
    public double rotateX = 0, rotateY = 0, rotateZ = 0;
    public double scaleX = 1, scaleY = 1, scaleZ = 1;
    public int width, height;
    public int frameWidth, frameHeight, frameTime, frameExtend;

    @Override
    public void refresh() {
        super.refresh();
        shouldReload = true;
        nowFrames = 0;
        frameCounter = 0;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public void fromNBT(NBTTagCompound tag) {
        model = tag.getInteger("model");
        animate = tag.getInteger("animate");
        url = tag.getString("url");
        color = tag.getLong("color");
        NBTTagList list = tag.getTagList("offset", 6);
        offsetX = list.getDoubleAt(0);
        offsetY = list.getDoubleAt(1);
        offsetZ = list.getDoubleAt(2);
        list = tag.getTagList("rotate", 6);
        rotateX = list.getDoubleAt(0);
        rotateY = list.getDoubleAt(1);
        rotateZ = list.getDoubleAt(2);
        list = tag.getTagList("scale", 6);
        scaleX = list.getDoubleAt(0);
        scaleY = list.getDoubleAt(1);
        scaleZ = list.getDoubleAt(2);
        if (scaleX <= 0) scaleX = 1; if (scaleY <= 0) scaleY = 1; if (scaleZ <= 0) scaleZ = 1;
        width = tag.getInteger("width");
        height = tag.getInteger("height");
        frameWidth = tag.getInteger("frameWidth");
        frameHeight = tag.getInteger("frameHeight");
        frameTime = tag.getInteger("frameTime");
        frameExtend = tag.getInteger("frameExtend");
    }

    @Nonnull
    @Override
    public NBTTagCompound toNBT(NBTTagCompound tag) {
        tag.setInteger("model", model);
        tag.setInteger("animate", animate);
        tag.setString("url", url);
        tag.setLong("color", color);
        NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagDouble(offsetX));
        list.appendTag(new NBTTagDouble(offsetY));
        list.appendTag(new NBTTagDouble(offsetZ));
        tag.setTag("offset", list);
        list = new NBTTagList();
        list.appendTag(new NBTTagDouble(rotateX));
        list.appendTag(new NBTTagDouble(rotateY));
        list.appendTag(new NBTTagDouble(rotateZ));
        tag.setTag("rotate", list);
        list = new NBTTagList();
        list.appendTag(new NBTTagDouble(scaleX));
        list.appendTag(new NBTTagDouble(scaleY));
        list.appendTag(new NBTTagDouble(scaleZ));
        tag.setTag("scale", list);
        tag.setInteger("width", width);
        tag.setInteger("height", height);
        tag.setInteger("frameWidth", frameWidth);
        tag.setInteger("frameHeight", frameHeight);
        tag.setInteger("frameTime", frameTime);
        tag.setInteger("frameExtend", frameExtend);

        return tag;
    }

    @Override
    public void update() {
        if (!world.isRemote)
            return;

        if (frameTime <= 0) frameTime = 1;
        if (frameExtend <= 0) frameExtend = 1;

        px = x; py = y; pu = u; pv = v;

        double totalFrames;
        switch (animate) {
            case ANIMATE_NONE:
                x = 0; y = 0;
                u = 1; v = 1;
                break;
            case ANIMATE_LOOP:
                totalFrames = (double) height / (double) frameHeight;
                x = 0;
                y = nowFrames / totalFrames;
                u = 1;
                v = 1 / totalFrames;
                if (frameCounter % frameTime == 0)
                    nowFrames += 1;
                if (nowFrames >= totalFrames) {
                    nowFrames = 0;
                    frameCounter = 0;
                }
                break;
            case ANIMATE_LOOP_INV:
                totalFrames = (double) height / (double) frameHeight;
                x = 0;
                y = 1 - 1 / totalFrames - nowFrames / totalFrames;
                u = 1;
                v = 1 / totalFrames;
                if (frameCounter % frameTime == 0)
                    nowFrames += 1;
                if (nowFrames >= totalFrames) {
                    nowFrames = 0;
                    frameCounter = 0;
                }
                break;
            case ANIMATE_SCROLL_V:
                totalFrames = (double) height / (double) frameHeight * frameExtend;
                x = 0;
                y = nowFrames / totalFrames;
                u = 1;
                v = (double) frameHeight / (double) height;
                if ((frameCounter / frameTime) % 2 == 0)
                    nowFrames += ((double) frameExtend / (double) frameTime);
                if (nowFrames > totalFrames - frameExtend) {
                    nowFrames = 0;
                    frameCounter = 0;
                }
                break;
            case ANIMATE_SCROLL_V_INV:
                totalFrames = (double) height / (double) frameHeight * frameExtend;
                x = 0;
                y = 1 - 1 / totalFrames * frameExtend - nowFrames / totalFrames;
                u = 1;
                v = (double) frameHeight / (double) height;
                if ((frameCounter / frameTime) % 2 == 0)
                    nowFrames += ((double) frameExtend / (double) frameTime);
                if (nowFrames > totalFrames - frameExtend) {
                    nowFrames = 0;
                    frameCounter = 0;
                }
                break;
            case ANIMATE_SCROLL_H:
                totalFrames = (double) width / (double) frameWidth * frameExtend;
                x = nowFrames / totalFrames;
                y = 0;
                u = (double) frameWidth / (double) width;
                v = 1;
                if ((frameCounter / frameTime) % 2 == 0)
                    nowFrames += ((double) frameExtend / (double) frameTime);
                if (nowFrames > totalFrames - frameExtend) {
                    nowFrames = 0;
                    frameCounter = 0;
                }
                break;
            case ANIMATE_SCROLL_H_INV:
                totalFrames = (double) width / (double) frameWidth * frameExtend;
                x = 1 - 1 / totalFrames * frameExtend - nowFrames / totalFrames;
                y = 0;
                u = (double) frameWidth / (double) width;
                v = 1;
                if ((frameCounter / frameTime) % 2 == 0)
                    nowFrames += ((double) frameExtend / (double) frameTime);
                if (nowFrames > totalFrames - frameExtend) {
                    nowFrames = 0;
                    frameCounter = 0;
                }
                break;
            default:
                break;
        }

        frameCounter += 1;
    }

}
