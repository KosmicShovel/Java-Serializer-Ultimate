package me.KosmicDev.JSU;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;
import java.util.*;

public class JsonSerializer {

    public static String Serialize(Object value) {
        String result = "";

        Class<?> clazz = value.getClass();

        for (Field f : clazz.getFields()) {
            try {
                result += f.getName() + " : " + f.get(value) + " : " + f.get(value).getClass().getTypeName() + "\n";
            } catch (Exception e) {}
        }

        return result;
    }

    public static Object Deserialize(Class type, String json, @Nullable Object... args) {
        try {
            Object clazz = type.newInstance();
            HashMap<String, Object> FieldValues = new HashMap<>();
            for (String line : json.split("\n")) {
                String[] parts = line.split(" : ");
                String name = parts[0];
                String value = parts[1];
                String typeName = parts[2];
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
}
