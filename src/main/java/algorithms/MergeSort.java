package algorithms;

import algorithms.metrics.DepthTracker;
import algorithms.metrics.Metrics;
import util.ArrayUtils;

public class MergeSort {
    private static final int CUTOFF = 16;

    public static void sort(int[] arr, Metrics metrics) {
        int[] buffer = new int[arr.length];
        DepthTracker.track(() -> {
            mergeSort(arr, 0, arr.length - 1, buffer, metrics, 0);
            return null;
        }, metrics);
    }

    private static void mergeSort(int[] arr, int left, int right, int[] buffer,
                                  Metrics metrics, int depth) {
        if (right - left <= CUTOFF) {
            ArrayUtils.insertionSort(arr, left, right);
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid, buffer, metrics, depth + 1);
        mergeSort(arr, mid + 1, right, buffer, metrics, depth + 1);

        merge(arr, left, mid, right, buffer, metrics);
    }

    private static void merge(int[] arr, int left, int mid, int right,
                              int[] buffer, Metrics metrics) {
        metrics.recordAllocation(0); // buffer reused

        System.arraycopy(arr, left, buffer, left, right - left + 1);

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.recordComparison();
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
        }

        while (j <= right) {
            arr[k++] = buffer[j++];
        }
    }
}
