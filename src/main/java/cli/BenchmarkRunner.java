package cli;

import algorithms.*;
import algorithms.metrics.CSVWriter;
import algorithms.metrics.Metrics;
import util.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("header")) {
            writeHeader();
            return;
        }

        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};
        int trials = 5;

        for (int size : sizes) {
            for (int trial = 0; trial < trials; trial++) {
                benchmarkAlgorithms(size, trial);
            }
        }
    }

    private static void writeHeader() throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("header", "algorithm,n,trial,time_ns,comparisons,allocations_bytes,max_depth");
        CSVWriter.writeMetrics("benchmark_results.csv", header);
    }

    private static void benchmarkAlgorithms(int n, int trial) throws IOException {
        Random random = new Random();

        // Generate test data
        int[] arr = random.ints(n).toArray();
        Point[] points = generateRandomPoints(n, random);

        // Test MergeSort
        testAlgorithm("MergeSort", arr.clone(), (a, m) -> MergeSort.sort(a, m), n, trial);

        // Test QuickSort
        testAlgorithm("QuickSort", arr.clone(), (a, m) -> QuickSort.sort(a, m), n, trial);

        // Test DeterministicSelect
        if (n > 0) {
            Metrics metrics = new Metrics();
            int k = random.nextInt(n);
            int[] selectArr = arr.clone();
            DeterministicSelect.select(selectArr, k, metrics);
            writeMetrics("DeterministicSelect", n, trial, metrics);
        }

        // Test ClosestPair
        Metrics metrics = new Metrics();
        ClosestPair.findClosestDistance(points, metrics);
        writeMetrics("ClosestPair", n, trial, metrics);
    }

    private static void testAlgorithm(String name, int[] arr, Algorithm algorithm, int n, int trial) throws IOException {
        Metrics metrics = new Metrics();
        algorithm.run(arr, metrics);
        writeMetrics(name, n, trial, metrics);
    }

    private static Point[] generateRandomPoints(int n, Random random) {
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    private static void writeMetrics(String algorithm, int n, int trial, Metrics metrics) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("algorithm", algorithm);
        data.put("n", String.valueOf(n));
        data.put("trial", String.valueOf(trial));
        data.put("time_ns", String.valueOf(metrics.getElapsedTime()));
        data.put("comparisons", String.valueOf(metrics.getComparisons()));
        data.put("allocations_bytes", String.valueOf(metrics.getAllocations()));
        data.put("max_depth", String.valueOf(metrics.getMaxRecursionDepth()));

        CSVWriter.writeMetrics("benchmark_results.csv", data);
    }

    interface Algorithm {
        void run(int[] arr, Metrics metrics);
    }
}