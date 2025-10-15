// src/test/java/EdgeCaseTest.java
import algorithms.*;
import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;
import util.Point;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EdgeCaseTest {

    @Test
    void testNullArrays() {
        Metrics metrics = new Metrics();

        // Should not throw exceptions
        MergeSort.sort(null, metrics);
        QuickSort.sort(null, metrics);

        assertThrows(IllegalArgumentException.class, () ->
                DeterministicSelect.select(null, 0, metrics));

        assertEquals(Double.POSITIVE_INFINITY, ClosestPair.findClosestDistance(null, metrics));
    }

    @Test
    void testEmptyArrays() {
        Metrics metrics = new Metrics();
        int[] empty = {};
        Point[] emptyPoints = {};

        MergeSort.sort(empty, metrics);
        QuickSort.sort(empty, metrics);
        assertEquals(Double.POSITIVE_INFINITY, ClosestPair.findClosestDistance(emptyPoints, metrics));

        assertThrows(IllegalArgumentException.class, () ->
                DeterministicSelect.select(empty, 0, metrics));
    }

    @Test
    void testSingleElement() {
        Metrics metrics = new Metrics();
        int[] single = {42};
        Point[] singlePoint = {new Point(1, 1)};

        MergeSort.sort(single, metrics);
        QuickSort.sort(single, metrics);
        assertEquals(42, DeterministicSelect.select(single, 0, metrics));
        assertEquals(Double.POSITIVE_INFINITY, ClosestPair.findClosestDistance(singlePoint, metrics));

        assertArrayEquals(new int[]{42}, single);
    }

    @Test
    void testTwoElements() {
        Metrics metrics = new Metrics();
        int[] two = {2, 1};
        Point[] twoPoints = {new Point(0, 0), new Point(1, 1)};

        MergeSort.sort(two, metrics);
        assertArrayEquals(new int[]{1, 2}, two);

        QuickSort.sort(new int[]{2, 1}, metrics);

        assertEquals(1.414, ClosestPair.findClosestDistance(twoPoints, metrics), 0.001);
    }

    @Test
    void testAllDuplicates() {
        int[] duplicates = {5, 5, 5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5, 5, 5};

        Metrics metrics = new Metrics();
        MergeSort.sort(duplicates, metrics);
        assertArrayEquals(expected, duplicates);

        QuickSort.sort(duplicates.clone(), metrics);

        // Test select on duplicates
        for (int k = 0; k < duplicates.length; k++) {
            assertEquals(5, DeterministicSelect.select(duplicates.clone(), k, metrics));
        }
    }

    @Test
    void testManyDuplicates() {
        int[] manyDupes = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9};
        int[] expected = manyDupes.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(manyDupes, metrics);
        assertArrayEquals(expected, manyDupes);

        QuickSort.sort(manyDupes.clone(), metrics);
    }

    @Test
    void testAlreadySorted() {
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = sorted.clone();

        Metrics metrics = new Metrics();
        MergeSort.sort(sorted, metrics);
        assertArrayEquals(expected, sorted);

        QuickSort.sort(sorted.clone(), metrics);
    }

    @Test
    void testReverseSorted() {
        int[] reverse = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Metrics metrics = new Metrics();
        MergeSort.sort(reverse, metrics);
        assertArrayEquals(expected, reverse);

        QuickSort.sort(reverse.clone(), metrics);
    }

    @Test
    void testTinyArrays() {
        for (int size = 0; size <= 5; size++) {
            int[] tiny = new int[size];
            for (int i = 0; i < size; i++) {
                tiny[i] = size - i; // Reverse order
            }
            int[] expected = tiny.clone();
            Arrays.sort(expected);

            Metrics metrics = new Metrics();
            MergeSort.sort(tiny, metrics);
            assertArrayEquals(expected, tiny, "Failed for size " + size);

            QuickSort.sort(tiny.clone(), metrics);
        }
    }

    @Test
    void testLargeNumbers() {
        int[] large = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1};
        int[] expected = large.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(large, metrics);
        assertArrayEquals(expected, large);

        QuickSort.sort(large.clone(), metrics);
    }

    @Test
    void testPointsWithSameCoordinates() {
        Point[] samePoints = {
                new Point(1, 1),
                new Point(1, 1),
                new Point(2, 2),
                new Point(1, 1)
        };

        Metrics metrics = new Metrics();
        double distance = ClosestPair.findClosestDistance(samePoints, metrics);

        assertEquals(0.0, distance, 1e-9);
    }
}