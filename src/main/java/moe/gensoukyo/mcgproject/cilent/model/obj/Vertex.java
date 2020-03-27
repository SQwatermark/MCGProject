package moe.gensoukyo.mcgproject.cilent.model.obj;

/**
 * from Forge 1.7.10
 * @author drzzm32
 * @date 2020/3/27
 */
public class Vertex
{
    public float x, y, z;

    public Vertex(float x, float y)
    {
        this(x, y, 0F);
    }

    public Vertex(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
