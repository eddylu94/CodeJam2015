package com.eddylu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestDecisionTree {

    Node tree;

    public TestDecisionTree() {
        tree = new Node();
    }

    void test_addRow(String[] row) {
        Node n = tree;
        iterate(row, n, 1);
    }

    void iterate(String[] row, Node n, int column) {

        if (row[3].equals("RESISTANT")) {
            n.RESISTANT_counter++;
        }
        else if (row[3].equals("COMPLETE_REMISSION")) {
            n.COMPLETE_REMISSION_counter++;
        }
        else {
            System.out.println("Error with Outcome0");
        }
        iterateNextNode(row, n, column);
    }

    void iterateNextNode(String[] row, Node n, int column) {
        if (column < 5) {
            if (row[column].equals("0")) {
                if (n.ifLeftPresent() == false) {
                    Node newNode = new Node();
                    n.addLeftNode(newNode);
                }
                iterate(row, n.left, column + 1);
            }
            else if (row[column].equals("1")) {
                if (n.ifRightPresent() == false) {
                    Node newNode = new Node();
                    n.addRightNode(newNode);
                }
                iterate(row, n.right, column + 1);
            }
        }
    }

    void test_printPartialTree(int limit, int column_assignment) {
        Node n = tree;
        System.out.println("Printing tree:");
        System.out.println();
        printPartialTreeLevel(n, 0, limit, column_assignment);
        System.out.println();
    }

    void printPartialTreeLevel(Node n, int level, int limit, int column_assignment) {

        if (level < limit) {
            for (int i = 0; i < level; i++) {
                System.out.print("---");
            }
            System.out.print(n.RESISTANT_counter + "," + n.COMPLETE_REMISSION_counter);
            System.out.printf("   %.3f", calculateEntropy(n.RESISTANT_counter, n.COMPLETE_REMISSION_counter));

            // stop printing to reduce execution time during testing
            if (level == 8) {
                System.out.println();
                return;
            }

            double current_gainScore = calculateGainScore(calculateGain(n, n.left, n.right));
            System.out.printf("   %.0f", current_gainScore);

            if (level == 0) {
                try {

                    BufferedWriter out = new BufferedWriter(
                            new FileWriter("gainScores_level0.txt", true));

                    String output = column_assignment + " "
                            + (int)current_gainScore;

                    out.append(output);
                    out.newLine();

                    out.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println();

            if (n.ifLeftPresent() && n.ifRightPresent()) {
                printPartialTreeLevel(n.left, level + 1, limit, column_assignment);
                printPartialTreeLevel(n.right, level + 1, limit, column_assignment);
            }
            else if (n.ifLeftPresent() && n.ifRightPresent() == false) {
                printPartialTreeLevel(n.left, level + 1, limit, column_assignment);
            }
            else if (n.ifLeftPresent() == false && n.ifRightPresent()) {
                printPartialTreeLevel(n.right, level + 1, limit, column_assignment);
            }
        }
    }


    void printTree() {
        Node n = tree;
        System.out.println("Printing tree:");
        System.out.println();
        printLevel(n, 0);
        System.out.println();
    }

    void printLevel(Node n, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("---");
        }
        System.out.print(n.RESISTANT_counter + "," + n.COMPLETE_REMISSION_counter);
        System.out.printf("   %.3f", calculateEntropy(n.RESISTANT_counter, n.COMPLETE_REMISSION_counter));

        // stop printing to reduce execution time during testing
        if (level == 8) {
            System.out.println();
            return;
        }

        System.out.printf("   %.0f", calculateGainScore(calculateGain(n, n.left, n.right)));
        System.out.println();

        if (n.ifLeftPresent() && n.ifRightPresent()) {
            printLevel(n.left, level + 1);
            printLevel(n.right, level + 1);
        }
        else if (n.ifLeftPresent() && n.ifRightPresent() == false) {
            printLevel(n.left, level + 1);
        }
        else if (n.ifLeftPresent() == false && n.ifRightPresent()) {
            printLevel(n.right, level + 1);
        }
    }

    public static double calculateGainScore(double gain) {
        return gain * 1000000;
    }

    public static double calculateGain(Node parent, Node left, Node right) {

        if (left == null || right == null) {
            return 0;
        }

        double gain;

        double entropyParent = calculateEntropy(parent);
        double entropyLeft = calculateEntropy(left);
        double entropyRight = calculateEntropy(right);

//        System.out.println();
//        System.out.println("calculateEntropy(parent): " + calculateEntropy(parent));
//        System.out.println("calculateEntropy(left): " + calculateEntropy(left));
//        System.out.println("calculateEntropy(right): " + calculateEntropy(right));

        int sumParent = parent.RESISTANT_counter + parent.COMPLETE_REMISSION_counter;
        int sumLeft = left.RESISTANT_counter + left.COMPLETE_REMISSION_counter;
        int sumRight = right.RESISTANT_counter + right.COMPLETE_REMISSION_counter;

        gain = entropyParent - (double)sumLeft / (double)sumParent * entropyLeft - (double)sumRight / (double)sumParent * entropyRight;

        return gain;
    }

    public static double calculateEntropy(double a, double b) {
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        if (a != 0 && b != 0) {
            return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
        }
        return 0;
    }

    public static double calculateEntropy(Node n) {
        double a = n.RESISTANT_counter;
        double b = n.COMPLETE_REMISSION_counter;
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        if (a != 0 && b != 0) {
            return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
        }
        return 0;
    }

    public static double log_base2(double n) {
        return Math.log(n) / Math.log(2);
    }

}