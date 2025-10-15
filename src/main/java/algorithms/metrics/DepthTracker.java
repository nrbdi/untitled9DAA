package algorithms.metrics;

public class DepthTracker {
    private static final ThreadLocal<Integer> currentDepth = ThreadLocal.withInitial(() -> 0);

    public static <T> T track(RunnableWithReturn<T> operation, Metrics metrics) {
        int depth = currentDepth.get();
        metrics.recordRecursionDepth(depth);

        currentDepth.set(depth + 1);
        try {
            return operation.run();
        } finally {
            currentDepth.set(depth);
        }
    }

    @FunctionalInterface
    public interface RunnableWithReturn<T> {
        T run();
    }
}

