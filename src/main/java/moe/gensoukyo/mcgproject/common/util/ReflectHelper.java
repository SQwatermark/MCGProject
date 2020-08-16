package moe.gensoukyo.mcgproject.common.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ReflectHelper {

    /**
     * 调用父类的方法，如果子类无法正确混淆的话就用这个直接调用
     * @param obj 要操作的对象，调用其对应的类的父类方法
     * @param name 混淆方法名
     * @param args 参数，[参数类型，参数实体]... 长度需要是偶数（含零）
     * */
    public static Object invokeSuperMethod(Object obj, Class<?> superClass, Class<?> returnType, String name, Object... args) {
        if ((args.length % 2) != 0)
            return null;

        ArrayList<Class<?>> paramTypes = new ArrayList<>();
        ArrayList<Object> params = new ArrayList<>();

        for (int i = 0; i < (args.length / 2); i++) {
            if (!(args[i] instanceof Class))
                return null;
            paramTypes.add((Class<?>) args[i]);
            params.add(args[i + 1]);
        }

        try {
            Field IMPL_LOOKUP = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP.setAccessible(true);
            MethodHandles.Lookup lookup = (MethodHandles.Lookup) IMPL_LOOKUP.get(null);

            MethodHandle handle;
            Class<?> cls = obj.getClass();
            if (args.length == 0) {
                handle = lookup.findSpecial(superClass, name,
                        MethodType.methodType(returnType), cls);
                return handle.invoke(obj);
            } else {
                handle = lookup.findSpecial(superClass, name,
                        MethodType.methodType(returnType, paramTypes.toArray(new Class[0])), cls);
                return handle.invoke(obj, params.toArray());
            }
        } catch (Throwable ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

}
