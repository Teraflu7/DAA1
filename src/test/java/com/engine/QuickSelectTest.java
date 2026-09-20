package com.engine;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuickSelectTest {

    @Test
    void testQuickSelectOnRandomArrays() {
        Random rng = new Random();
        Metrics m = new Metrics();

        for (int i = 0; i < 100; i++) {
            int size = rng.nextInt(300) + 1;
            int[] original = rng.ints(size, -1000, 1000).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            int k = rng.nextInt(size);
            int selected = QuickSelect.select(original, k, m);

            assertEquals(expected[k], selected, "QuickSelect returned wrong element at k=" + k);
        }
    }

    @Test
    void testInvalidInputThrowsException() {
        Metrics m = new Metrics();

        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[0], 0, m));

        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, -1, m));

        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, 3, m));
    }
}