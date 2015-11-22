package com.eddylu;

import java.io.*;

public class Main {

    private static String[][] data;
    private static int numRows;
    private static int numColumns;

    private static int correctResponses = 0;
    private static int incorrectResponses = 0;

    private static String[][] data_testSet;
    private static int numRows_testSet;
    private static int numColumns_testSet;

    private static String inputPath;

    public Main(String path) throws IOException {

//        inputPath = "STDIN.txt";
        inputPath = path;

        numRows = countRows();
        numColumns = countColumns();
        System.out.println();

         data = new String[numRows][numColumns];
        parse();
//        printData();

        UpdateData updateData = new UpdateData(data, numRows, numColumns);
        data = updateData.update();
//        printData();

        numRows_testSet = countRows_testSet();
        numColumns_testSet = countColumns_testSet();
        System.out.println();

        data_testSet = new String[numRows][numColumns];
        parse_testSet();
//        printData_testSet();

        UpdateData updateData_testSet = new UpdateData(data_testSet, numRows_testSet, numColumns_testSet);
        data_testSet = updateData_testSet.update();
//        printData_testSet();

        DecisionTree decisionTree = new DecisionTree();
        for (int i = 1; i < numRows; i++) {
//        for (int i = 1; i < 135; i++) { // for when part of training set is used as test set
            String[] row = new String[numColumns];
            for (int j = 0; j < numColumns; j++) {
                row[j] = data[i][j];
            }
            decisionTree.addRow(row);
        }
//        decisionTree.printTree();
        decisionTree.printPartialTree(5);

//        /*
//        For testing gain scores for top levels
//         */
//
//        clearGainScoresLog();
//
//        TestDecisionTree[] testDecisionTrees = new TestDecisionTree[numColumns - 3];
//        for (int t = 1; t < testDecisionTrees.length; t++) {
//            if (t != 183 && t != 30 && t != 34 && t != 217 && t != 102) {
//                testDecisionTrees[t] = new TestDecisionTree();
//                for (int i = 1; i < numRows; i++) {
//                    String[] testRow = new String[10];
//                    testRow[0] = data[i][0];
//                    testRow[1] = data[i][183];
//                    testRow[2] = data[i][30];
//                    testRow[3] = data[i][34];
//                    testRow[4] = data[i][217];
//                    testRow[5] = data[i][102];
//                    testRow[6] = data[i][t];
//                    testRow[7] = data[i][numColumns - 3];
//                    testRow[8] = data[i][numColumns - 2];
//                    testRow[9] = data[i][numColumns - 1];
//                    testDecisionTrees[t].test_addRow(testRow);
//                }
//            }
//        }
//
//        for (int p = 1; p < numColumns - 3; p++) {
//            if (p != 183 && p != 30 && p != 34 && p != 217 && p != 102) {
//                testDecisionTrees[p].test_printPartialTree(6, p);
//            }
//        }

        /*
        For testing classifier by using part of training set as test set
         */

//        for (int current_row_index = 150; current_row_index < numRows; current_row_index++) {
//            classifyRow(current_row_index, decisionTree);
//        }

        System.out.println();

        System.out.println("Output to STDOUT.txt:");
        System.out.println();

        clearOutput();

        for (int currentTestSet_row_index = 0; currentTestSet_row_index < numRows_testSet; currentTestSet_row_index++) {
            classifyRow_testSet(currentTestSet_row_index, decisionTree);
        }
        System.out.println();

//        System.out.println("Correct responses: " + correctResponses);
//        System.out.println("Incorrect responses: " + incorrectResponses);
//        System.out.printf("Classifier accuracy: %.2f %%",
//                (double) correctResponses / (double) (correctResponses + incorrectResponses) * 100);
//        System.out.println();
    }

    public static void classifyRow(int current_row_index, DecisionTree decisionTree) {
        String[] current_row = new String[numColumns - 3];
        for (int a = 0; a < numColumns - 3; a++) {
            current_row[a] = data[current_row_index][a];
        }
        Classifier classifier = new Classifier(current_row, numColumns, decisionTree.tree);

        System.out.println("Classified0 Result for " + data[current_row_index][0] + ": " + classifier.getClassified0_result());
        System.out.println("Actual Value0 for " + data[current_row_index][0] + ": " + data[current_row_index][266]);
        System.out.println();

        String theoretical = classifier.getClassified0_result();
        String actual = data[current_row_index][266];
        boolean correct = theoretical.equals(actual);

        if (correct) {
            correctResponses++;
        }
        else {
            incorrectResponses++;
        }

//        System.out.println("Classifying for " + data[current_row_index][0] + ": " + correct);
//        System.out.println();
    }

    public static void classifyRow_testSet(int current_row_index, DecisionTree decisionTree) {
        String[] current_row = new String[numColumns_testSet];
        for (int a = 0; a < numColumns_testSet; a++) {
            current_row[a] = data_testSet[current_row_index][a];
        }
        Classifier classifier = new Classifier(current_row, numColumns, decisionTree.tree);

        System.out.println("Classified0 Result for " + data_testSet[current_row_index][0] + ": " + classifier.getClassified0_result());

        String result2;
        String result3;

        if (classifier.getClassified0_result().equals("RESISTANT")) {
            result2 = "N/A";
            result3 = "" + 96.30804;
        }
        else {
            result2 = "" + 158.1478261;
            result3 = "" + 219.3849565;
        }

        printOutput(data_testSet[current_row_index][0],
                classifier.getClassified0_result(), result2, result3);
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

    public static void clearGainScoresLog() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("gainScores_level5.txt"));
            out.write("");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parse_testSet() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        for (int i = 0; i < numRows_testSet; i++) {
            line = reader.readLine();
            String[] lineArray = line.split("\\s+");
            for (int j = 0; j < lineArray.length; j++) {
                data_testSet[i][j] = lineArray[j];
            }
        }
    }

    public static int countRows_testSet() throws IOException {
        BufferedReader prereader = new BufferedReader(new FileReader(inputPath));
        String line;
        int numberOfRows_testSet = 0;
        while ((line = prereader.readLine()) != null) {
            numberOfRows_testSet++;
        }
        System.out.println("numberOfRows_testSet: " + numberOfRows_testSet);
        numberOfRows_testSet = numberOfRows_testSet;
        return numberOfRows_testSet;
    }

    public static int countColumns_testSet() throws IOException {
        BufferedReader prereader = new BufferedReader(new FileReader(inputPath));
        String line;
        int numberOfColumns_testSet = 0;
        line = prereader.readLine();
        line = prereader.readLine();
        String[] lineArray = line.split("\\s+");
        numberOfColumns_testSet = lineArray.length;
        System.out.println("numberOfColumns_testSet: " + numberOfColumns_testSet);
        return numberOfColumns_testSet;
    }

    public static void printData_testSet() {
        for (int i = 0; i < numRows_testSet; i++) {
            for (int j = 0; j < numColumns_testSet; j++) {
                System.out.print(data_testSet[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void clearOutput() {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("STDOUT.txt"));
            out.append("");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printOutput(String index, String result0, String result1, String result2) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("STDOUT.txt", true));

            String output = index + " " + result0 + " " + result1 + " " + result2;
            out.append(output);
            out.newLine();

            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}