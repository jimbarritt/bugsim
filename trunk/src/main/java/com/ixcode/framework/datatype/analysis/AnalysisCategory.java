/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.datatype.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Description : Provides a user definable way of grouping either agents or grids.
 */
public class AnalysisCategory {

    public AnalysisCategory(String name, String code) {
        _name = name;
        _code = code;
    }


    public String getName() {
        return _name;
    }

    public String getCode() {
        return _code;
    }

    public AnalysisValue addValue(String code, String description) {
        AnalysisValue value = new AnalysisValue(this, code, description);
        _values.add(value);
        _valueMap.put(value.getCode(), value);
        return value;
    }

    public AnalysisValue getValue(String code) {
        return (AnalysisValue)_valueMap.get(code);
    }

    public int hashCode() {
       return _code.hashCode();
    }
    public boolean equals(Object other) {
        if (other instanceof AnalysisCategory) {
            return ((AnalysisCategory)other).getCode().equals(_code);
        }
        return false;
    }

    public List getValues() {
        return _values;
    }

    private String _name;
    private String _code;
    private List _values = new ArrayList();
    private Map _valueMap = new HashMap();
}
