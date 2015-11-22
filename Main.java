package com.eddylu;

import java.io.*;

public class Main {

    private static String[][] data;
    private static int numRows;
    private static int numColumns;

    private static int correctResponses = 0;
    private static int incorrectResponses = 0;

    public static void main(String[] args) throws IOException {
        numRows = countRows();
        numColumns = countColumns();
        System.out.println();

        data = new String[numRows][numColumns];
        parse();
//        printData();

        UpdateData updateData = new UpdateData(data, numRows, numColumns);
        data = updateData.update();
//        printData();

        DecisionTree decisionTree = new DecisionTree();
//        for (int i = 1; i < numRows; i++) {
        for (int i = 1; i < numRows; i++) {
            String[] row = new String[numColumns];
            for (int j = 0; j < numColumns; j++) {
                row[j] = data[i][j];
            }
            decisionTree.addRow(row);
        }
//        decisionTree.printTree();

        clearOutput();

        TestDecisionTree[] testDecisionTrees = new TestDecisionTree[numColumns - 3];
        for (int t = 1; t < testDecisionTrees.length; t++) {
            testDecisionTrees[t] = new TestDecisionTree();
            for (int i = 1; i < numRows; i++) {
                String[] testRow = new String[5];
                testRow[0] = data[i][0];
                testRow[1] = data[i][t];
                testRow[2] = data[i][numColumns - 3];
                testRow[3] = data[i][numColumns - 2];
                testRow[4] = data[i][numColumns - 1];
                testDecisionTrees[t].test_addRow(testRow);
            }
        }

        for (int p = 1; p < numColumns - 3; p++) {
            testDecisionTrees[p].test_printPartialTree(2, p);
        }

//        for (int current_row_index = 100; current_row_index < numRows - 1; current_row_index++) {
//            classifyRow(current_row_index, decisionTree);
//        }

        System.out.println("Correct responses: " + correctResponses);
        System.out.println("Incorrect responses: " + incorrectResponses);
        System.out.printf("Classifier accuracy: %.2f %%",
                (double) correctResponses / (double) (correctResponses + incorrectResponses) * 100);
        System.out.println();
    }

    public static void classifyRow(int current_row_index, DecisionTree decisionTree) {
        String[] current_row = new String[numColumns - 3];
        for (int a = 0; a < numColumns - 3; a++) {
            current_row[a] = data[current_row_index][a];
        }
        Classifier classifier = new Classifier(current_row, numColumns, decisionTree.tree);

        System.out.println("Classified0 Result for " + data[current_row_index][0] + ": " + classifier.getClassified0_result());
        System.out.println("Actual Value0 for " + data[current_row_index][0] + ": " + data[current_row_index][266]);

        String theoretical = classifier.getClassified0_result();
        String actual = data[current_row_index][266];
        boolean correct = theoretical.equals(actual);

        if (correct) {
            correctResponses++;
        }
        else {
            incorrectResponses++;
        }

        System.out.println("Classifying for " + data[current_row_index][0] + ": " + correct);
        System.out.println();
    }

    public static void parse() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("trainingData.txt"));
        reader.readLine(); // skips first line
        for (int i = 0; i < numRows; i++) {
            String[] lineArray = reader.readLine().split("\\s+");
            for (int j = 0; j < lineArray.length; j++) {
                data[i][j] = lineArray[j];
            }
        }
    }

    public static int countRows() throws IOException {
        BufferedReader prereader = new BufferedReader(new FileReader("trainingData.txt"));
        String line;
        int numberOfRows = 0;
        while ((line = prereader.readLine()) != null) {
            numberOfRows++;
        }
        System.out.println("numberOfRows: " + numberOfRows);
        numberOfRows = numberOfRows - 1;
        return numberOfRows;
    }

    public static int countColumns() throws IOException {
        BufferedReader prereader = new BufferedReader(new FileReader("trainingData.txt"));
        String line;
        int numberOfColumns = 0;
        line = prereader.readLine();
        line = prereader.readLine();
        String[] lineArray = line.split("\\s+");
        numberOfColumns = lineArray.length;
        System.out.println("numberOfColumns: " + numberOfColumns);
        return numberOfColumns;
    }

    public static void printData() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void clearOutput() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("gainScores_level0.txt"));

            out.write("");

            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}