package me.KosmicDev.JSU;

public class Main {

    public static void main(String... args) {
        TempClass o = new TempClass();
        o.anInt = 87;
        o.aBoolean = true;
        o.arrayList.add("Test");
        o.arrayList.add("Test2");
        o.arrayList.add(54);
        o.arrayList.add('c');
        String s = JsonSerializer.Serialize(o);
        System.out.println(s);
        TempClass deserializedClass = (TempClass) JsonSerializer.Deserialize(TempClass.class, s,null);
        System.out.println(o.anInt); //87
        System.out.println(o.aBoolean); //true
        System.out.println(o.arrayList.get(0)); //Test
        System.out.println(o.arrayList.get(1)); //Test2
        System.out.println(o.arrayList.get(2)); //54
        System.out.println(o.arrayList.get(3)); //c
    }
}
