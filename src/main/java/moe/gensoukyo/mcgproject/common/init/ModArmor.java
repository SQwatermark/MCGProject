package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.item.ItemMCGArmor;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.Objects;

public class ModArmor {

    private static ModArmor instance;
    public static ModArmor instance()
    {
        if(instance == null) instance = new ModArmor();
        return instance;
    }

    public static LinkedList<Item> armors;

    public ModArmor()
    {

        MCGProject.logger.info("MCGProject: loading armors");

        armors = new LinkedList<>();

        addArmors("Rumia", "th06", 120, 2, 4, 3, 1, 10);
        addArmors("Daiyosei", "th06", 100, 1, 3, 3, 1, 8);
        addArmors("Cirno", "th06", 120, 2, 4, 3, 1, 12);

    }

    public void addArmors(String character, String th, int durability, int a, int b, int c ,int d, int e)
    {
        ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(character.toUpperCase(),
                MCGProject.ID + ":" + th + "_" + character.toLowerCase(), durability, new int[]
                        { a, b, c, d }, e, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
        assert material != null;
        armors.add(new ItemMCGArmor(material.toString().toLowerCase(), material, EntityEquipmentSlot.HEAD));
        armors.add(new ItemMCGArmor(material.toString().toLowerCase(), material, EntityEquipmentSlot.CHEST));
        armors.add(new ItemMCGArmor(material.toString().toLowerCase(), material, EntityEquipmentSlot.LEGS));
        armors.add(new ItemMCGArmor(material.toString().toLowerCase(), material, EntityEquipmentSlot.FEET));
    }

    @SubscribeEvent
    public void registerArmors(RegistryEvent.Register<Item> event)
    {
        MCGProject.logger.info("MCGProject: registering armors");
        event.getRegistry().registerAll(armors.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerArmorModels(ModelRegistryEvent event)
    {
        MCGProject.logger.info("MCGProject: registering armor models");
        for (Item i : armors) {
                ModelLoader.setCustomModelResourceLocation(i, 0,
                        new ModelResourceLocation(Objects.requireNonNull(i.getRegistryName()), "inventory"));
        }
    }

}
