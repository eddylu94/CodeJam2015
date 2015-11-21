package com.company;

public class DecisionTree {

    Node tree;

    public DecisionTree() {
        tree = new Node();
    }

    void addRow(String[] row) {

        Node n = tree;

        iterate(row, n, 0);
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
    }

}