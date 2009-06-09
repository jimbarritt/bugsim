/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.datatype.analysis;

import com.ixcode.framework.datatype.analysis.AnalysisCategory;

/**
 *  Description : AN Analysis Value
 */
public class AnalysisValue {
    public AnalysisValue(AnalysisCategory category, String code, String description) {
        _code = code;
        _description = description;
        _category = category;
    }

    public String getCode() {
        return _code;
    }

    public String getDescription() {
        return _description;
    }

    public boolean equals(Object other) {
        boolean equal = false;
        if (other != null && AnalysisValue.class.isAssignableFrom(other.getClass())) {
            AnalysisValue value = (AnalysisValue)other;
            if (value.getCode().equals(_code)) {
                equal = true;
            }
        }
        return equal;
    }

    public AnalysisCategory getCategory() {
        return _category;
    }

    private String _code;
    private String _description;
    private AnalysisCategory _category;
}
