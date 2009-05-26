/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.parameter.boundary.extent;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DistancedExtentStrategy extends ExtentStrategyBase {


    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);
        connectToParam();
    }

    /**
     * Required for being a ParameterisedStrategy
     */
    public DistancedExtentStrategy() {

    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition created = null;
        if (parameterName.equals(P_INNER_BOUNDARY)) {
            created = BoundaryStrategyFactory.createBoundaryStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            created = super.createStrategyDefinition(parameterName, strategyS);
        }
        return created;
    }


    public DistancedExtentStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
        connectToParam();
    }


    public BoundaryStrategyBase getInnerBoundary() {
        return (BoundaryStrategyBase)super.getStrategyDefinition(P_INNER_BOUNDARY);
    }

    public void setInnerBoundary(BoundaryStrategyBase innerBoundary) {
        super.replaceStrategyDefinition(P_INNER_BOUNDARY, innerBoundary);
    }

    public Parameter getInnerBoundaryP() {
        return super.getParameter(P_INNER_BOUNDARY);
    }


    public double getDistance() {
        return super.getParameter(P_DISTANCE).getDoubleValue();
    }

    public void setDistance(double distance) {
        super.setParameterValue(P_DISTANCE, distance);
    }

    public boolean isInnerBoundaryDerived() {
        return (super.getParameter(P_INNER_BOUNDARY) instanceof DerivedParameter);
    }


    public void setInnerBoundaryP(Parameter innerBoundaryP) {
        super.setParameterValue(P_INNER_BOUNDARY, innerBoundaryP);
        connectToParam();
    }

    private void connectToParam() {
        StrategyDefinitionParameter boundaryS = (StrategyDefinitionParameter)super.getParameter(P_INNER_BOUNDARY).getValue();

        BoundaryStrategyBase innerBoundary = BoundaryStrategyFactory.createBoundaryStrategy(boundaryS, super.getParameterMap(), super.isForwardEvents());
        super.addStrategyDefinition(P_INNER_BOUNDARY, innerBoundary);

    }

    private Parameter getDistanceP() {
        return super.getParameter(P_DISTANCE);
    }

    public void replaceInnerBoundary(StrategyDefinitionParameter boundaryS) {
        super.replaceStrategyDefinitionParameter(P_INNER_BOUNDARY, boundaryS);
    }

    private static final Logger log = Logger.getLogger(DistancedExtentStrategy.class);

    public static final String S_DISTANCED_EXTENT = "distancedExtentStrategy";

    public static final String P_INNER_BOUNDARY = "innerBoundary";
    public static final String P_DISTANCE = "distance";

}
