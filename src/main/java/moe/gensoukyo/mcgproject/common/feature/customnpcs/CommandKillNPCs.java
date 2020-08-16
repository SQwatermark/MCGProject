package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import noppes.npcs.entity.EntityCustomNpc;

import java.util.List;

public class CommandKillNPCs extends CommandBase {

    @Override
    public String getName() {
        return "killNpc";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "用法：/killNpc 名字 半径，如果要删除会重生的NPC，使用/killNpc 名字 半径 d";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        } else {
            String name = args[0];
            int r = 5;
            boolean delete = false;
            if (args.length > 2 && args[2].equals("d")) delete = true;
            try {
                r = Integer.parseInt(args[1]);
            } catch (Exception ignored) {
                sender.sendMessage(new TextComponentString("错误的数字，半径自动设定为5"));
            }
            if (sender instanceof EntityPlayer) {
                int i = 0;
                List<EntityCustomNpc> list = ((EntityPlayer) sender).world.getEntitiesWithinAABB(EntityCustomNpc.class, new AxisAlignedBB(sender.getPosition()).grow(r));
                for (EntityCustomNpc customNpc : list) {
                    if (customNpc.getName().equals(name)) {
                        if (delete) {
                            customNpc.delete();
                        } else {
                            customNpc.setDead();
                        }
                        i++;
                    }
                }
                sender.sendMessage(new TextComponentString("清除了" + i + "个" + "[" + name + "]"));
            }
        }
    }
}
