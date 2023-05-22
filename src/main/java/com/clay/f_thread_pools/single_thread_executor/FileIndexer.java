package com.clay.f_thread_pools.single_thread_executor;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * In this example, we have a FileIndexer class that uses ExecutorService with newSingleThreadExecutor() to perform indexing of multiple directories and their files. The indexFiles() method takes an array of directory paths and submits each directory for indexing using the executor. The indexDirectory() method recursively traverses the directory structure, indexing files encountered. The indexFile() method performs the actual indexing operations on each file.
 *
 * The complex use case here involves indexing a large number of files within multiple directories. The single thread provided by newSingleThreadExecutor() ensures that the indexing tasks are executed sequentially, maintaining consistency and avoiding race conditions. The use of a single thread simplifies the synchronization and coordination of the indexing process.
 *
 * In the main() method, we create an instance of FileIndexer, specify the directories to be indexed, and invoke the indexFiles() method. Finally, we call executor.shutdown() to gracefully shutdown the executor service once all indexing tasks have been completed.
 *
 */
public class FileIndexer {
    private final ExecutorService executor;

    public FileIndexer() {
        executor = Executors.newSingleThreadExecutor();
    }

    public void indexFiles(String[] directories) {
        for (String directory : directories) {
            executor.submit(() -> {
                indexDirectory(new File(directory));
                System.out.println("Indexing completed for directory: " + directory);
            });
        }
    }

    private void indexDirectory(File directory) {
        System.out.println("Indexing directory: " + directory.getAbsolutePath() +
                " by " + Thread.currentThread().getName());

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        indexFile(file);
                    } else if (file.isDirectory()) {
                        indexDirectory(file);
                    }
                }
            }
        }
    }

    private void indexFile(File file) {
        System.out.println("Indexing file: " + file.getName() +
                " by " + Thread.currentThread().getName());

        // Perform file indexing operations
        // ...

        // Simulating indexing time
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileIndexer fileIndexer = new FileIndexer();

        // Simulating multiple directories to be indexed
        String[] directories = {"documents", "photos", "music"};

        fileIndexer.indexFiles(directories);

        fileIndexer.executor.shutdown();
    }
}
