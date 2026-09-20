package com.engine;

import java.util.Random;

public class QuickSort {
    private static final Random RNG = new Random();

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int low, int high, Metrics m) {
        while (low < high) {
            m.enterRecursion();

            int[] bounds = partition(a, low, high, m);
            int lt = bounds[0];
            int gt = bounds[1];

            int leftSize = lt - low;
            int rightSize = high - gt;

            if (leftSize < rightSize) {
                sort(a, low, lt - 1, m);
                low = gt + 1;
            } else {
                sort(a, gt + 1, high, m);
                high = lt - 1;
            }

            m.exitRecursion();
        }
    }

    public static int[] partition(int[] a, int low, int high, Metrics m) {
        int pivotIdx = low + RNG.nextInt(high - low + 1);
        swap(a, low, pivotIdx);
        int pivot = a[low];

        int lt = low;
        int gt = high;
        int i = low + 1;

        while (i <= gt) {
            m.incComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else {
                m.incComparisons();
                if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}