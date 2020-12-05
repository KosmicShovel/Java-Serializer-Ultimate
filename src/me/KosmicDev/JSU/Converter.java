package me.KosmicDev.JSU;

import java.util.ArrayList;

/**
 * @author KosmicShovel(KosmicDev)
 */
public class Converter {
    /**
     * Note, I did NOT make the majority of this nor do I wish to claim to.
     * The original author is cambecc on StackOverflow
     * https://stackoverflow.com/questions/13868986/dynamically-create-an-object-in-java-from-a-class-name-and-set-class-fields-by-u
     * @param target    Class to cast the string to.
     * @param s String of an object to convert.
     * @return  Converted object from s to typeof target.
     */
    public static Object convert(Class<?> target, String s) {
        if (target == Object.class || target == String.class || s == null) {
            return s;
        }
        if (target == Character.class || target == char.class) {
            return s.charAt(0);
        }
        if (target == Byte.class || target == byte.class) {
            return Byte.parseByte(s);
        }
        if (target == Short.class || target == short.class) {
            return Short.parseShort(s);
        }
        if (target == Integer.class || target == int.class) {
            return Integer.parseInt(s);
        }
        if (target == Long.class || target == long.class) {
            return Long.parseLong(s);
        }
        if (target == Float.class || target == float.class) {
            return Float.parseFloat(s);
        }
        if (target == Double.class || target == double.class) {
            return Double.parseDouble(s);
        }
        if (target == Boolean.class || target == boolean.class) {
            return Boolean.parseBoolean(s);
        }
        if (target == ArrayList.class) {
            ArrayList a = new ArrayList();
            for (String tmp:s.split(", "))
                a.add(convert(Object.class, tmp.replace("[", "").replace("]", "")));
            return a;
        }
        throw new IllegalArgumentException("Don't know how to convert to " + target);
    }
}
