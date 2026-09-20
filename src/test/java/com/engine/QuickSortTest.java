package com.engine;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {

    @Test
    void testQuickSortOnRandomArrays() {
        Random rng = new Random();
        Metrics m = new Metrics();

        for (int i = 0; i < 100; i++) {
            int size = rng.nextInt(500) + 1;
            int[] original = rng.ints(size, -1000, 1000).toArray();
            int[] expected = original.clone();

            Arrays.sort(expected);
            QuickSort.sort(original, m);

            assertArrayEquals(expected, original, "QuickSort failed on random array");
        }
    }

    @Test
    void testEdgeCases() {
        Metrics m = new Metrics();

        int[] empty = new int[0];
        QuickSort.sort(empty, m);
        assertArrayEquals(new int[0], empty);

        int[] single = {10};
        QuickSort.sort(single, m);
        assertArrayEquals(new int[]{10}, single);

        int[] duplicates = {3, 3, 3, 3, 3};
        QuickSort.sort(duplicates, m);
        assertArrayEquals(new int[]{3, 3, 3, 3, 3}, duplicates);

        int[] sorted = {1, 2, 3, 4, 5};
        QuickSort.sort(sorted, m);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void testRecursionDepthOnSortedArray() {
        int n = 100_000;
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = i;
        }

        Metrics m = new Metrics();
        QuickSort.sort(sorted, m);

        double maxAllowedDepth = 2 * (Math.log(n) / Math.log(2));
        assertTrue(m.getMaxDepth() <= maxAllowedDepth,
                "Recursion depth " + m.getMaxDepth() + " exceeded limit " + maxAllowedDepth);
    }
}