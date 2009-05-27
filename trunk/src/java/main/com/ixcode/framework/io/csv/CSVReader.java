/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CSVReader {

    public CSVReader(int dataStartRow, boolean firstRowIsHeadings) {
        _firsRowIsHeadings = firstRowIsHeadings;
        _dataStartRow = dataStartRow;
    }

    public void readCSVFile(InputStream in, ICSVHandler handler) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int lineNumber = 1;
        int rowId = 0;

        while(reader.ready()) {
            String line = reader.readLine();
//            System.out.println("Line: "+ lineNumber + " : " + line);
            if (lineNumber >= _dataStartRow) {
                rowId = processLine(lineNumber, rowId, line, handler);
            }
            lineNumber++;
        }

    }

    private int processLine(int lineNumber, int rowId, String line, ICSVHandler handler) {
        StringTokenizer st = new StringTokenizer(line, ",");
        String[] data = new String[st.countTokens()];
        int index = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            data[index] = stripQuotes(token);

            index++;
        }

        int newRowId = rowId;
        if (rowId == 0 && _firsRowIsHeadings) {
            handler.handleHeadings(data);
            newRowId = 1;
        } else {
            handler.handleRow(rowId, data);
            newRowId++;
        }
        return newRowId;
    }

    private String stripQuotes(String token) {
        int start=0;
        int end=token.length();

        if (token.startsWith("\"") && token.length() >1) {
            start=1;
        }
        if (token.endsWith("\"") && token.length() > 1) {
            end-=1;
        }
        return token.substring(start, end);
    }


    private int _dataStartRow;
    private boolean _firsRowIsHeadings;
}
