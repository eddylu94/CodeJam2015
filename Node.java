package com.eddylu;

public class Node {

    int RESISTANT_counter = 0;
    int COMPLETE_REMISSION_counter = 0;
    int counter = 0;
    Node left;
    Node right;

    public boolean ifLeftPresent() {
        if (this.left == null) {
            return false;
        }
        return true;
    }

    public boolean ifRightPresent() {
        if (this.right == null) {
            return false;
        }
        return true;
    }

    public void addLeftNode(Node n) {
        this.left = n;
    }

    public void addRightNode(Node n) {
        this.right = n;
    }

    void printTree() {
        Node n = this;
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