package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author drzzm32
 * @date 2020/6/7
 */
public class PackAdminPacket implements IMessage {

    public NBTTagCompound tag;

    public PackAdminPacket() {
        this.tag = new NBTTagCompound();
    }

    public void put(String name, String... types) {
        NBTTagList list = new NBTTagList();
        for (String t : types)
            list.appendTag(new NBTTagString(t));
        this.tag.setTag(name, list);
    }

    public Set<String> getNames() {
        return tag.getKeySet();
    }

    public List<String> getTypes(String name) {
        if (this.tag.hasKey(name)) {
            NBTTagList list = this.tag.getTagList(name, 8);
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < list.tagCount(); i++)
                arrayList.add(list.getStringTagAt(i));
            return arrayList;
        }
        return Collections.emptyList();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            (new PacketBuffer(buf)).writeCompoundTag(tag);
        } catch (Exception ignored) { }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            tag = (new PacketBuffer(buf)).readCompoundTag();
        } catch (Exception ignored) { }
    }

}
