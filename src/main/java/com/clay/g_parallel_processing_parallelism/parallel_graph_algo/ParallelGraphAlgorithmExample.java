package com.clay.g_parallel_processing_parallelism.parallel_graph_algo;

import java.util.*;
import java.util.concurrent.*;

/**
 * In this example, we have a graph represented using an adjacency list data structure. We want to perform a parallel Breadth-First Search (BFS) traversal on the graph starting from a specified vertex. The BFS algorithm visits all the vertices in the graph in a breadth-first manner, i.e., it visits all the neighbors of a vertex before moving on to the next level of vertices.
 *
 * The main logic of the parallelBFS method executes the parallel BFS algorithm. It starts with the initial vertex, adds it to the visited list, and enqueues it in a shared Queue. Then, it enters a loop where it processes vertices from the queue in parallel. Each vertex's neighbors are retrieved, and if a neighbor has not been visited, it is added to the visited list, enqueued in the shared Queue, and a BFSTask is submitted to the executor service for parallel execution. The BFSTask performs the BFS traversal starting from the neighbor vertex and returns the list of visited vertices. The main thread collects the results from all the tasks and adds them to the visited list. The process continues until the queue is empty, meaning all vertices have been processed.
 *
 * The Graph class represents the graph using an adjacency list, and the BFSTask class is a callable that performs the BFS traversal starting from a specific vertex. The BFSTask is executed by separate threads in parallel, allowing for faster exploration of the graph.
 */
public class ParallelGraphAlgorithmExample {

    public static void main(String[] args) {
        // Create a graph
        Graph graph = new Graph();
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(3, 7);
        graph.addEdge(4, 8);
        graph.addEdge(5, 9);

        int startVertex = 1;

        // Execute parallel BFS
        List<Integer> result = parallelBFS(graph, startVertex);
        System.out.println("Parallel BFS Result: " + result);
    }

    private static List<Integer> parallelBFS(Graph graph, int startVertex) {
        List<Integer> visited = new ArrayList<>();
        visited.add(startVertex);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            List<Future<List<Integer>>> futures = new ArrayList<>();

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                List<Integer> neighbors = graph.getNeighbors(vertex);

                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);

                        Callable<List<Integer>> callable = new BFSTask(graph, neighbor);
                        Future<List<Integer>> future = executorService.submit(callable);
                        futures.add(future);
                    }
                }
            }

            // Collect results from all threads
            for (Future<List<Integer>> future : futures) {
                try {
                    List<Integer> subResult = future.get();
                    visited.addAll(subResult);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executorService.shutdown();
        return visited;
    }

    private static class Graph {
        private Map<Integer, List<Integer>> adjacencyList;

        public Graph() {
            adjacencyList = new HashMap<>();
        }

        public void addEdge(int source, int destination) {
            adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
            adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>()).add(source);
        }

        public List<Integer> getNeighbors(int vertex) {
            return adjacencyList.getOrDefault(vertex, new ArrayList<>());
        }
    }

    private static class BFSTask implements Callable<List<Integer>> {
        private Graph graph;
        private int startVertex;

        public BFSTask(Graph graph, int startVertex) {
            this.graph = graph;
            this.startVertex = startVertex;
        }

        @Override
        public List<Integer> call() {
            List<Integer> visited = new ArrayList<>();
            visited.add(startVertex);

            Queue<Integer> queue = new LinkedList<>();
            queue.offer(startVertex);

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                List<Integer> neighbors = graph.getNeighbors(vertex);

                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }

            return visited;
        }
    }
}
