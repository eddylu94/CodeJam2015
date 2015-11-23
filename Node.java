package com.eddylu;

public class Node {

    int RESISTANT_counter = 0;
    int COMPLETE_REMISSION_counter = 0;
    int counter = 0;
    Node left;
    Node right;

    /*
    Checks if there is a left child node
    @return if left child node exists
     */
    public boolean ifLeftPresent() {
        if (this.left == null) {
            return false;
        }
        return true;
    }

    /*
    Checks if there is a right child node
    @return if right child node exists
     */
    public boolean ifRightPresent() {
        if (this.right == null) {
            return false;
        }
        return true;
    }

    /*
    Adds left child node
    @param n    node that will have child node added to it
     */
    public void addLeftNode(Node n) {
        this.left = n;
    }

    /*
    Adds right child node
    @param n    node that will have child node added to it
     */
    public void addRightNode(Node n) {
        this.right = n;
    }

    /*
    Prints full decision tree
     */
    void printTree() {
        Node n = this;
        System.out.println("Printing tree:");
        System.out.println();
        printLevel(n, 0);
        System.out.println();
    }

    /*
    Prints information in regards to specific node at certain level of decision tree
    @param n        current Node
    @param level    current level
     */
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
    Determines log_2(n)
    @param  n       n in equation
    @return         log_2(n)
     */
    public static double log_base2(double n) {
        return Math.log(n) / Math.log(2);
    }

}