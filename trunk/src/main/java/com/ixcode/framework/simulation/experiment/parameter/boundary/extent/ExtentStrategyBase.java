/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 *  Description : Base Class for boundary strategies. There are three:
 *
 *      a) Fixed - simple boundary that is set and then doesn't change
 *
 *      b) Distanced - takes a source boundary (usually a derived parameter) and makes itself a certain distance outside it.
 *
 *      c) Proportional - takes a source boundary (usually derived parameter) and makes itself a certain proportion of it - i.e. 2 times the size.
 *
 */
public abstract class ExtentStrategyBase extends StrategyDefinition {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);
        connectToParam();
    }

    /**
     * Required for being a ParameterisedStrategy
     */
    protected ExtentStrategyBase() {
    }

    protected ExtentStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
        connectToParam();
    }


    public BoundaryStrategyBase getOuterBoundary() {
        return _outerBoundary;
    }

    public void setOuterBoundaryP(Parameter outerBoundaryP) {
        super.setParameter(outerBoundaryP);
    }


    private void connectToParam() {
        StrategyDefinitionParameter boundaryS = (StrategyDefinitionParameter)super.getParameter(P_OUTER_BOUNDARY).getValue();

        _outerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, super.getParameterMap(), super.isForwardEvents());

    }

    public Parameter getOuterBoundaryP() {
        return super.getParameter(P_OUTER_BOUNDARY);
    }


    private static final Logger log = Logger.getLogger(ExtentStrategyBase.class);

    public static final String P_OUTER_BOUNDARY = "outerBoundary";
    private BoundaryStrategyBase _outerBoundary;


}
