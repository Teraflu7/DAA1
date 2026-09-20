package com.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        System.out.println("Warm-up JVM started...");
        warmUp();
        System.out.println("Warm-up finished. Running benchmark...");

        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (int n : SIZES) {
                for (String type : INPUT_TYPES) {
                    System.out.printf("Testing n = %d, input = %s%n", n, type);

                    runExperiment("MergeSort", type, n, writer);

                    runExperiment("QuickSort", type, n, writer);

                    runExperiment("QuickSelect", type, n, writer);
                }
            }
            System.out.println("Benchmark completed! Results saved to results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void warmUp() {
        Random rng = new Random(42);
        Metrics m = new Metrics();
        for (int i = 0; i < 5; i++) {
            int[] arr = rng.ints(20_000).toArray();
            MergeSort.sort(arr.clone(), m);
            QuickSort.sort(arr.clone(), m);
            QuickSelect.select(arr.clone(), 10_000, m);
        }
    }

    private static void runExperiment(String algorithm, String inputType, int n, PrintWriter writer) {
        double[] times = new double[RUNS];
        long[] comps = new long[RUNS];
        int[] depths = new int[RUNS];

        for (int r = 0; r < RUNS; r++) {
            int[] original = generateData(n, inputType, r);
            Metrics m = new Metrics();

            long start = System.nanoTime();
            if (algorithm.equals("MergeSort")) {
                MergeSort.sort(original, m);
            } else if (algorithm.equals("QuickSort")) {
                QuickSort.sort(original, m);
            } else if (algorithm.equals("QuickSelect")) {
                QuickSelect.select(original, n / 2, m);
            }
            long end = System.nanoTime();

            times[r] = (end - start) / 1_000_000.0; // переводим в миллисекунды
            comps[r] = m.getComparisons();
            depths[r] = m.getMaxDepth();
        }

        Arrays.sort(times);
        double medianTime = times[RUNS / 2];

        Arrays.sort(comps);
        long medianComps = comps[RUNS / 2];

        Arrays.sort(depths);
        int medianDepth = depths[RUNS / 2];

        writer.printf(java.util.Locale.US, "%s,%s,%d,%.4f,%d,%d%n",
                algorithm, inputType, n, medianTime, medianComps, medianDepth);
        writer.flush();
    }

    private static int[] generateData(int n, String inputType, int seedOffset) {
        Random rng = new Random(1337 + seedOffset);
        int[] a = new int[n];
        switch (inputType) {
            case "random":
                for (int i = 0; i < n; i++) a[i] = rng.nextInt();
                break;
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) a[i] = rng.nextInt(10); // значения от 0 до 9
                break;
        }
        return a;
    }
}