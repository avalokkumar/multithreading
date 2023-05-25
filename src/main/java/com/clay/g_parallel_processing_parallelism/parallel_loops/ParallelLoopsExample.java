package com.clay.g_parallel_processing_parallelism.parallel_loops;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ParallelLoopsExample {

    public static void main(String[] args) {
        // Specify the directory containing the files to process
        String directoryPath = "src/main/java/com/clay/g_parallel_processing_parallelism/parallel_loops/data";

        try {
            // Get a list of files in the directory
            List<File> files = getFilesFromDirectory(directoryPath);

            // Perform text analysis on each file in parallel
            processFilesInParallel(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<File> getFilesFromDirectory(String directoryPath) throws IOException {
        // Retrieve all files from the specified directory
        return Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    private static void processFilesInParallel(List<File> files) {
        // Create an AtomicInteger to keep track of the total word count
        AtomicInteger totalWordCount = new AtomicInteger(0);

        // Process each file in parallel using parallelStream()
        files.parallelStream().forEach(file -> {
            try {
                // Read the content of the file
                String content = new String(Files.readAllBytes(file.toPath()));

                // Split the content into words and count the number of words
                int wordCount = countWords(content);

                // Update the total word count using atomic operations
                totalWordCount.addAndGet(wordCount);

                // Print the file name and its word count
                System.out.println("File: " + file.getName() + ", Word Count: " + wordCount +
                        ", Thread: " + Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Print the total word count
        System.out.println("Total Word Count: " + totalWordCount.get());
    }

    private static int countWords(String content) {
        // Split the content into words using whitespace as the delimiter
        String[] words = content.split("\\s+");

        // Return the number of words
        return words.length;
    }
}
