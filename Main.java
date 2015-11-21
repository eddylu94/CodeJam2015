package com.eddylu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static String[][] data;
    private static int numRows;
    private static int numColumns;

    public static void main(String[] args) throws IOException {
        numRows = countRows();
        numColumns = countColumns();
        System.out.println();

        data = new String[numRows][numColumns];
        parse();
        printData();

        UpdateData updateData = new UpdateData(data, numRows, numColumns);
        data = updateData.update();
        printData();

        DecisionTree decisionTree = new DecisionTree();
//        for (int i = 1; i < numRows; i++) {
        for (int i = 1; i < 80; i++) {
            String[] row = new String[numColumns];
            for (int j = 0; j < numColumns; j++) {
                row[j] = data[i][j];
            }
            decisionTree.addRow(row);
            System.out.println("Trained: " + row[0]);
        }
        decisionTree.printTree();

        for (int current_row_index = 80; current_row_index < 150; current_row_index++) {
            classifyRow(current_row_index, decisionTree);
        }

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
}