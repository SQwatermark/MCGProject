package moe.gensoukyo.mcgproject.cilent.model.obj;

/**
 * from Forge 1.7.10
 * @author drzzm32
 * @date 2020/3/27
 */
public class TextureCoordinate
{
    public float u, v, w;

    public TextureCoordinate(float u, float v)
    {
        this(u, v, 0F);
    }

    public TextureCoordinate(float u, float v, float w)
    {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
