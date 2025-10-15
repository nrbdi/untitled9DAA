
import algorithms.DeterministicSelect;
import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {
    private final Random random = new Random(42);

    @Test
    void testSelectInvalidInput() {
        Metrics metrics = new Metrics();

        assertThrows(IllegalArgumentException.class, () ->
                DeterministicSelect.select(new int[]{}, 0, metrics));
        assertThrows(IllegalArgumentException.class, () ->
                DeterministicSelect.select(new int[]{1}, -1, metrics));
        assertThrows(IllegalArgumentException.class, () ->
                DeterministicSelect.select(new int[]{1}, 2, metrics));
    }

    @Test
    void testSelectSingleElement() {
        int[] arr = {5};

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, 0, metrics);

        assertEquals(5, result);
    }

    @Test
    void testSelectMin() {
        int[] arr = {5, 2, 8, 1, 9};

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, 0, metrics);

        assertEquals(1, result);
    }

    @Test
    void testSelectMax() {
        int[] arr = {5, 2, 8, 1, 9};

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, 4, metrics);

        assertEquals(9, result);
    }

    @Test
    void testSelectMedian() {
        int[] arr = {5, 2, 8, 1, 9};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, 2, metrics);

        assertEquals(sorted[2], result);
    }

    @RepeatedTest(100)
    void testSelectRandomArrays() {
        int[] arr = generateRandomArray(100);
        int k = random.nextInt(100);

        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int expected = sorted[k];

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, k, metrics);

        assertEquals(expected, result,
                String.format("Failed for k=%d, expected %d, got %d", k, expected, result));
    }

    @Test
    void testSelectWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        for (int k = 0; k < arr.length; k++) {
            Metrics metrics = new Metrics();
            int result = DeterministicSelect.select(arr, k, metrics);
            assertEquals(sorted[k], result, "Failed for k=" + k);
        }
    }

    @Test
    void testSelectAllDuplicates() {
        int[] arr = {1, 1, 1, 1, 1};

        for (int k = 0; k < arr.length; k++) {
            Metrics metrics = new Metrics();
            int result = DeterministicSelect.select(arr, k, metrics);
            assertEquals(1, result);
        }
    }

    @Test
    void testSelectLargeArray() {
        int[] arr = generateRandomArray(1000);
        int k = 499; // median

        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int expected = sorted[k];

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, k, metrics);

        assertEquals(expected, result);
    }

    private int[] generateRandomArray(int size) {
        return random.ints(size).toArray();
    }
}