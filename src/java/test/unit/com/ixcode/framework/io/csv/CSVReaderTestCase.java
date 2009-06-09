/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

import junit.framework.TestCase;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * TestCase for class : CSVReader
 */
public class CSVReaderTestCase extends TestCase {



    public void testImportCSV() throws Exception {


        CSVReader reader = new CSVReader(2, true);

        FakeCSVHandler handler= new FakeCSVHandler();

        URL url = this.getClass().getResource("/test/TestImport.csv");
        assertNotNull("Could not load test data!", url);
        reader.readCSVFile(url.openStream(), handler);

        assertEquals("Headings", Arrays.asList(EXPECTED_HEADINGS), Arrays.asList(handler.getHeadings()));


        List rows = handler.getRows();
        for (Iterator itr = rows.iterator(); itr.hasNext();) {
            FakeCSVHandler.Row row = (FakeCSVHandler.Row)itr.next();
            assertEquals("RowId", handler.getRows().indexOf(row) +1, row.getId());
            for (int i=0;i<row.getData().length;++i) {
                String value = row.getData()[i];
                String expectedValue = ROW_PREFIXES[i] + row.getId();
                assertEquals("CellValue", expectedValue, value);
            }
        }

    }

    public static String[] EXPECTED_HEADINGS = new String[] {
            "Column A", "Column B", "Column C", "Column D", "Column E"
    };

    public static String[] ROW_PREFIXES = new String[] {
        "A", "B", "C", "D", "E"
    };

}
