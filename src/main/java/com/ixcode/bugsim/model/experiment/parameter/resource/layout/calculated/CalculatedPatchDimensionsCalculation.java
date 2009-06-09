/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated;

import com.ixcode.framework.parameter.model.IDerivedParameterCalculation;
import com.ixcode.framework.parameter.model.ISourceParameterForwardingMap;
import com.ixcode.framework.parameter.model.ISourceParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.math.geometry.CoordinateDistributor;

import java.util.List;
import java.util.ArrayList;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 4, 2007 @ 7:59:58 PM by jim
 */
public class CalculatedPatchDimensionsCalculation implements IDerivedParameterCalculation {

    public static List createParams(Parameter radiusP, Parameter interEdgeSeparationP) {
        List params = new ArrayList();

        params.add(interEdgeSeparationP);
        params.add(radiusP);

        return params;
    }

    public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
        forwardingMap.addForward(CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION);
        forwardingMap.addForward(CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS);
    }

    public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
        Parameter interEdge = sourceParams.getParameter(CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION);
        Parameter radius = sourceParams.getParameter(CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS);
        double interPoint = interEdge.getDoubleValue() + (radius.getDoubleValue() * 2);
        return _d.calculateBoundsForSeparation(4, 4, interPoint, true).getDimensions();
    }


    CoordinateDistributor _d = new CoordinateDistributor();

}
