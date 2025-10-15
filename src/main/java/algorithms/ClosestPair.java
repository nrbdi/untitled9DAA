package algorithms;

import algorithms.metrics.DepthTracker;
import algorithms.metrics.Metrics;
import util.Point;

import java.util.Arrays;

public class ClosestPair {

    public static double findClosestDistance(Point[] points, Metrics metrics) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, (a, b) -> Double.compare(a.x, b.x));
        metrics.recordAllocation(points.length * 32L); // Approximate Point size

        return DepthTracker.track(() ->
                closestDistance(pointsByX, 0, pointsByX.length - 1, metrics, 0), metrics);
    }

    private static double closestDistance(Point[] pointsByX, int left, int right, Metrics metrics, int depth) {
        int n = right - left + 1;

        if (n <= 3) {
            return bruteForce(pointsByX, left, right, metrics);
        }

        int mid = left + (right - left) / 2;
        Point midPoint = pointsByX[mid];

        double leftMin = closestDistance(pointsByX, left, mid, metrics, depth + 1);
        double rightMin = closestDistance(pointsByX, mid + 1, right, metrics, depth + 1);

        double minDistance = Math.min(leftMin, rightMin);

        // Check strip
        return Math.min(minDistance, stripClosest(pointsByX, left, right, midPoint, minDistance, metrics));
    }

    private static double bruteForce(Point[] points, int left, int right, Metrics metrics) {
        double minDistance = Double.POSITIVE_INFINITY;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = points[i].distanceTo(points[j]);
                metrics.recordComparison();
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }

    private static double stripClosest(Point[] pointsByX, int left, int right,
                                       Point midPoint, double minDistance, Metrics metrics) {
        // Collect points in the strip
        Point[] strip = new Point[right - left + 1];
        int stripSize = 0;

        for (int i = left; i <= right; i++) {
            if (Math.abs(pointsByX[i].x - midPoint.x) < minDistance) {
                strip[stripSize++] = pointsByX[i];
            }
        }

        // Sort strip by y-coordinate
        Arrays.sort(strip, 0, stripSize, (a, b) -> Double.compare(a.y, b.y));
        metrics.recordAllocation(stripSize * 32L);

        // Check only 7 neighbors for each point
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < minDistance; j++) {
                double dist = strip[i].distanceTo(strip[j]);
                metrics.recordComparison();
                if (dist < minDistance) {
                    minDistance = dist;
                }
                if (j - i > 7) break; // Safety check
            }
        }

        return minDistance;
    }

    // Utility method for validation
    public static double bruteForceClosest(Point[] points) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }
}