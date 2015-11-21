package com.eddylu;

public class DecisionTree {

    Node tree;

    public DecisionTree() {
        tree = new Node();
    }

    void addRow(String[] row) {
        Node n = tree;
        iterate(row, n, 1);
    }

    void iterate(String[] row, Node n, int column) {

        if (row[266].equals("RESISTANT")) {
            n.RESISTANT_counter++;
        }
        else if (row[266].equals("COMPLETE_REMISSION")) {
            n.COMPLETE_REMISSION_counter++;
        }
        else {
            System.out.println("Error with Outcome0");
        }
        iterateNextNode(row, n, column);
    }

    void iterateNextNode(String[] row, Node n, int column) {
        if (column < 266) {
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
        System.out.println(n.RESISTANT_counter + "," + n.COMPLETE_REMISSION_counter
            + "   " + calculateEntropy(n.RESISTANT_counter, n.COMPLETE_REMISSION_counter));

        // stop printing to reduce execution time during testing
        if (level == 8) {
            return;
        }

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

    public static double calculateEntropy(double a, double b) {
        double frac1 = a / (a + b);
        double frac2 = b / (a + b);
        return - frac1 * log_base2(frac1) - frac2 * log_base2(frac2);
    }

    public static double log_base2(double n) {
        return Math.log(n) / Math.log(2);
    }

}