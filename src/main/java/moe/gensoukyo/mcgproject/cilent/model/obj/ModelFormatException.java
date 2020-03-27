package moe.gensoukyo.mcgproject.cilent.model.obj;

/**
 * from Forge 1.7.10
 * @author drzzm32
 * @date 2020/3/27
 */
public class ModelFormatException extends RuntimeException {

    private static final long serialVersionUID = 2023547503969671835L;

    public ModelFormatException()
    {
        super();
    }

    public ModelFormatException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ModelFormatException(String message)
    {
        super(message);
    }

    public ModelFormatException(Throwable cause)
    {
        super(cause);
    }

}
