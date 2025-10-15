import algorithms.QuickSort;
import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    private final Random random = new Random(42);

    @Test
    void testQuickSortEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        assertEquals(0, metrics.getMaxRecursionDepth());
    }

    @Test
    void testQuickSortSingleElement() {
        int[] arr = {5};
        int[] expected = {5};

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        assertEquals(0, metrics.getMaxRecursionDepth());
    }

    @Test
    void testQuickSortSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortRandomArray() {
        int[] arr = generateRandomArray(1000);
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        // QuickSort depth should be O(log n) with high probability
        assertTrue(metrics.getMaxRecursionDepth() <= 2 * Math.ceil(Math.log(1000) / Math.log(2)) + 10);
    }

    @RepeatedTest(10)
    void testQuickSortRandomizedPivot() {
        int[] arr = generateRandomArray(100);
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortAllDuplicates() {
        int[] arr = {1, 1, 1, 1, 1};
        int[] expected = {1, 1, 1, 1, 1};

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortDepthBounded() {
        int[] arr = generateRandomArray(10000);

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        double log2n = Math.log(10000) / Math.log(2);
        assertTrue(metrics.getMaxRecursionDepth() <= 2.5 * log2n,
                "Depth: " + metrics.getMaxRecursionDepth() + " should be <= " + (2.5 * log2n));
    }

    private int[] generateRandomArray(int size) {
        return random.ints(size).toArray();
    }
}
