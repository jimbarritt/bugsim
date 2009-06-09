/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.Stack;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 2, 2007 @ 9:22:45 PM by jim
 */
public class ParameterDisconnectedEvent extends ParameterEvent {

    public ParameterDisconnectedEvent(Parameter source, String fullyQualifiedName, Stack parameterPath) {
        super(source, parameterPath);
        _fullyQualifiedName = fullyQualifiedName;
    }

    public String getFullyQualifiedName() {
        return _fullyQualifiedName;
    }

    private String _fullyQualifiedName;
}
