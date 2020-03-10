package moe.gensoukyo.mcgproject.common.init;

public class ModFluid {

    private static ModFluid instance;
    public static ModFluid instance()
    {
        if(instance == null) instance = new ModFluid();
        return instance;
    }

    private ModFluid() {

    }

}
