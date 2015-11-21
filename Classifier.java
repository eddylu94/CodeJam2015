package com.eddylu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Classifier {

    private static String[] row;
    private static int numColumns;
    private static Node trained_decisionTree;

    // map entropy to classified String
    private static HashMap<Double, String> entropy_and_classifiedString = new HashMap<Double, String>();
    private static ArrayList<Double> visited_entropies = new ArrayList<Double>();

    private static String classified0_result;

    public Classifier(String[] inputRow, int columns, Node tree) {
        row = inputRow;
        numColumns = columns;
        trained_decisionTree = tree;

        classify(row);
    }

    public static void classify(String[] row) {
        classify_iterateNextNode(row, trained_decisionTree, 1);
    }

    public static void classify_iterateNextNode(String[] row, Node n, int column) {
        if (column < 266 - 3) {

            double current_entropy = DecisionTree.calculateEntropy(n.RESISTANT_counter, n.COMPLETE_REMISSION_counter);
            String current_classfied0 = checkHighestFrequency(n);

            entropy_and_classifiedString.put(current_entropy, current_classfied0);
            visited_entropies.add(current_entropy);

            if (row[column].equals("0")) {
                if (n.ifLeftPresent() == false) {
                    determineResult();
                    return;
                }
                classify_iterateNextNode(row, n.left, column + 1);
            }
            else if (row[column].equals("1")) {
                if (n.ifRightPresent() == false) {
                    determineResult();
                    return;
                }
                classify_iterateNextNode(row, n.right, column + 1);
            }
        }
    }

    public static String checkHighestFrequency(Node n) {
        String current_classfied0;
        if (n.RESISTANT_counter > n.COMPLETE_REMISSION_counter) {
            current_classfied0 = "RESISTANT";
        }
        else {
            current_classfied0 = "COMPLETE_REMISSION";
        }
        return current_classfied0;
    }

    public static void determineResult() {
        Collections.sort(visited_entropies);
        double highestEntropy = visited_entropies.get(0);
        classified0_result = entropy_and_classifiedString.get(highestEntropy);
    }

    public static String getClassified0_result() {
        return classified0_result;
    }

}