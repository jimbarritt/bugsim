/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : Helps you to write CSV files.
 */
public class CSVWriter {


    public CSVWriter(File outputFile) throws IOException {
      this(outputFile, false);
    }

    public CSVWriter(PrintWriter out) {
        _out = out;
    }

    public CSVWriter(File output, boolean append) throws IOException{
        if (output.exists() && !append) {
            throw new IOException("File Already Exists: " + output.getAbsolutePath());
        }

        if (!output.exists()) {
            output.createNewFile();
        }

        
        _out = new PrintWriter(new BufferedWriter(new FileWriter(output, append)));
    }

    public void writeRow(CSVRow row) {
        for (int i=0;i<row.getColumnCount();++i)  {
            if (row.getValue(i) != null) {
                _out.print(row.getValue(i));
            }
            if (!row.isLastColumn(i)) {
               _out.print(",");
            }
        }
        _out.println();
    }

    public void close() throws IOException {
        _out.flush();
        _out.close();
    }

    public void writeRows(List rows) {
        for (Iterator itr = rows.iterator(); itr.hasNext();) {
            CSVRow row = (CSVRow)itr.next();
            writeRow(row);
        }
    }


    public void writeProperty(String name, long value) {
        writeProperty(name, new Long(value));

    }
    public void writeProperty(String name, Object value) {
        CSVRow row = new CSVRow(2);
        row.setObject(0, name);
        row.setObject(1, value);
        writeRow(row);
    }

    public void println() {
        _out.println();
    }

    public void println(String out) {
        _out.println(out);
    }
    private PrintWriter _out;
}
