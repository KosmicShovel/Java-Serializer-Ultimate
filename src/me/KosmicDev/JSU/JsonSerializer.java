package me.KosmicDev.JSU;

import com.sun.istack.internal.Nullable;

import java.io.Console;
import java.lang.reflect.Field;
import java.util.*;

public class JsonSerializer {

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

    private static String trimExcess(String json) {
        return json.replace("{\n", "").replace("}", "").replace("\t", "");
    }
}
