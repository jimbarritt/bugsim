/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class TestCSVHandler implements ICSVHandler {

    public void handleHeadings(String[] headings) {
        _headings = headings;
    }

    public void handleRow(int rowId, String[] data) {
        _rows.add(new Row(rowId, data));
    }

    public static class Row {

        public Row(int id, String[] data) {
            _id = id;
            _data = data;
        }

        public int getId() {
            return _id;
        }

        public String[] getData() {
            return _data;
        }

        private int _id;
        private String[] _data;
    }

    public String[] getHeadings() {
        return _headings;
    }

    public List getRows() {
        return _rows;
    }

    private String[] _headings;
    private List _rows = new ArrayList();
}
