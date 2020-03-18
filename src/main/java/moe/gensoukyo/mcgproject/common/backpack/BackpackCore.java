package moe.gensoukyo.mcgproject.common.backpack;

import com.google.common.collect.Lists;
import moe.gensoukyo.mcgproject.common.network.BackpackPacket;
import moe.gensoukyo.mcgproject.common.network.NetworkWrapper;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author drzzm32
 * @date 2020/3/16
 * @apiNote 幻想箱/背包的核心类
 */
public class BackpackCore {

    public static final int COLUMN = 27;
    public static final int SIZE = 162;

    /**
     * @apiNote 这里用于分模式背包判断
     * @apiNote 不同模式下的背包需分别开启
     * */
    public static boolean openBackpack(World world, EntityPlayer player) {
        return openBackpack(world, player, player.getName(), player.isCreative() ? BackpackRepo.TYPE_CREATIVE : BackpackRepo.TYPE_DEFAULT);
    }

    public static boolean openBackpack(World world, EntityPlayer player, String id, String type) {
        if (!BackpackRepo.has(world, id, type))
            return false;
        Backpack backpack = new Backpack(world, id, type);
        if (!world.isRemote) {
            if (player instanceof EntityPlayerMP) {
                EntityPlayerMP mp = (EntityPlayerMP) player;
                if (mp.openContainer != mp.inventoryContainer) {
                    mp.closeScreen();
                }
                mp.getNextWindowId();
                NetworkWrapper.INSTANCE.sendTo(new BackpackPacket(mp.currentWindowId, mp.getName(), type, backpack), mp);
                mp.openContainer = new ContainerGensoChest(mp.inventory, backpack, mp);
                mp.openContainer.windowId = mp.currentWindowId;
                mp.openContainer.addListener(mp);
                MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(mp, mp.openContainer));
            }
        }
        return true;
    }

    public static class BackpackRepo {

        public static final String TYPE_DEFAULT = "default";
        public static final String TYPE_CREATIVE = "creative";

        static NonNullList<ItemStack> def() {
            return NonNullList.withSize(SIZE, ItemStack.EMPTY);
        }

        private static final String NBT_TAG = "mcg.backpack";

        public static class SaveData extends WorldSavedData {

            public LinkedHashMap<String, LinkedHashMap<String, NonNullList<ItemStack>>> backpacks;

            public SaveData(String mapName) {
                super(mapName);
                backpacks = new LinkedHashMap<>();
            }

            @Nonnull
            @Override
            public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound tagCompound) {
                if (backpacks.isEmpty())
                    return tagCompound;

                for (Map.Entry<String, LinkedHashMap<String, NonNullList<ItemStack>>> i : backpacks.entrySet()) {
                    NBTTagCompound tag = new NBTTagCompound();
                    for (Map.Entry<String, NonNullList<ItemStack>> j : i.getValue().entrySet()) {
                        NBTTagCompound pack = new NBTTagCompound();
                        ItemStackHelper.saveAllItems(pack, j.getValue());
                        tag.setTag(j.getKey(), pack);
                    }
                    tagCompound.setTag(i.getKey(), tag);
                }

                MCGProject.logger.info("[GB] " + "Backpacks saving...");

                return tagCompound;
            }

            @Override
            public void readFromNBT(@Nonnull NBTTagCompound tagCompound) {
                if (tagCompound.getKeySet().isEmpty())
                    return;

                MCGProject.logger.info("[GB] " + "Backpacks loading...");

                backpacks.clear();
                for (String id : tagCompound.getKeySet()) {
                    NBTTagCompound tag = tagCompound.getCompoundTag(id);
                    for (String type : tag.getKeySet()) {
                        NBTTagCompound pack = tag.getCompoundTag(type);
                        NonNullList<ItemStack> list = def();
                        ItemStackHelper.loadAllItems(pack, list);
                        if (!backpacks.containsKey(id))
                            backpacks.put(id, new LinkedHashMap<>());
                        backpacks.get(id).put(type, list);
                    }
                }
            }

        }

        private static SaveData getData(World world) {
            MapStorage storage = world.getPerWorldStorage();
            SaveData data = (SaveData) storage.getOrLoadData(SaveData.class, NBT_TAG);
            if (data == null) {
                data = new SaveData(NBT_TAG);
                storage.setData(NBT_TAG, data);
            }
            return data;
        }

        static String showBackpacksInfo(World world) {
            StringBuilder builder = new StringBuilder();
            SaveData data = getData(world);

            if (data.backpacks.isEmpty()) {
                builder.append("This world do not have any backpack.");
                return builder.toString();
            }

            builder.append("World [");
            builder.append(world.getWorldInfo().getWorldName());
            builder.append("] has ");
            builder.append(data.backpacks.size());
            builder.append(" backpack(s):\n");
            for (Map.Entry<String, LinkedHashMap<String, NonNullList<ItemStack>>> i : data.backpacks.entrySet()) {
                builder.append(i.getKey());
                builder.append(": ");
                for (Map.Entry<String, NonNullList<ItemStack>> j : i.getValue().entrySet()) {
                    builder.append(j.getKey());
                    builder.append("-");
                    builder.append(j.getValue().hashCode());
                    builder.append("; ");
                }
                builder.append("\n");
            }

            return builder.toString();
        }

        public static NonNullList<ItemStack> get(World world, String id, String type) {
            SaveData data = getData(world);
            if (!data.backpacks.containsKey(id))
                return NonNullList.create();
            if (!data.backpacks.get(id).containsKey(type))
                return NonNullList.create();
            return data.backpacks.get(id).get(type);
        }

        public static void put(World world, String id, String type, NonNullList<ItemStack> value) {
            SaveData data = getData(world);
            if (!data.backpacks.containsKey(id))
                data.backpacks.put(id, new LinkedHashMap<>());
            data.backpacks.get(id).put(type, value);
            data.markDirty();
        }

        public static void del(World world, String id) {
            SaveData data = getData(world);
            data.backpacks.remove(id);
            data.markDirty();
        }

        public static void del(World world, String id, String type) {
            SaveData data = getData(world);
            if (data.backpacks.containsKey(id)) {
                data.backpacks.get(id).remove(type);
                data.markDirty();
            }
        }

        public static boolean has(World world, String id, String type) {
            return !get(world, id, type).isEmpty();
        }

        public static void save(World world) {
            SaveData data = getData(world);
            data.markDirty();
        }

    }

    public static class Backpack implements IInventory {

        public static Backpack fromCompoundTag(String id, String type, NBTTagCompound tag) {
            Backpack backpack = new Backpack(id, type);
            ItemStackHelper.loadAllItems(tag, backpack.backpack);
            return backpack;
        }

        public NBTTagCompound toCompoundTag() {
            NBTTagCompound tag = new NBTTagCompound();
            ItemStackHelper.saveAllItems(tag, this.backpack);
            return tag;
        }

        public final String id;
        public final String type;
        public NonNullList<ItemStack> backpack;

        public Backpack(String id, String type) {
            this.id = id;
            this.type = type;
            backpack = BackpackRepo.def();
        }

        public Backpack(World world, String id, String type) {
            this.id = id;
            this.type = type;
            backpack = BackpackRepo.get(world, this.id, this.type);
        }
        
        @Override
        public int getSizeInventory() { return SIZE; }
        @Override
        public boolean isEmpty() {
            Iterator<ItemStack> it = backpack.iterator();
            ItemStack itemstack;
            do {
                if (!it.hasNext())
                    return true;
                itemstack = it.next();
            } while (itemstack.isEmpty());
            return false;
        }
        @Override
        @Nonnull
        public ItemStack getStackInSlot(int i) { return backpack.get(i); }
        @Override
        @Nonnull
        public ItemStack decrStackSize(int i, int j) {
            return ItemStackHelper.getAndSplit(backpack, i, j);
        }
        @Override
        @Nonnull
        public ItemStack removeStackFromSlot(int i) {
            ItemStack stack = backpack.get(i);
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                backpack.set(i, ItemStack.EMPTY);
                return stack;
            }
        }
        @Override
        public void setInventorySlotContents(int i, @Nonnull ItemStack stack) {
            backpack.set(i, stack);
            if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
                stack.setCount(this.getInventoryStackLimit());
            }
        }
        @Override
        public int getInventoryStackLimit() { return 64; }
        @Override
        public void markDirty() { }
        @Override
        public boolean isUsableByPlayer(@Nonnull EntityPlayer player) { return true; }
        @Override
        public void openInventory(@Nonnull EntityPlayer player) { }
        @Override
        public void closeInventory(@Nonnull EntityPlayer player) { }
        @Override
        public boolean isItemValidForSlot(int i, @Nonnull ItemStack itemStack) { return true; }
        @Override
        public int getField(int i) { return 0; }
        @Override
        public void setField(int i, int i1) { }
        @Override
        public int getFieldCount() { return 0; }
        @Override
        public void clear() { backpack.clear(); }
        @Override
        @Nonnull
        public String getName() { return "GensoBackpack"; }
        @Override
        public boolean hasCustomName() { return true; }
        @Override
        @Nonnull
        public ITextComponent getDisplayName() {
            return new TextComponentString(
                    TextFormatting.DARK_PURPLE + "GensoBackpack" +
                            TextFormatting.RESET + " - " +
                            TextFormatting.BOLD + id +
                            TextFormatting.RESET + " - " +
                            TextFormatting.DARK_AQUA + type.toUpperCase()
            );
        }
        
    }
    
    public static class BackpackCommand extends CommandBase {

        public BackpackCommand(){
            aliases = Lists.newArrayList("gb");
        }

        private final List<String> aliases;

        @Override
        @Nonnull
        public String getName() {
            return "gb";
        }

        @Override
        @Nonnull
        public String getUsage(@Nonnull ICommandSender sender) {
            return " execute \"/gb\" to open gensoBackpack (aka GB)";
        }

        @Override
        @Nonnull
        public List<String> getAliases() {
            return aliases;
        }

        @Override
        public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
            if (!(sender instanceof EntityPlayer)) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "[ERROR] This command can only be executed by player!"));
                return;
            }

            EntityPlayer player = (EntityPlayer) sender;
            World world = server.getWorld(player.dimension);
            if (!openBackpack(world, player))
                player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "No backpack found"));
            else
                player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Here you are~"));
        }

        @Override
        public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
            return true;
        }

        @Override
        @Nonnull
        public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
            return Collections.emptyList();
        }

    }

    public static class BackpackManageCommand extends CommandBase {

        public BackpackManageCommand(){
            aliases = Lists.newArrayList("gbm", "mcgBack", "mcgPack", "mcgBackpack");
        }

        private final List<String> aliases;

        @Override
        @Nonnull
        public String getName() {
            return "mcgPack";
        }

        @Override
        @Nonnull
        public String getUsage(@Nonnull ICommandSender sender) {
            return " mcgPack <new/del/del!/show> <player name> [backpack type]" + "\n" +
                    "type: default, creative" + "\n" +
                    "note: \"/mcgPack del!\" will delete all packs !!!";
        }

        @Override
        @Nonnull
        public List<String> getAliases() {
            return aliases;
        }

        @Override
        public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
            if (args.length < 2) {
                sender.sendMessage(new TextComponentString(TextFormatting.AQUA + getUsage(sender)));
                if (sender instanceof EntityPlayer) {
                    int dim = ((EntityPlayer) sender).dimension;
                    if (args.length == 1) {
                        String[] str = BackpackRepo.showBackpacksInfo(server.getWorld(dim)).split("\n");
                        for (String s : str)
                            sender.sendMessage(new TextComponentString(TextFormatting.GRAY + s));
                    }
                }
                return;
            }

            if (!(sender instanceof EntityPlayer)) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "[ERROR] This command can only be executed by player!"));
                return;
            }

            String op = args[0], id = args[1], type = BackpackRepo.TYPE_DEFAULT;
            if (args.length > 2) type = args[2];
            EntityPlayer player = (EntityPlayer) sender;
            World world = server.getWorld(player.dimension);
            switch (op.toLowerCase()) {
                case "new":
                    if (BackpackRepo.has(world, id, type)) {
                        player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "Backpack exist"));
                        break;
                    }
                    BackpackRepo.put(world, id, type, BackpackRepo.def());
                    player.sendMessage(new TextComponentString(TextFormatting.GRAY + "Backpack created"));
                    break;
                case "del":
                    BackpackRepo.del(world, id, type);
                    player.sendMessage(new TextComponentString(TextFormatting.GRAY + "Backpack deleted"));
                    break;
                case "del!":
                    BackpackRepo.del(world, id);
                    player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "All backpacks deleted"));
                    break;
                case "show":
                    if (!openBackpack(world, player, id, type))
                        player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "No backpack found"));
                        break;
                default:
                    break;
            }

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

}
