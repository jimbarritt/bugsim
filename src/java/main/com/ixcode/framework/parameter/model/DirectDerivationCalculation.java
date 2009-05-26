/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DirectDerivationCalculation implements IDerivedParameterCalculation {


    public static List createSourceParameters(Parameter sourceParameter) {
        List params = new ArrayList();
        params.add(sourceParameter);

        return params;
    }

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        forwardingMap.addForwardFromFirstParameterReference();
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        return sourceParams.getFirstParameter();
    }


    public static DirectDerivationCalculation INSTANCE = new DirectDerivationCalculation();

}
