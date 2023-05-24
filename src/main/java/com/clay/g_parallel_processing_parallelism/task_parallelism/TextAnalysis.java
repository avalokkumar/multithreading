package com.clay.g_parallel_processing_parallelism.task_parallelism;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TextAnalysis {
    
    public static void main(String[] args) {
        // Get a list of documents to process
        List<String> documents = getDocuments();

        // Create a thread pool with a fixed number of threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Create a concurrent map to store the results
        Map<String, Integer> wordCounts = new ConcurrentHashMap<>();
        Map<String, Double> sentimentScores = new ConcurrentHashMap<>();

        // Process documents in parallel using task parallelism
        for (String document : documents) {
            executor.submit(() -> {
                int count = countWords(document); // Count words in the document
                wordCounts.put(document, count); // Store the word count result

                double score = analyzeSentiment(document); // Analyze sentiment of the document
                sentimentScores.put(document, score); // Store the sentiment score result
            });
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Waiting for all tasks to complete
        }

        // Print the results
        for (String document : documents) {
            System.out.println("Document: " + document);
            System.out.println("Word Count: " + wordCounts.get(document));
            System.out.println("Sentiment Score: " + sentimentScores.get(document));
            System.out.println();
        }
    }

    private static List<String> getDocuments() {
        // Simulated method to fetch a list of documents to process
        List<String> documents = new ArrayList<>();
        documents.add("document1.txt");
        documents.add("document2.txt");
        documents.add("document3.txt");
        // Add more documents to the list
        return documents;
    }

    private static int countWords(String document) {
        // Simulated method to count words in a document
        // Perform actual word counting operations
        return document.split("\\s+").length;
    }

    private static double analyzeSentiment(String document) {
        // Simulated method to analyze sentiment of a document
        // Perform actual sentiment analysis operations
        return Math.random() * 10; // Return a random sentiment score
    }
}
