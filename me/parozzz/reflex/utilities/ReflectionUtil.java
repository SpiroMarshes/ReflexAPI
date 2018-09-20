package me.parozzz.reflex.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ReflectionUtil
{
    public static <T> Constructor<T> getConstructor(final Class<T> clazz, final Class<?>... types)
    {
        try
        {
            return clazz.getDeclaredConstructor(types);
        }
        catch(SecurityException | NoSuchMethodException ex)
        {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Field getField(final Class<?> clazz, final String name)
    {
        try
        {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        }
        catch(NoSuchFieldException | SecurityException ex)
        {
            throw new NullPointerException("Field " + name + " does not exist in class " + clazz.getName());
        }
    }

    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... args)
    {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(mt -> mt.getName().equals(name))
                .filter(mt -> args.length == 0 || ReflectionUtil.classListEqual(args, mt.getParameterTypes())).findFirst().map(method ->
                {
                    method.setAccessible(true);
                    return method;
                }).orElseThrow(() -> new NullPointerException(
                        "Method " + name + " does not exist in class " + clazz.getSimpleName()));
    }

    public static boolean classListEqual(Class<?>[] l1, Class<?>[] l2)
    {
        return l1.length == l2.length && IntStream.of(l1.length - 1).allMatch(i -> l1[i] == l2[i]);
    }
}