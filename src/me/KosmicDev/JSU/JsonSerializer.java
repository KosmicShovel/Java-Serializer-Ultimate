package me.KosmicDev.JSU;

import com.sun.istack.internal.Nullable;

import java.io.Console;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author KosmicShovel(KosmicDev)
 */
public class JsonSerializer {

     /**
     * For good performance, it is essential to call this on initialization
     * for your program or any other time it wont interfere with the end user
     */
    public static void Init() { Init(2000); }

     /**
     * For good performance, it is essential to call this on initialization
     * for your program or any other time it wont interfere with the end user
     * @param iterations    The amount of iterations to warm up the serializer with.
     */
    public static void Init(int iterations) {
        Random r = new Random();
        for (int i = 0; i < iterations; i++) {
            TempClass tmp = new TempClass();
            tmp.anInt = r.nextInt();
            tmp.aBoolean = r.nextBoolean();
            tmp.arrayList.add(r.nextGaussian() + "");
            tmp.arrayList.add(r.nextGaussian());
            tmp.arrayList.add(r.nextFloat());
            tmp.arrayList.add((byte)r.nextInt()%127);
            long timeThen = System.nanoTime();
            String gcThis = JsonSerializer.Serialize(tmp);
            long timeNow = System.nanoTime();
            gcThis = null;
            System.gc();
        }
        System.gc();
    }

    /**
     *  Serializes any given object into JSON.
     *  All serializable object members are:
     *  - Object
     *  - String
     *  - Character
     *  - Byte
     *  - Short
     *  - Integer
     *  - Long
     *  - Float
     *  - Double
     *  - Boolean
     *  - ArrayList
     * @param value The object given to serialize.
     * @return  Serialized JSON as a String.
     */
    public static String Serialize(Object value) {
        String result = "{\n";

        Class<?> clazz = value.getClass();

        for (Field f : clazz.getFields()) {
            try {
                result += "\t\"" + f.getName() + "\": \"" + f.get(value) + "::" + f.get(value).getClass().getTypeName() + "\"" + (clazz.getFields()[clazz.getFields().length - 1].equals(f) ? "" : ",") + "\n";
            } catch (Exception e) {}
        }

        result += "}";

        return result;
    }

    /**
     *  Deserializes any given JSON string to the selected type with args provided if necessary.
     * @param type  Class type to deserialize to.
     * @param json  Serialized JSON.
     * @param args  Any args needed for instantiation. (Nullable)
     * @return  The object deserialized from the JSON.
     */
    public static Object Deserialize(Class type, String json, @Nullable Object... args) {
        try {
            Object clazz = type.newInstance();
            HashMap<String, Object> FieldValues = new HashMap<>();
            for (String line : trimExcess(json).split("\n")) {
                String[] parts = line.split(": ");
                String name = parts[0].replace("\"", "");
                String[] parts2 = parts[1].split("::");
                String value = parts2[0].replace("\"", "");
                String typeName = parts2[1].replace("\"", "").replace(",", "");
                FieldValues.put(name, Converter.convert(Class.forName(typeName), value));
            }

            for (Map.Entry<String, Object> entry : FieldValues.entrySet()) {
                type.getField(entry.getKey()).set(clazz, entry.getValue());
            }
            return clazz;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Removes any excess characters in the JSON
     * @param json JSON string to parse
     * @return
     */
    private static String trimExcess(String json) {
        return json.replace("{\n", "").replace("}", "").replace("\t", "");
    }
}
