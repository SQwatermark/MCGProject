package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.common.item.ItemMCGWeapon;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

/**
 * 统一管理剑的模型
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MCGProject.ID)
public class SwordModelMapper {
    private static final ResourceLocation SWORD_BLOCKSTATE_NAME =
            new ResourceLocation(MCGProject.ID, ItemMCGWeapon.UNIFIED_NAME);

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        List<ItemSword> swords = ItemMCGWeapon.getInstances();
        for (int i = 0; i < swords.size(); i++) {
            Item item = swords.get(i);
            registerModel(item, i);
        }
    }

    private static void registerModel(Item item, int i) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(SwordModelMapper.SWORD_BLOCKSTATE_NAME,
                        String.valueOf(i)));
    }
}
