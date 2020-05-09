package moe.gensoukyo.mcgproject.common.feature.hackychat;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CmdFilteredChat implements ICommand {

    @Override
    @Nonnull
    public String getName() {
        return "chatFilter";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "just type /cf";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("cf", "chatFil");
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (sender instanceof EntityPlayer && GuiFilteredChat.INSTANCE != null) {
            EntityPlayer player = (EntityPlayer) sender;
            GuiFilteredChat.INSTANCE.canFilter = !GuiFilteredChat.INSTANCE.canFilter;
            player.sendMessage(new TextComponentString(TextFormatting.GRAY + "chatFilter = " + GuiFilteredChat.INSTANCE.canFilter));
        }
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos pos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int i) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

}
