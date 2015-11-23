package com.eddylu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class UpdateData {

    private static String[][] data;
    private static int numRows;
    private static int numColumns;

    public UpdateData(String[][] input, int rows, int columns) {
        data = input;
        numRows = rows;
        numColumns = columns;
    }

    /*
    Updates data matrix with binary values
    @return     data matrix with updated binary values
     */
    public static String[][] update() {

        int rowStart_index;
        if (data[0][0].charAt(0) == '#') {
            rowStart_index = 1;
        }
        else {
            rowStart_index = 0;
        }

        updateMaleFemale(1, rowStart_index);
        updateDecimalColumn(2, rowStart_index);
        updateDecimalColumn(3, rowStart_index);
        updateYesNo(4, rowStart_index);
        updateYesNo(5, rowStart_index);
        updateYesNo(6, rowStart_index);
        updateYesNo(7, rowStart_index);
        updateNegPos(8, rowStart_index);
        updateNegPos(9, rowStart_index);
        updateNegPos(10, rowStart_index);
        updateChemoSimplest(11, rowStart_index);

        for (int i = 12; i < numColumns - 3; i++) {
            updateDecimalColumn(i, rowStart_index);
        }

        return data;
    }

    /*
    Updates data matrix for male or female column
    @param  column          column index
    @param  rowStart_index  row index to start at
     */
    public static void updateMaleFemale(int column, int rowStart_index) {
        for (int i = rowStart_index; i < numRows; i++) {
            if (data[i][column].equals("F")) {
                data[i][column] = "0";
            }
            else {
                data[i][column] = "1";
            }
        }
    }

    /*
    Updates data matrix for yes or no column
    @param  column          column index
    @param  rowStart_index  row index to start at
     */
    public static void updateYesNo(int column, int rowStart_index) {
        for (int i = rowStart_index; i < numRows; i++) {
            if (data[i][column].toUpperCase().equals("NO")) {
                data[i][column] = "0";
            }
            else {
                data[i][column] = "1";
            }
        }
    }

    /*
    Updates data matrix for negative or positive column
    @param  column          column index
    @param  rowStart_index  row index to start at
     */
    public static void updateNegPos(int column, int rowStart_index) {
        for (int i = rowStart_index; i < numRows; i++) {
            if (data[i][column].equals("NEG")) {
                data[i][column] = "0";
            }
            else {
                data[i][column] = "1";
            }
        }
    }

    /*
    Updates data matrix for chemo simplest column
    @param  column          column index
    @param  rowStart_index  row index to start at
     */
    public static void updateChemoSimplest(int column, int rowStart_index) {

        for (int i = rowStart_index; i < numRows; i++) {
            if (data[i][column].equals("Anthra-HDAC")) {
                data[i][column] = "0";
            }
            else {
                data[i][column] = "1";
            }
        }
    }

    /*
    Updates data matrix for decimal column
    @param  column          column index
    @param  rowStart_index  row index to start at
     */
    public static void updateDecimalColumn(int column, int rowStart_index) {

        ArrayList<Double> numbers = new ArrayList<Double>();

        for (int i = rowStart_index; i < numRows; i++) {
            if (isNumber(data[i][column])) {
                numbers.add(Double.parseDouble(data[i][column]));
            }
        }

        double median = findMedian(numbers);

        for (int i = rowStart_index; i < numRows; i++) {
            if (isNumber(data[i][column])) {
                if (Double.parseDouble(data[i][column]) <= median) {
                    data[i][column] = "0";
                }
                else {
                    data[i][column] = "1";
                }
            }
            else {
                data[i][column] = "1";
            }
        }

    }

    /*
    Finds median of values in column of data
    @param  input   list of values from column of data
    @return         median
     */
    public static double findMedian(ArrayList<Double> input) {
        Collections.sort(input);
        double median;
        if (input.size() % 2 == 0) { // even number of values in input
            median = 0.5 * (input.get(input.size() / 2) + input.get(input.size() / 2 + 1));
        }
        else {
            median = input.get(input.size() / 2);
        }
        return median;
    }

    /*
    Checks if String is a number
    @param  str     String to be checked if it is a number
    @return         if str is a number
     */
    public static boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /*
    Counts different values in the chemo simplest column of data array
    @param  rowStart_index  row index to start at
     */
    public static void ChemoSimplest_statistics(int rowStart_index) {
        HashMap<String, Integer> values = new HashMap<String, Integer>();
        ArrayList<String> visited = new ArrayList<String>();
        for (int i = rowStart_index; i < numRows; i++) {
            if (values.containsKey(data[i][11])) {
                values.put(data[i][11], values.get(data[i][11]) + 1);
            }
            else {
                values.put(data[i][11], 0);
                visited.add(data[i][11]);
            }
        }
        for (int i = 0; i < visited.size(); i++) {
            System.out.println(visited.get(i) + ": " + values.get(visited.get(i)));
        }

        /*
        Results when run:
        Anthra-HDAC: 93
        HDAC-Plus: 18
        Flu-HDAC: 26
        StdAraC-Plus: 23
        Anthra-Plus: 1
         */
    }

}