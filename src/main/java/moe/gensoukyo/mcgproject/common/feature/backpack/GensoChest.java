package moe.gensoukyo.mcgproject.common.feature.backpack;

import com.google.common.collect.Lists;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.init.ModSound;
import moe.gensoukyo.mcgproject.common.tileentity.AbstractTileEntity;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * @author drzzm32
 * @date 2020/3/17
 * @apiNote 幻想箱方块
 */
public class GensoChest extends BlockContainer {

    public static class TileGensoChest extends AbstractTileEntity {

        public String packType = "null";

        @Override
        public void fromNBT(NBTTagCompound tagCompound) {
            packType = tagCompound.getString("packType");
        }

        @Nonnull
        @Override
        public NBTTagCompound toNBT(NBTTagCompound tagCompound) {
            tagCompound.setString("packType", packType);
            return tagCompound;
        }

        @Nullable
        @Override
        public ITextComponent getDisplayName() {
            if (packType.isEmpty() || packType.equals("null"))
                return null;
            return new TextComponentString(
                    TextFormatting.LIGHT_PURPLE + "[" + packType + "]");
        }

    }

    public GensoChest() {
        super(Material.GLASS);
        setTranslationKey(MCGProject.ID + "." + "GensoChest");
        setRegistryName(MCGProject.ID, "genso_chest");
        setHardness(2.0F);
        setLightLevel(0.125F);
        setSoundType(SoundType.GLASS);
        setResistance(10.0F);
        setCreativeTab(MCGTabs.FANTASY);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileGensoChest();
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (world.isRemote)
            return true;

        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem().equals(Items.NAME_TAG)) {
            if (checkPermission(world.getMinecraftServer(), player)) {
                String type = "null";
                if (stack.hasDisplayName())
                    type = stack.getDisplayName();
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TileGensoChest) {
                    ((TileGensoChest) tileEntity).packType = type;
                    ((TileGensoChest) tileEntity).refresh();
                    player.sendMessage(new TextComponentString(
                            TextFormatting.DARK_PURPLE + "GensoChest -> " + type));
                    return true;
                }
            }
        }

        String type = "null";
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileGensoChest)
            type = ((TileGensoChest) tileEntity).packType;

        boolean result = false;
        if (type.isEmpty() || type.equals("null"))
            result = BackpackCore.openBackpack(world, player);
        else if (!player.isCreative())
            result = BackpackCore.openBackpack(world, player, player.getName(), type);

        if (result)
            ModSound.instance().playSound(player, ModSound.instance().GENSOCHEST_OPEN, SoundCategory.AMBIENT);

        return true;
    }

}
