package com.clay.b_thread_basics;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExample {
    private final Path inputFile;
    private final Path outputFile;

    public ThreadExample(Path inputFile, Path outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void processFile() {
        try (BufferedReader reader = Files.newBufferedReader(inputFile);
             FileWriter writer = new FileWriter(outputFile.toFile())) {

            reader.lines()
                    .map(this::processLine)
                    .forEach(line -> {
                        try {
                            writer.write(line + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            System.out.println("File processing completed for: " + inputFile.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processLine(String line) {
        // Perform any desired computations or transformations on the line
        // For simplicity, we'll just convert the line to uppercase
        return line.toUpperCase();
    }

    public static void main(String[] args) {
        // Specify input and output file paths
        Path inputFile1 = Paths.get("src/main/java/com/clay/b_thread_basics/input1.txt");
        Path inputFile2 = Paths.get("src/main/java/com/clay/b_thread_basics/input2.txt");
        Path outputFile1 = Paths.get("src/main/java/com/clay/b_thread_basics/output1.txt");
        Path outputFile2 = Paths.get("src/main/java/com/clay/b_thread_basics/output2.txt");

        // Create an ExecutorService with a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit tasks for file processing
        executor.submit(() -> new ThreadExample(inputFile1, outputFile1).processFile());
        executor.submit(() -> new ThreadExample(inputFile2, outputFile2).processFile());

        // Shut down the ExecutorService after all tasks have completed
        executor.shutdown();
    }
}
