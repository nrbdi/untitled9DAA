package util;

import java.util.Random;

public class ArrayUtils {
    private static final Random random = new Random();

    public static void swap(int[] arr, int i, int j) {
        if (arr == null || i == j || i < 0 || j < 0 || i >= arr.length || j >= arr.length) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void shuffle(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    public static int partition(int[] arr, int left, int right, int pivotIndex) {
        if (left > right) return left;

        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, right, storeIndex);
        return storeIndex;
    }

    public static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1) return true;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean allEqual(int[] arr) {
        if (arr == null || arr.length <= 1) return true;

        int first = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != first) {
                return false;
            }
        }
        return true;
    }

    // Three-way partition for better duplicate handling (Dutch National Flag)
    public static int[] threeWayPartition(int[] arr, int left, int right, int pivotIndex) {
        if (left > right) return new int[]{left, left};

        int pivot = arr[pivotIndex];
        int i = left, j = left, k = right;

        while (j <= k) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
                j++;
            } else if (arr[j] > pivot) {
                swap(arr, j, k);
                k--;
            } else {
                j++;
            }
        }
        return new int[]{i, k}; // range of elements equal to pivot
    }
}
