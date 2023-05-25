package com.clay.g_parallel_processing_parallelism.data_parallelism;

import java.util.Arrays;
import java.util.List;

public class DataParallelismExample {

    public static void main(String[] args) {
        // Simulating a large dataset of sales for different regions
        List<Integer> salesData = Arrays.asList(1000, 2000, 1500, 3000, 2500, 1800, 2200, 3500, 2800, 4000);

        // Calculate total sales using data parallelism
        int totalSales = calculateTotalSales(salesData);

        System.out.println("Total Sales: " + totalSales);
    }

    private static int calculateTotalSales(List<Integer> salesData) {
        // Use parallel stream to process the sales data in parallel
        return salesData.parallelStream()
                .mapToInt(sale -> {
                    // Simulating a complex computation for each sale
                    simulateComputation();

                    // Return the sale amount
                    return sale;
                })
                .sum();
    }

    private static void simulateComputation() {
        // Simulating a complex computation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
