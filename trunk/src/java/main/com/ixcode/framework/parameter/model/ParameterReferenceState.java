/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : What state is the parameterReference in?
 *  Created     : Jan 30, 2007 @ 4:04:55 PM by jim
 */
class ParameterReferenceState extends TypeSafeEnum  {

    public static final ParameterReferenceState DISCONNECTED  = new ParameterReferenceState("disconnected");
    public static final ParameterReferenceState CONNECTED = new ParameterReferenceState("connected");


    public ParameterReferenceState(String name) {
        super(name);
    }
}


