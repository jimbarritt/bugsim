/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : In order to derive a strategy you need to put the containing Parameter into the source parameters
 *                  This is incase someone changes the strategy - you need to get the strategy out of the source parameters.
 * There MIGHT be a case where we need a DirectDerivedStrategy Calculation but you can just use the DirectDerivationCalculation for the =- this would
 * be the case where there is only the strategy - it doesnt have a container and cannot be replaced.
 */
public class DerivedStrategyCalculation implements IDerivedParameterCalculation {


    public static List createSourceParameters(Parameter strategyContainingP) {
        List params = new ArrayList();
        params.add(strategyContainingP);

        return params;
    }

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        forwardingMap.addForwardFromFirstParameterReference();
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        return sourceParams.getFirstParameter().getStrategyDefinitionValue();
    }


    public static DerivedStrategyCalculation INSTANCE = new DerivedStrategyCalculation();

}
