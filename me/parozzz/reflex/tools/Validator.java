package me.parozzz.reflex.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validator
{
    public static <T extends Enum<T>> T validateEnum(final String name, final Class<T> en)
    {
        return Optional.ofNullable(name).map(String::toUpperCase).map(s ->
        {
            try
            {
                return Enum.valueOf(en, s);
            }
            catch(final IllegalArgumentException t)
            {
                throw new IllegalArgumentException(
                        "Wrong format type for enum " + en.getSimpleName() + ". Value " + name + " does not exist");
            }
        }).orElseThrow(() -> new IllegalArgumentException("Null value passed to " + en.getSimpleName() + " for enum validation"));
    }

    public static Object validateMethod(final Method m, final Object o, final Object... arguments)
    {
        try
        {
            return m.invoke(o, arguments);
        }
        catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static <T> T validateMethod(final Method m, final Object o, final Class<T> cast, final Object... arguments)
    {
        try
        {
            return cast.cast(m.invoke(o, arguments));
        }
        catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static <T> T validateConstructor(final Constructor<T> c, final Object... arguments)
    {
        try
        {
            return c.newInstance(arguments);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Object validateField(final Field f, final Object instance)
    {
        try
        {
            setFieldAccessible(f);
            return f.get(instance);
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static <T> T validateField(final Field f, final Object instance, Class<T> cast)
    {
        try
        {
            setFieldAccessible(f);
            return cast.cast(f.get(instance));
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean validateBooleanField(final Field f, final Object instance)
    {
        try
        {
            setFieldAccessible(f);
            return f.getBoolean(instance);
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static int validateIntField(final Field f, final Object instance)
    {
        try
        {
            setFieldAccessible(f);
            return f.getInt(instance);
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public static long validateLongField(final Field f, final Object instance)
    {
        try
        {
            setFieldAccessible(f);
            return f.getLong(instance);
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return 0L;
        }
    }

    public static float validateFloatField(final Field f, final Object instance)
    {
        try
        {
            setFieldAccessible(f);
            return f.getFloat(instance);
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return 0F;
        }
    }

    public static double validateDoubleField(final Field f, final Object instance)
    {
        try
        {
            setFieldAccessible(f);
            return f.getDouble(instance);
        }
        catch(IllegalAccessException | IllegalArgumentException ex)
        {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            return 0D;
        }
    }

    private static void setFieldAccessible(Field f)
    {
        if(!f.isAccessible())
        {
            f.setAccessible(true);
        }
    }

    /**
     * Parse the passed string to an int
     *
     * @param string The string to parse
     * @return The parsed String or Integer.MIN_VALUE if not valid.
     */
    public static int parseInt(String string)
    {
        try
        {
            return Integer.parseInt(string);
        }
        catch(NumberFormatException ex)
        {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Parse the passed string to an int
     *
     * @param string The string to parse
     * @return The parsed String or Duble.MIN_VALUE if not valid.
     */
    public static double parseDouble(String string)
    {
        try
        {
            return Double.parseDouble(string);
        }
        catch(NumberFormatException ex)
        {
            return Double.MIN_VALUE;
        }
    }

}
