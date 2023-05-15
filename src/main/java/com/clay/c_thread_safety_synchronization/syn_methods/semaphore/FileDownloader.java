package com.clay.c_thread_safety_synchronization.syn_methods.semaphore;

import java.util.concurrent.Semaphore;

public class FileDownloader {
    private Semaphore downloadSlots;

    public FileDownloader(int maxConcurrentDownloads) {
        downloadSlots = new Semaphore(maxConcurrentDownloads);
    }

    public void downloadFile(String fileUrl) {
        try {
            downloadSlots.acquire(); // Acquire a download slot
            System.out.println("Downloading file: " + fileUrl);
            // Logic for downloading the file
            Thread.sleep(2000); // Simulating file download time
            System.out.println("File downloaded: " + fileUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            downloadSlots.release(); // Release the download slot
        }
    }
}
