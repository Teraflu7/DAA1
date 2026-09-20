package com.engine;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

    @Test
    void testMergeSortOnRandomArrays() {
        Random rng = new Random();
        Metrics m = new Metrics();

        for (int i = 0; i < 100; i++) {
            int size = rng.nextInt(500) + 1;
            int[] original = rng.ints(size, -1000, 1000).toArray();
            int[] expected = original.clone();

            Arrays.sort(expected);
            MergeSort.sort(original, m);

            assertArrayEquals(expected, original, "MergeSort failed on random array");
        }
    }

    @Test
    void testEdgeCases() {
        Metrics m = new Metrics();

        int[] empty = new int[0];
        MergeSort.sort(empty, m);
        assertArrayEquals(new int[0], empty);

        int[] single = {42};
        MergeSort.sort(single, m);
        assertArrayEquals(new int[]{42}, single);

        int[] duplicates = {5, 5, 5, 5, 5};
        MergeSort.sort(duplicates, m);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, duplicates);

        int[] sorted = {1, 2, 3, 4, 5, 6, 7};
        MergeSort.sort(sorted, m);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, sorted);
    }
}