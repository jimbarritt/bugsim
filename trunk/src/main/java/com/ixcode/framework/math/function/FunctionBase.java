/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.function;

import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.math.function.IFunction;

import java.io.Serializable;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class FunctionBase extends ModelBase implements IFunction, Serializable {

    protected FunctionBase(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    private String _name;
}
