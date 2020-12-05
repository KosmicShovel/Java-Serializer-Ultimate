package me.KosmicDev.JSU;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String... args) {
        List<Float> timeTaken = new ArrayList<>();

        TempClass o = new TempClass();
        o.anInt = 87;
        o.aBoolean = true;
        o.arrayList.add("Test");
        o.arrayList.add("Test2");
        o.arrayList.add(54);
        o.arrayList.add('c');

        for (int i = 0; i < 500; i++) {
            long timeThen = System.nanoTime();
            String gcThis = JsonSerializer.Serialize(o);
            long timeNow = System.nanoTime();
            gcThis = null;
            System.gc();
            float timeTook = (timeNow - timeThen) / 1000000f;
            timeTaken.add(timeTook);
            System.out.println("Time taken was " + (timeTook > 0.03f ? "\u001b[31m" : (timeTook < 0.015f ? "\u001b[32m" : "\u001b[33m")) + timeTook + "ms\u001b[00m.");
            System.out.println("---------------------------------------------------------");
        }

        //Average Time Taken was 0.002ms.
        //Usually ranges from 0.004ms to 0.002ms
        System.out.println("Average Time Taken was \u001b[36m" + timeTaken.stream().mapToDouble(a -> Math.round(a * 100)/100).average().getAsDouble() + "ms\u001b[00m.");
    }
}
