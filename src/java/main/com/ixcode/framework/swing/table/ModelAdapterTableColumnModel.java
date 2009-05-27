/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.swing.table;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 6:17:40 PM by jim
 */
public class ModelAdapterTableColumnModel {

    public ModelAdapterTableColumnModel(String propertyName, String label, Class columnClass, int columnWidth) {
        _propertyName = propertyName;
        _label = label;
        _columnClass = columnClass;
        _columnWidth = columnWidth;
    }

    public String getPropertyName() {
        return _propertyName;
    }

    public String getLabel() {
        return _label;
    }

    public Class getColumnClass() {
        return _columnClass;
    }

    public int getColumnWidth() {
        return _columnWidth;
    }

    private String _propertyName;
    private String _label;
    private Class _columnClass;
    private int _columnWidth;
}
