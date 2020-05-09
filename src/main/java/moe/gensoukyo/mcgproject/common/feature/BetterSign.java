package moe.gensoukyo.mcgproject.common.feature;

import moe.gensoukyo.mcgproject.cilent.gui.GuiBetterSign;
import moe.gensoukyo.mcgproject.core.Information;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 创造模式才能打开牌子
 * 手中持有告示牌的情况下，可以直接右击打开
 * 手中未持有告示牌的情况下，按住ctrl右击才可以打开
 */
@Information(author = {"Vazkii", "SQwatermark"}, licence = "CC-BY-NC-SA 3.0", source = "https://github.com/Vazkii/Quark")
public class BetterSign {

	private static BetterSign instance;
	public static BetterSign instance() {
		if(instance == null) instance = new BetterSign();
		return instance;
	}

	/**
	 * 牌子刚放下时要取消打开 GUI 的事件，只有右键点击牌子时才能打开
	 */
	boolean cancel = true;

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer entityPlayer = event.getEntityPlayer();
		/*
		只有创造模式可以打开
		手中未持有牌子时需要按下左ctrl键，手中持有牌子时，直接打开
		 */
		if(!entityPlayer.isSneaking() && entityPlayer.getHeldItemMainhand().getItem() != Items.SIGN || event.getUseBlock() == Result.DENY || !entityPlayer.isCreative()) return;

		TileEntity tile = event.getWorld().getTileEntity(event.getPos());
		if(tile instanceof TileEntitySign && entityPlayer.capabilities.allowEdit) {
			TileEntitySign sign = (TileEntitySign) tile;
			sign.setPlayer(entityPlayer);
			ObfuscationReflectionHelper.setPrivateValue(TileEntitySign.class, sign, true, "field_145916_j");
			if(event.getWorld().isRemote) setCancel(false);
			entityPlayer.openEditSign(sign);
			if(event.getWorld().isRemote) setCancel(true);
		}
	}

	@SideOnly(Side.CLIENT)
	private void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onOpenGUI(GuiOpenEvent event) {
		if(event.getGui() instanceof GuiEditSign) {
			if(cancel) {
				return;
			}
			TileEntitySign sign = ObfuscationReflectionHelper.getPrivateValue(GuiEditSign.class, (GuiEditSign) event.getGui(), "field_146848_f");
			event.setGui(new GuiBetterSign(sign));
		}
	}
}