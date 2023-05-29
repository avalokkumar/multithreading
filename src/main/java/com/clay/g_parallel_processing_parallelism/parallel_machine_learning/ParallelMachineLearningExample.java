package com.clay.g_parallel_processing_parallelism.parallel_machine_learning;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;
//import org.apache.commons.math3.util.ThreadSafe;

import libsvm.*;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

//@ThreadSafe
public class ParallelMachineLearningExample {

    public static void main(String[] args) {
        // Generate a synthetic dataset for classification
        Pair<List<RealVector>, double[]> dataset = generateDataset(1000, 20);
        List<RealVector> X = dataset.getFirst();
        double[] y = dataset.getSecond();

        // Split the dataset into training and testing sets
        int[] indices = IntStream.range(0, X.size()).toArray();
        int[][] trainTestSplit = splitTrainTest(indices, 0.8);
        int[] trainIndices = trainTestSplit[0];
        int[] testIndices = trainTestSplit[1];
        List<RealVector> XTrain = Arrays.stream(trainIndices)
                .mapToObj(i -> X.get(i))
                .collect(Collectors.toList());
        double[] yTrain = Arrays.stream(trainIndices)
                .mapToDouble(i -> y[i])
                .toArray();
        List<RealVector> XTest = Arrays.stream(testIndices)
                .mapToObj(i -> X.get(i))
                .collect(Collectors.toList());
        double[] yTest = Arrays.stream(testIndices)
                .mapToDouble(i -> y[i])
                .toArray();

        // Train SVM models in parallel
        List<svm_model> parallelModels = trainParallelSVM(XTrain, yTrain);

        // Use the parallel ensemble model to make predictions on the test set
        double[] ensemblePredictions = parallelPredict(XTest, parallelModels);

        // Evaluate the accuracy of the ensemble predictions
        double accuracy = evaluateAccuracy(ensemblePredictions, yTest);
        System.out.printf("Ensemble accuracy: %.2f%%\n", accuracy * 100);
    }

    private static Pair<List<RealVector>, double[]> generateDataset(int numSamples, int numFeatures) {
        double[][] data = new double[numSamples][numFeatures];
        double[] labels = new double[numSamples];

        // Generate random data and labels for classification
        // Replace this with your own dataset generation logic
        for (int i = 0; i < numSamples; i++) {
            RealVector vector = new ArrayRealVector(numFeatures);
            for (int j = 0; j < numFeatures; j++) {
                data[i][j] = Math.random();
                vector.setEntry(j, data[i][j]);
            }
            labels[i] = Math.random() < 0.5 ? -1.0 : 1.0;
        }

        return new Pair<>(Arrays.stream(data).map(ArrayRealVector::new).collect(Collectors.toList()), labels);
    }

    private static int[][] splitTrainTest(int[] indices, double trainRatio) {
        int numTrain = (int) (indices.length * trainRatio);
        int[] trainIndices = Arrays.copyOf(indices, numTrain);
        int[] testIndices = Arrays.copyOfRange(indices, numTrain, indices.length);
        return new int[][]{trainIndices, testIndices};
    }

    private static List<svm_model> trainParallelSVM(List<RealVector> XTrain, double[] yTrain) {
        int numCores = Runtime.getRuntime().availableProcessors();
        int batchSize = XTrain.size() / numCores;

        ForkJoinPool forkJoinPool = new ForkJoinPool(numCores);
        List<svm_model> models = forkJoinPool.submit(() -> IntStream.range(0, numCores)
                .parallel()
                .mapToObj(i -> {
                    int start = i * batchSize;
                    int end = start + batchSize;
                    List<RealVector> subsetX = XTrain.subList(start, end);
                    double[] subsetY = Arrays.copyOfRange(yTrain, start, end);
                    return trainSVM(subsetX, subsetY);
                })
                .collect(Collectors.toList())).join();

        forkJoinPool.shutdown();
        return models;
    }

    private static svm_model trainSVM(List<RealVector> XTrain, double[] yTrain) {
        svm_problem prob = new svm_problem();
        prob.l = XTrain.size();
        prob.x = new svm_node[prob.l][];
        prob.y = yTrain;

        for (int i = 0; i < XTrain.size(); i++) {
            RealVector vector = XTrain.get(i);
            prob.x[i] = new svm_node[vector.getDimension()];
            for (int j = 0; j < vector.getDimension(); j++) {
                svm_node node = new svm_node();
                node.index = j + 1;
                node.value = vector.getEntry(j);
                prob.x[i][j] = node;
            }
        }

        svm_parameter param = new svm_parameter();
        param.probability = 1;
        param.kernel_type = svm_parameter.LINEAR;
        svm.svm_set_print_string_function(s -> {
        });

        return svm.svm_train(prob, param);
    }

    private static double[] parallelPredict(List<RealVector> XTest, List<svm_model> models) {
        int numCores = Runtime.getRuntime().availableProcessors();

        ForkJoinPool forkJoinPool = new ForkJoinPool(numCores);
        double[][] predictions = forkJoinPool.submit(() -> IntStream.range(0, numCores)
                .parallel()
                .mapToObj(i -> {
                    int start = i * XTest.size() / numCores;
                    int end = (i + 1) * XTest.size() / numCores;
                    List<RealVector> subsetX = XTest.subList(start, end);
                    return predictSubset(subsetX, models);
                })
                .toArray(double[][]::new)).join();

        forkJoinPool.shutdown();
        return Arrays.stream(predictions)
                .flatMapToDouble(Arrays::stream)
                .toArray();
    }


    private static double[] predictSubset(List<RealVector> XTest, List<svm_model> models) {
        double[][] predictions = new double[models.size()][];
        for (int i = 0; i < models.size(); i++) {
            svm_model model = models.get(i);
            predictions[i] = new double[XTest.size()];

            for (int j = 0; j < XTest.size(); j++) {
                RealVector vector = XTest.get(j);
                svm_node[] nodes = new svm_node[vector.getDimension()];
                for (int k = 0; k < vector.getDimension(); k++) {
                    svm_node node = new svm_node();
                    node.index = k + 1;
                    node.value = vector.getEntry(k);
                    nodes[k] = node;
                }
                double prediction = svm.svm_predict(model, nodes);
                predictions[i][j] = prediction;
            }
        }
        return combinePredictions(predictions);
    }

    private static double[] combinePredictions(double[][] predictions) {
        int numSamples = predictions[0].length;
        int numModels = predictions.length;
        double[] ensemblePredictions = new double[numSamples];

        for (int i = 0; i < numSamples; i++) {
            double sum = 0.0;
            for (int j = 0; j < numModels; j++) {
                sum += predictions[j][i];
            }
            ensemblePredictions[i] = Precision.round(sum / numModels, 2);
        }

        return ensemblePredictions;
    }


    /*private static double[] predictSubset(List<RealVector> XTest, List<svm_model> models) {
        double[][] predictions = new double[models.size()][];
        for (int i = 0; i < models.size(); i++) {
            svm_model model = models.get(i);
            predictions[i] = new double[XTest.size()];

            for (int j = 0; j < XTest.size(); j++) {
                RealVector vector = XTest.get(j);
                svm_node[] nodes = new svm_node[vector.getDimension()];
                for (int k = 0; k < vector.getDimension(); k++) {
                    svm_node node = new svm_node();
                    node.index = k + 1;
                    node.value = vector.getEntry(k);
                    nodes[k] = node;
                }
                double prediction = svm.svm_predict(model, nodes);
                predictions[i][j] = prediction;
            }
        }
        return combinePredictions(predictions);
    }

    private static double[] combinePredictions(double[][] predictions) {
        int numSamples = predictions[0].length;
        int numModels = predictions.length;
        double[] ensemblePredictions = new double[numSamples];

        for (int i = 0; i < numSamples; i++) {
            double sum = 0.0;
            for (int j = 0; j < numModels; j++) {
                sum += predictions[j][i];
            }
            ensemblePredictions[i] = Precision.round(sum / numModels, 2);
        }

        return ensemblePredictions;
    }*/

    private static double evaluateAccuracy(double[] predictions, double[] labels) {
        int numCorrect = 0;
        for (int i = 0; i < predictions.length; i++) {
            if (predictions[i] == labels[i]) {
                numCorrect++;
            }
        }
        return (double) numCorrect / predictions.length;
    }
}
