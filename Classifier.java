package com.eddylu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Classifier {

    private static String[] row;
    private static int numColumns;
    private static Node trained_decisionTree;

    ///// parameters

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

            ////////// update parameters

            if (row[column].equals("0")) {
                if (n.ifLeftPresent() == false) {
                    return;
                }
                classify_iterateNextNode(row, n.left, column + 1);
            }
            else if (row[column].equals("1")) {
                if (n.ifRightPresent() == false) {
                    return;
                }
                classify_iterateNextNode(row, n.right, column + 1);
            }
        }
    }

    public static double calculateGain(Node parent, Node left, Node right) {
        double gain;

        

        return gain;
    }

    public static double calculateEntropy(double a, double b) {
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
    }

    public static double calculateEntropy(Node n) {
        double a = n.RESISTANT_counter;
        double b = n.COMPLETE_REMISSION_counter;
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
    }

    public static double log_base2(double n) {
        return Math.log(n) / Math.log(2);
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

    public static String getClassified0_result() {
        return classified0_result;
    }

}