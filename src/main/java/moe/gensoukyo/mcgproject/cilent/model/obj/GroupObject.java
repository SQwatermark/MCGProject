package moe.gensoukyo.mcgproject.cilent.model.obj;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

/**
 * from Forge 1.7.10
 * @author drzzm32
 * @date 2020/3/27
 */
public class GroupObject
{
    public String name;
    public ArrayList<Face> faces = new ArrayList<Face>();
    public int glDrawingMode;

    public GroupObject()
    {
        this("");
    }

    public GroupObject(String name)
    {
        this(name, -1);
    }

    public GroupObject(String name, int glDrawingMode)
    {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    @SideOnly(Side.CLIENT)
    public void render()
    {
        if (faces.size() > 0)
        {
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            bufferBuilder.begin(glDrawingMode, DefaultVertexFormats.POSITION_TEX_NORMAL);
            render(bufferBuilder);
            GlStateManager.enableRescaleNormal();
            Tessellator.getInstance().draw();
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(BufferBuilder bufferBuilder)
    {
        if (faces.size() > 0)
        {
            for (Face face : faces)
            {
                face.addFaceForRender(bufferBuilder);
            }
        }
    }
}
