package com.eddylu;

import java.util.ArrayList;
import java.util.HashMap;

public class Classifier {

    private static String[] row;
    private static int numColumns;
    private static Node trained_decisionTree;

    // map entropy to classified String
    HashMap<Integer, String> entropy_and_classifiedString = new HashMap<Integer, String>();
    ArrayList<Integer> visited_entropies = new ArrayList<Integer>();

    String result;

    public Classifier(String[] inputRow, int columns, Node tree) {
        row = inputRow;
        numColumns = columns;
        trained_decisionTree = tree;
    }

    public static String classify(String[] row) {
        classify_iterateNextNode(row, trained_decisionTree, 1);
    }

    public static void classify_iterateNextNode(String[] row, Node n, int column) {
        if (column < 266 - 3) {
            if (row[column].equals("0")) {
                if (n.ifLeftPresent() == false) {

                }
                classify_iterateNextNode(row, n.left, column + 1);
            }
            else if (row[column].equals("1")) {
                if (n.ifRightPresent() == false) {

                }
                classify_iterateNextNode(row, n.right, column + 1);
            }
        }
    }

    void determineResult;

}