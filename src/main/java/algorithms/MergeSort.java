package algorithms;

import algorithms.metrics.DepthTracker;
import algorithms.metrics.Metrics;
import util.ArrayUtils;

public class MergeSort {
    private static final int CUTOFF = 16;

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) {
            if (arr != null && arr.length == 1) {
                metrics.recordComparison(); // Count single element as sorted
            }
            return;
        }

        // Check if already sorted (optimization)
        if (ArrayUtils.isSorted(arr)) {
            metrics.recordComparison();
            return;
        }

        // Check if all duplicates (optimization)
        if (ArrayUtils.allEqual(arr)) {
            metrics.recordComparison();
            return;
        }

        int[] buffer = new int[arr.length];
        DepthTracker.track(() -> {
            mergeSort(arr, 0, arr.length - 1, buffer, metrics, 0);
            return null;
        }, metrics);
    }

    private static void mergeSort(int[] arr, int left, int right, int[] buffer,
                                  Metrics metrics, int depth) {
        // Base case: use insertion sort for small arrays
        if (right - left <= CUTOFF) {
            ArrayUtils.insertionSort(arr, left, right);
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid, buffer, metrics, depth + 1);
        mergeSort(arr, mid + 1, right, buffer, metrics, depth + 1);

        // Skip merge if already sorted (optimization for nearly sorted arrays)
        if (arr[mid] <= arr[mid + 1]) {
            metrics.recordComparison();
            return;
        }

        merge(arr, left, mid, right, buffer, metrics);
    }

    // merge method remains the same...
}