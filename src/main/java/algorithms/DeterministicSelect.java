package algorithms;

import algorithms.metrics.DepthTracker;
import algorithms.metrics.Metrics;
import util.ArrayUtils;

import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] arr, int k, Metrics metrics) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("k must be between 0 and " + (arr.length - 1));
        }

        return DepthTracker.track(() -> deterministicSelect(arr.clone(), 0, arr.length - 1, k, metrics, 0), metrics);
    }

    private static int deterministicSelect(int[] arr, int left, int right, int k, Metrics metrics, int depth) {
        while (true) {
            if (left == right) return arr[left];

            int pivotIndex = medianOfMedians(arr, left, right, metrics);
            pivotIndex = ArrayUtils.partition(arr, left, right, pivotIndex);

            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics) {
        int n = right - left + 1;

        if (n <= 5) {
            return medianOfFive(arr, left, right, metrics);
        }

        // Group by 5 and find medians
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);
            medians[i] = medianOfFive(arr, groupLeft, groupRight, metrics);
        }

        // Recursively find median of medians
        return deterministicSelect(medians, 0, numGroups - 1, numGroups / 2, metrics, 0);
    }

    private static int medianOfFive(int[] arr, int left, int right, Metrics metrics) {
        int size = right - left + 1;
        int[] temp = Arrays.copyOfRange(arr, left, right + 1);
        metrics.recordAllocation(temp.length * 4L);

        Arrays.sort(temp);
        int medianValue = temp[size / 2];

        // Find original index of median value
        for (int i = left; i <= right; i++) {
            metrics.recordComparison();
            if (arr[i] == medianValue) {
                return i;
            }
        }
        return left;
    }
}
