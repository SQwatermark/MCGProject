package moe.gensoukyo.mcgproject.common.feature.sticker;

import moe.gensoukyo.mcgproject.cilent.gui.StickerEditor;
import moe.gensoukyo.mcgproject.cilent.util.TextureLoader;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.network.NetworkWrapper;
import moe.gensoukyo.mcgproject.common.network.StickerPacket;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

import static moe.gensoukyo.mcgproject.cilent.tileentity.TileStickerRenderer.DEFAULT_RES;
import static moe.gensoukyo.mcgproject.cilent.tileentity.TileStickerRenderer.DEFAULT_URL;

/**
 * @author drzzm32
 * @date 2020/4/12
 */
public class BlockSticker extends BlockContainer {

    public static BlockSticker BLOCK, BLOCK_LIT;
    public static Item ITEM, ITEM_LIT;

    public static void initBlock() { BLOCK = new BlockSticker(false); BLOCK_LIT = new BlockSticker(true); }
    public static void initItem() {
        ITEM = new ItemBlock(BLOCK).setRegistryName(MCGProject.ID, "sticker");
        ITEM_LIT = new ItemBlock(BLOCK_LIT).setRegistryName(MCGProject.ID, "sticker_lit");
    }

    public static boolean heldItem(@Nonnull ItemStack stack) {
        return stack.getItem() == ITEM || stack.getItem() == ITEM_LIT;
    }

    public BlockSticker(boolean lit) {
        super(Material.CLOTH, MapColor.AIR);
        setRegistryName(MCGProject.ID, "sticker" + (lit ? "_lit" : ""));
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.CLOTH);
        setLightLevel(lit ? 1.0F : 0.0F);
        setTranslationKey(MCGProject.ID + "." + "blockSticker" + (lit ? "Lit" : ""));
        setCreativeTab(MCGTabs.FANTASY);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.GOLD + "Java Swing inside");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int i) {
        return new TileSticker();
    }

    @Override
    public boolean isSideSolid(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing facing) {
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

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    public static boolean heldThis(ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock) {
            return ((ItemBlock) stack.getItem()).getBlock() instanceof BlockSticker;
        }
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean v) {
        if (MCGProject.isServer())
            return false;
        else {
            return heldThis(Minecraft.getMinecraft().player.getHeldItemMainhand());
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSticker) {
            TileSticker sticker = (TileSticker) te;
            int val = MathHelper.floor((double)((player.rotationYaw + 180.0F) * 8.0F / 360.0F) + 0.5D) & 7;
            sticker.rotateY = (360 - val * 45 + 180) % 360;
            sticker.refresh();
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)  {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSticker) {
            TileSticker sticker = (TileSticker) te;
            ItemStack stack = player.getHeldItem(hand);
            if (heldItem(stack)) {
                if (world.isRemote) {
                    new StickerEditor(sticker).setCallback(info -> {
                        if (info.width == 0 || info.height == 0) {
                            Point point;

                            String url = info.url;
                            if (url.startsWith(DEFAULT_URL))
                                url = url.substring(DEFAULT_URL.length());

                            if (url.startsWith("http"))
                                point = TextureLoader.getTextureSize(url);
                            else {
                                IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
                                ResourceLocation loc = new ResourceLocation(DEFAULT_RES + url);
                                try {
                                    manager.getResource(loc);
                                    point = TextureLoader.getTextureSize(loc);
                                } catch (Exception e) {
                                    point = TextureLoader.getTextureSize(DEFAULT_URL + url);
                                }
                            }

                            info.width = point.x; info.height = point.y;
                            info.frameWidth = info.width;
                            info.frameHeight = info.height;
                        }
                        if (info.scaleZ != 1) {
                            info.scaleX = info.scaleZ;
                            info.scaleY = (double) info.height / (double) info.width * info.scaleZ;
                        }
                        Minecraft.getMinecraft().addScheduledTask(() -> {
                            info.toSticker(sticker);
                            sticker.refresh();
                            NetworkWrapper.INSTANCE.sendToServer(new StickerPacket(sticker));
                        });
                    });
                }
                return true;
            }
        }

        return false;
    }

}
