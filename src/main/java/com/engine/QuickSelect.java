package com.engine;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or k is out of range");
        }
        return select(a, 0, a.length - 1, k, m);
    }

    private static int select(int[] a, int low, int high, int k, Metrics m) {
        while (low <= high) {
            if (low == high) {
                return a[low];
            }

            m.enterRecursion();

            int[] bounds = QuickSort.partition(a, low, high, m);
            int lt = bounds[0];
            int gt = bounds[1];

            if (k >= lt && k <= gt) {
                m.exitRecursion();
                return a[k]; // Элемент найден среди равных pivot
            } else if (k < lt) {
                high = lt - 1; // k находится слева (в зоне < pivot)
            } else {
                low = gt + 1;  // k находится справа (в зоне > pivot)
            }

            m.exitRecursion();
        }
        return a[k];
    }
}