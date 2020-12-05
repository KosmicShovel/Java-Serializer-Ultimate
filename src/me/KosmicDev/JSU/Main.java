package me.KosmicDev.JSU;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String... args) {
        TempClass o = new TempClass();
        o.anInt = 87;
        o.aBoolean = true;
        o.arrayList.add("Test");
        o.arrayList.add("Test2");
        o.arrayList.add(54);
        o.arrayList.add('c');

        //Warmup
        Random r = new Random();
        for (int i = 0; i < 5000; i++) {
            TempClass tmp = new TempClass();
            tmp.anInt = r.nextInt();
            tmp.aBoolean = r.nextBoolean();
            tmp.arrayList.add(r.nextGaussian() + "");
            tmp.arrayList.add(r.nextGaussian() + "");
            tmp.arrayList.add(r.nextInt());
            tmp.arrayList.add(r.nextInt()%127);
            long timeThen = System.nanoTime();
            String gcThis = JsonSerializer.Serialize(tmp);
            long timeNow = System.nanoTime();
            gcThis = null;
            System.gc();
        }
        System.gc();

        List<Float> timeTaken = new ArrayList<>();

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
        //Usually ranges from 0.004ms to 0.0ms
        System.out.println("Average Time Taken was \u001b[36m" + timeTaken.stream().mapToDouble(a -> Math.round(a * 100)/100).average().getAsDouble() + "ms\u001b[00m.");
    }
}
