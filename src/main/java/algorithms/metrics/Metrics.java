package algorithms.metrics;

public class Metrics {
    private long comparisons;
    private long allocations;
    private int maxRecursionDepth;
    private long startTime;

    public Metrics() {
        reset();
    }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxRecursionDepth = 0;
        startTime = System.nanoTime();
    }

    public void recordComparison() { comparisons++; }
    public void recordAllocation(long bytes) { allocations += bytes; }
    public void recordRecursionDepth(int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);
    }

    public long getComparisons() { return comparisons; }
    public long getAllocations() { return allocations; }
    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public long getElapsedTime() { return System.nanoTime() - startTime; }
}
