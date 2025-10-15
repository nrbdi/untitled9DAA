
import algorithms.MergeSort;
import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    private final Random random = new Random(42);

    @Test
    void testMergeSortEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        assertEquals(0, metrics.getMaxRecursionDepth());
    }

    @Test
    void testMergeSortSingleElement() {
        int[] arr = {5};
        int[] expected = {5};

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        assertEquals(0, metrics.getMaxRecursionDepth());
    }

    @Test
    void testMergeSortSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testMergeSortReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testMergeSortRandomArray() {
        int[] arr = generateRandomArray(1000);
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        assertTrue(metrics.getMaxRecursionDepth() <= Math.ceil(Math.log(1000) / Math.log(2)) + 1);
    }

    @Test
    void testMergeSortWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        int[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testMergeSortCutoffBehavior() {
        int[] arr = generateRandomArray(50);
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    private int[] generateRandomArray(int size) {
        return random.ints(size).toArray();
    }
}
