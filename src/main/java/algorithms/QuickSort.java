package algorithms;

import algorithms.metrics.DepthTracker;
import algorithms.metrics.Metrics;
import util.ArrayUtils;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        DepthTracker.track(() -> {
            quickSort(arr, 0, arr.length - 1, metrics, 0);
            return null;
        }, metrics);
    }

    private static void quickSort(int[] arr, int left, int right, Metrics metrics, int depth) {
        while (left < right) {
            if (right - left < 16) {
                ArrayUtils.insertionSort(arr, left, right);
                return;
            }

            int pivotIndex = randomizedPartition(arr, left, right, metrics);

            // Recurse on smaller partition first
            if (pivotIndex - left < right - pivotIndex) {
                quickSort(arr, left, pivotIndex - 1, metrics, depth + 1);
                left = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, right, metrics, depth + 1);
                right = pivotIndex - 1;
            }
        }
    }

    private static int randomizedPartition(int[] arr, int left, int right, Metrics metrics) {
        int randomIndex = left + random.nextInt(right - left + 1);
        return ArrayUtils.partition(arr, left, right, randomIndex);
    }
}
