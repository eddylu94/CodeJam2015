package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        for (int i = 1; i < numRows; i++) {
            String[] row = new String[numColumns];
            for (int j = 0; j < numColumns; j++) {
                row[j] = data[i][j];
            }
            decisionTree.addRow(row);
        }
        decisionTree.printTree();
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