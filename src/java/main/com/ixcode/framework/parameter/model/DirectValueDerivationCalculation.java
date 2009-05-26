/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.List;
import java.util.ArrayList;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DirectValueDerivationCalculation implements IDerivedParameterCalculation {


    public static List createSourceParameters(Parameter sourceParameter) {
        List params = new ArrayList();
        params.add(sourceParameter);

        return params;
    }

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        forwardingMap.addForwardFromFirstParameterReference();
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        return sourceParams.getFirstParameter().getValue();
    }


    public static DirectValueDerivationCalculation INSTANCE = new DirectValueDerivationCalculation();

}
