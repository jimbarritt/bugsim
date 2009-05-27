/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io.csv;

import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanFormatter;

import java.util.Locale;

/**
 *  Description : Represents a row in a CSV file - makes it easier to manipulate
 */
public class CSVRow {

    public CSVRow(int columnCount) {
        _data = new String[columnCount];
    }

    public void setString(int index, String value) {
        _data[index] = value;
    }

    public String[] getData() {
        return _data;
    }

    public int getColumnCount() {
        return _data.length;
    }

    public String getValue(int index) {
        return _data[index];
    }

    public boolean isLastColumn(int index) {
        return index == _data.length-1;
    }

    public void setObject(int index, Object value) {
        if (value == null) {
            setString(index, "null");
        } else {
            IJavaBeanValueFormat format = _formatter.getFormat(Locale.UK, value.getClass());
            setString(index, format.format(value));
        }
    }

    public void setLong(int index, long value) {
        setObject(index, new Long(value));
    }

    public void setDouble(int index, double value) {
        setObject(index, new Double(value));
    }

    public void setBoolean(int index, boolean value) {
        setObject(index, new Boolean(value));

    }

    private JavaBeanFormatter _formatter = new JavaBeanFormatter();
    private String[] _data;
}
