/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * TestCase for class : ParameterEventForwarder
 * Created     : Jan 29, 2007 @ 11:58:39 AM by jim
 */
public class ParameterEventForwarderTestCase extends TestCase {

    public void testForwardStrategyChange() {
        StrategyDefinitionParameter strategyAS = new StrategyDefinitionParameter(S_CALCULATED, String.class.getName() );
        StrategyDefinitionParameter strategyBS = new StrategyDefinitionParameter(S_NOT_CALCULATED, String.class.getName() );



        Parameter strategyContainerP = new Parameter(P_SOURCE_STRATEGY_CONTAINER, strategyAS);


        List derivedPSourceParams = DerivedStrategyCalculation.createSourceParameters(strategyContainerP);
        DerivedParameter derivedP = new DerivedParameter(P_DERIVED_PARAMETER, derivedPSourceParams, new DerivedStrategyCalculation());

        FakeParameterListener l = createParameterListener(derivedP);

        strategyContainerP.setValue(strategyBS);

        assertTrue(l.isParamValueChanged());

    }


    public void testMultipleDerivedParameters() {
        Parameter dimensionP = new Parameter(P_DIMENSION, new CartesianDimensions(100, 100));

        Parameter locationP = new Parameter(P_LOCATION, new RectangularCoordinate(99, 99));

        Parameter boundsDP = new DerivedParameter(P_DERIVED_BOUNDS,
                TestDerivedBoundsCalculation.createSourceParameters(locationP, dimensionP),
                new TestDerivedBoundsCalculation());


        FakeParameterListener dimensionListener = createParameterListener(dimensionP);
        FakeParameterListener boundsListener = createParameterListener(boundsDP);

        dimensionP.setValue(new CartesianDimensions(200, 200));

        assertTrue("dimension", dimensionListener.isParamValueChanged());
        assertTrue("bounds", boundsListener.isParamValueChanged());
        assertEquals(1, boundsListener.getEvents().size());
        assertEquals(P_DERIVED_BOUNDS, boundsListener.getEvent(0).getSource().getName());

    }

    private static FakeParameterListener createParameterListener(Parameter parameter) {
        FakeParameterListener listener = new FakeParameterListener();
        parameter.addParameterListener(listener);
        return listener;
    }


    private static class TestDerivedBoundsCalculation implements IDerivedParameterCalculation {

        public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
            forwardingMap.addForward(P_LOCATION);
            forwardingMap.addForward(P_DIMENSION);
        }

        public static List createSourceParameters(Parameter sourceLocationP, Parameter sourceDimensionP) {
            List params = new ArrayList();

            params.add(sourceLocationP);
            params.add(sourceDimensionP);
            return params;
        }

        public Object calculateDerivedValue(ISourceParameterMap sourceParams) {

            RectangularCoordinate location = (RectangularCoordinate)sourceParams.getParameter(P_LOCATION).getValue();
            CartesianDimensions dimensions = (CartesianDimensions)sourceParams.getParameter(P_DIMENSION).getValue();


            return new CartesianBounds(location, dimensions);

        }


    }


    public static final String P_LOCATION = "Location";
    public static final String P_DIMENSION = "Dimension";
    public static final String P_DERIVED_BOUNDS = "DerivedBounds";
    private static final String S_PARENT = "ParentStrategy";
    private static final String P_SOURCE_STRATEGY_CONTAINER = "sourceStrategyContainer";
    private static final String P_DERIVED_PARAMETER = "derivedParameter";
    private static final String P_CALCULATED_FROM_DERIVED = "calculatedFromDerived";
    private static final String S_CALCULATED = "calculatedS";
    private static final String P_CALCULATED_DIMENSION = "calculatedDimension";
    private static final String P_UNCALCULATED_LOCATION = "uncalculatedLocation";
    private static final String S_NOT_CALCULATED = "notCalculatedS";
}
