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

    // map gain to classified String
    private static HashMap<Double, String> gainScore_and_classifiedString = new HashMap<Double, String>();
    private static ArrayList<Double> visited_gainScores = new ArrayList<Double>();

    private static String classified0_result;

    public Classifier(String[] inputRow, int columns, Node tree) {
        row = inputRow;
        numColumns = columns;
        trained_decisionTree = tree;

        classify(row);
    }

    /*
    Classifies row of data
    @param  row     row of data array
     */
    public static void classify(String[] row) {
        classify_iterateNextNode(row, trained_decisionTree, 183);
    }

    /*
    Iterates through decision tree while iterating through columns of row of data
    Updates entropies and gain scores
    @param  row     row of data array
     */
    public static void classify_iterateNextNode(String[] row, Node n, int column) {
        if (column < 266 - 3) {

            String current_classfied0 = checkHighestFrequency(n);

            double current_entropy = DecisionTree.calculateEntropy(n.RESISTANT_counter, n.COMPLETE_REMISSION_counter);
            if (current_entropy > 0 && (n.RESISTANT_counter + n.COMPLETE_REMISSION_counter) > 20) {
                entropy_and_classifiedString.put(current_entropy, current_classfied0);
                visited_entropies.add(current_entropy);
            }

            double current_gainScore = calculateGainScore(DecisionTree.calculateGain(n, n.left, n.right));
            if (current_gainScore == 1000000) {
                current_gainScore = 0;
            }
            gainScore_and_classifiedString.put(current_gainScore, current_classfied0);
            visited_gainScores.add(current_gainScore);

            if (row[column].equals("0")) {
                if (n.ifLeftPresent() == false) {
                    determineResult(n);
                    return;
                }
                classify_iterateNextNode(row, n.left, nextColumn(column));
            }
            else if (row[column].equals("1")) {
                if (n.ifRightPresent() == false) {
                    determineResult(n);
                    return;
                }
                classify_iterateNextNode(row, n.right, nextColumn(column));
            }
        }
    }

    /*
    Calculates gain from entropy of current node and its child nodes
    @param  parent   current node
    @param  left     left child node of current node
    @param  right    right child node of current node
    @return          gain
     */
    public static double calculateGain(Node parent, Node left, Node right) {

        if (left == null || right == null) {
            return 0;
        }

        double gain;

        double entropyParent = calculateEntropy(parent);
        double entropyLeft = calculateEntropy(left);
        double entropyRight = calculateEntropy(right);

        int sumParent = parent.RESISTANT_counter + parent.COMPLETE_REMISSION_counter;
        int sumLeft = left.RESISTANT_counter + left.COMPLETE_REMISSION_counter;
        int sumRight = right.RESISTANT_counter + right.COMPLETE_REMISSION_counter;

        gain = entropyParent - sumLeft / sumParent * entropyLeft - sumRight / sumParent * entropyRight;

        return gain;
    }

    /*
    Calculates gain score based on calculated gain
    @param  gain    gain calculated from entropy of current node and its child nodes
    @return         gain score
     */
    public static double calculateGainScore(double gain) {
        return gain * 1000000;
    }

    /*
    Calculate entropy based on distribution of outcome at current node
    Calculates based on given distributions of child nodes
    @param  a   distribution of one child node outcome
    @param  b   distribution of other child node outcome
    @return     entropy
     */
    public static double calculateEntropy(double a, double b) {
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
    }

    /*
    Calculate entropy based on distribution of outcome at current node
    Calculates based on given node
    @param  n   current node
    @return     entropy
     */
    public static double calculateEntropy(Node n) {
        double a = n.RESISTANT_counter;
        double b = n.COMPLETE_REMISSION_counter;
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
    }

    /*
    Determines log_2(n)
    @param  n       n in equation
    @return         log_2(n)
     */
    public static double log_base2(double n) {
        return Math.log(n) / Math.log(2);
    }

    /*
    Determines whether "RESISTANT" or "COMPLETE_REMISSION" occurs most frequently at particular node
    @param  n   current node
    @return     most frequent String
     */
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

    /*
    Determines classified result for row of data when reaching last node
    @param  n   current node
     */
    public static void determineResult(Node n) {

//        Collections.sort(visited_entropies);
//        double lowestEntropy = visited_entropies.get(0);
//        System.out.println(lowestEntropy);
//        classified0_result = entropy_and_classifiedString.get(lowestEntropy);
//
//        Collections.sort(visited_gainScores);
//        double highestGain = visited_gainScores.get(visited_gainScores.size() - 1);
//        System.out.printf("%.0f", highestGain);
//        System.out.println();
//        classified0_result = gainScore_and_classifiedString.get(highestGain);
//
        classified0_result = checkHighestFrequency(n);

//        double mostRecent = visited_entropies.get(visited_entropies.size() - 2);
//        classified0_result = entropy_and_classifiedString.get(mostRecent);

    }

    /*
    Returns first classified result
    @return     classified result
     */
    public static String getClassified0_result() {
        return classified0_result;
    }

    /*
    Determines next column that should be used for next level in decision tree
    Values are determined from gain score tests
    @param  n   current column index
    @return     next column index
     */
    public static int nextColumn(int n) {
        if (n == 183) {
            return 30;
        }
        else if (n == 30) {
            return 34;
        }
        else if (n == 34) {
            return 217;
        }
        else if (n == 217) {
            return 102;
        }
        else if (n == 102) {
            return 1;
        }
        else if (n == 182 || n == 29 || n == 33 || n == 216 || n == 101) {
            return n + 2;
        }
        else {
            return n + 1;
        }
    }

}