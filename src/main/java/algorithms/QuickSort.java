package algorithms;

import algorithms.metrics.DepthTracker;
import algorithms.metrics.Metrics;
import util.ArrayUtils;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();
    private static final int CUTOFF = 16;

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) {
            if (arr != null && arr.length == 1) {
                metrics.recordComparison();
            }
            return;
        }

        // Shuffle for probabilistic guarantee (prevents worst-case on sorted input)
        ArrayUtils.shuffle(arr);

        DepthTracker.track(() -> {
            quickSort(arr, 0, arr.length - 1, metrics, 0);
            return null;
        }, metrics);
    }

    private static void quickSort(int[] arr, int left, int right, Metrics metrics, int depth) {
        while (left < right) {
            // Use insertion sort for small subarrays
            if (right - left < CUTOFF) {
                ArrayUtils.insertionSort(arr, left, right);
                return;
            }

            // Choose pivot and partition
            int pivotIndex = choosePivot(arr, left, right, metrics);

            // For arrays with many duplicates, use three-way partition
            if (hasManyDuplicates(arr, left, right)) {
                int[] equalRange = ArrayUtils.threeWayPartition(arr, left, right, pivotIndex);
                int leftEnd = equalRange[0] - 1;
                int rightStart = equalRange[1] + 1;

                // Recurse on smaller partition first
                if (leftEnd - left < right - rightStart) {
                    quickSort(arr, left, leftEnd, metrics, depth + 1);
                    left = rightStart;
                } else {
                    quickSort(arr, rightStart, right, metrics, depth + 1);
                    right = leftEnd;
                }
            } else {
                // Standard partition for diverse arrays
                pivotIndex = randomizedPartition(arr, left, right, metrics);

                // Recurse on smaller partition first for bounded stack depth
                if (pivotIndex - left < right - pivotIndex) {
                    quickSort(arr, left, pivotIndex - 1, metrics, depth + 1);
                    left = pivotIndex + 1;
                } else {
                    quickSort(arr, pivotIndex + 1, right, metrics, depth + 1);
                    right = pivotIndex - 1;
                }
            }
        }
    }

    private static int randomizedPartition(int[] arr, int left, int right, Metrics metrics) {
        return 0;
    }

    private static int choosePivot(int[] arr, int left, int right, Metrics metrics) {
        // Use median-of-three for better pivot selection
        if (right - left >= 2) {
            int mid = left + (right - left) / 2;

            // Sort left, mid, right
            if (arr[left] > arr[mid]) {
                ArrayUtils.swap(arr, left, mid);
            }
            if (arr[left] > arr[right]) {
                ArrayUtils.swap(arr, left, right);
            }
            if (arr[mid] > arr[right]) {
                ArrayUtils.swap(arr, mid, right);
            }

            // Use mid as pivot
            return mid;
        }

        // For very small arrays, use random pivot
        return left + random.nextInt(right - left + 1);
    }

    private static boolean hasManyDuplicates(int[] arr, int left, int right) {
        // Heuristic: if more than 30% of elements are the same, use three-way partition
        if (right - left < 10) return false;

        int sampleSize = Math.min(10, right - left + 1);
        int uniqueCount = 0;
        java.util.Set<Integer> seen = new java.util.HashSet<>();

        for (int i = 0; i < sampleSize; i++) {
            int index = left + random.nextInt(right - left + 1);
            if (seen.add(arr[index])) {
                uniqueCount++;
            }
        }

        return (double) uniqueCount / sampleSize < 0.7;
    }

    // randomizedPartition method remains the same...
}