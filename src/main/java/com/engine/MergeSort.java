package com.engine;

public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        // Требование: выделяем вспомогательный массив ровно ОДИН раз
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] buffer, int low, int high, Metrics m) {
        if (high <= low) return;

        m.enterRecursion();

        if (high - low + 1 <= CUTOFF) {
            insertionSort(a, low, high, m);
            m.exitRecursion();
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, buffer, low, mid, m);
        sort(a, buffer, mid + 1, high, m);

        merge(a, buffer, low, mid, high, m);

        m.exitRecursion();
    }

    private static void merge(int[] a, int[] buffer, int low, int mid, int high, Metrics m) {
        for (int k = low; k <= high; k++) {
            buffer[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > high) {
                a[k] = buffer[i++];
            } else {
                m.incComparisons();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int low, int high, Metrics m) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low) {
                m.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}