package com.company;

import java.util.ArrayList;

public class UpdateData {

    private static String[][] data;
    private static int numRows;
    private static int numColumns;

    public UpdateData(String[][] input, int rows, int columns) {
        data = input;
        numRows = rows;
        numColumns = columns;
    }

    public static String[][] update() {

        updateColumn1();

        return data;
    }

    public static void updateColumn1() {
        for (int i = 1; i < numRows; i++) {
            if (data[i][1].equals("F")) {
                data[i][1] = "0";
            }
            else {
                data[i][1] = "1";
            }
        }
    }

    public static void updateDecimalColumn(int column) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i < numRows; i++) {

        }
    }

    public static void findMedian(ArrayList<Integer> input) {

    }

    public static boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}