package com.company;

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

}