/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

public interface ICSVHandler {


    void handleHeadings(String[] headings);

    void handleRow(int rowId, String[] data);
}
