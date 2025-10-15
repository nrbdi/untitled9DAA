
import algorithms.ClosestPair;
import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;
import util.Point;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {
    private final Random random = new Random(42);

    @Test
    void testClosestPairEmptyArray() {
        Point[] points = {};

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testClosestPairSinglePoint() {
        Point[] points = {new Point(1, 1)};

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testClosestPairTwoPoints() {
        Point[] points = {new Point(0, 0), new Point(1, 1)};
        double expected = Math.sqrt(2);

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairThreePoints() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(0, 1)
        };
        double expected = 1.0; // Distance between (0,0) and (0,1)

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairHorizontalLine() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0),
                new Point(3, 0)
        };
        double expected = 1.0;

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairVerticalLine() {
        Point[] points = {
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        };
        double expected = 1.0;

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairKnownSet() {
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(3, 3),
                new Point(4, 4)
        };
        double expected = Math.sqrt(2);

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairRandomSmall() {
        Point[] points = generateRandomPoints(50);

        double expected = ClosestPair.bruteForceClosest(points);
        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairRandomMedium() {
        Point[] points = generateRandomPoints(200);

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);
        double bruteForce = ClosestPair.bruteForceClosest(points);

        assertEquals(bruteForce, result, 1e-9);
    }

    @Test
    void testClosestPairDuplicatePoints() {
        Point[] points = {
                new Point(1, 1),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)
        };
        double expected = 0.0;

        Metrics metrics = new Metrics();
        double result = ClosestPair.findClosestDistance(points, metrics);

        assertEquals(expected, result, 1e-9);
    }

    @Test
    void testClosestPairDepthBounded() {
        Point[] points = generateRandomPoints(1000);

        Metrics metrics = new Metrics();
        ClosestPair.findClosestDistance(points, metrics);

        double log2n = Math.log(1000) / Math.log(2);
        assertTrue(metrics.getMaxRecursionDepth() <= log2n + 2,
                "Depth: " + metrics.getMaxRecursionDepth() + " should be <= " + (log2n + 2));
    }

    private Point[] generateRandomPoints(int n) {
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }
}
