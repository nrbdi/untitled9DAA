package algorithms.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriter {
    public static void writeMetrics(String filename, Map<String, String> data) throws IOException {
        try (FileWriter writer = new FileWriter(filename, true)) {
            if (data.containsKey("header")) {
                writer.write(data.get("header") + "\n");
            } else {
                writer.write(String.join(",", data.keySet()) + "\n");
                writer.write(String.join(",", data.values()) + "\n");
            }
        }
    }
}