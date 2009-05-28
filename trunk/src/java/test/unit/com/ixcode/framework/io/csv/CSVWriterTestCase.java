/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

import junit.framework.TestCase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * TestCase for class : CSVWriter
 */
public class CSVWriterTestCase extends TestCase {

    public void testWriteSomeRows() throws Exception {
        List rows = createTestRows();

        StringWriter out = new StringWriter();
        CSVWriter writer = new CSVWriter(new PrintWriter(out));

        writer.writeRows(rows);

        System.out.println("Result: \n" + out.toString());
    }

    private List createTestRows() {
        List rows = new ArrayList();

        CSVRow row1 = new CSVRow(3);
        row1.setString(0, "1:Column1");
        row1.setString(1, "1:Column2");
        row1.setString(2, "1:Column3");
        rows.add(row1);

        CSVRow row2 = new CSVRow(0);
        rows.add(row2);

        CSVRow row3 = new CSVRow(4);
        row3.setString(0, "3:Column1");
        row3.setString(1, "3:Column2");
        row3.setString(2, "3:Column3");
        row3.setString(3, "3:Column4");
        rows.add(row3);


        return rows;

    }

}
