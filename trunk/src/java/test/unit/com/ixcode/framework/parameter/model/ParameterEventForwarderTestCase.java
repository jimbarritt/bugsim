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

        TestParameterListener l = new TestParameterListener();

        derivedP.addParameterListener(l);

        strategyContainerP.setValue(strategyBS);

        assertTrue(l.isParamValueChanged());

    }


    public void testMultipleDerivedParameters() {
        // this actually goes in the top level one but is requred to derive the locatoin from the bottom level one to test circularity...
        Parameter uncalculatedLocationP = new Parameter(P_UNCALCULATED_LOCATION, new RectangularCoordinate(99, 99));

        // First the source
//        Parameter locationP = new Parameter(P_LOCATION, new RectangularCoordinate(10, 10));
        List sp1 = DirectDerivationCalculation.createSourceParameters(uncalculatedLocationP);
        DerivedParameter locationP = new DerivedParameter(P_LOCATION, sp1, DirectDerivationCalculation.INSTANCE);

        Parameter dimensionP = new Parameter(P_DIMENSION, new CartesianDimensions(100, 100));
        List sourceParams = TestDerivedBoundsCalculation.createSourceParameters(locationP, dimensionP);
        Parameter sourceBoundsDP = new DerivedParameter(P_DERIVED_BOUNDS, sourceParams, new TestDerivedBoundsCalculation());

        StrategyDefinitionParameter parentSP = new StrategyDefinitionParameter(S_PARENT, ParameterEventForwarderTestCase.class.getName());
        parentSP.addParameter(locationP);
        parentSP.addParameter(dimensionP);
        parentSP.addParameter(sourceBoundsDP);


        Parameter paramP = new Parameter(P_SOURCE_STRATEGY_CONTAINER, parentSP);

        //Then a derived Parameter of the strategy
        List derivedPSourceParams = DerivedStrategyCalculation.createSourceParameters(paramP);
        DerivedParameter derivedP = new DerivedParameter(P_DERIVED_PARAMETER, derivedPSourceParams, new DerivedStrategyCalculation());

        // Then a caluclated Derviation of the derived parameterrt
        StrategyDefinitionParameter calculatedS = new StrategyDefinitionParameter(S_CALCULATED, ParameterEventForwarderTestCase.class.getName());

        StrategyDefinitionParameter derivedS = derivedP.getStrategyDefinitionValue();
        Parameter srcDimensionP = derivedS.findParameter(P_DIMENSION);
        List sps = DirectDerivationCalculation.createSourceParameters(srcDimensionP);
        DerivedParameter calculatedDimensionDP = new DerivedParameter(P_CALCULATED_DIMENSION, sps, DirectDerivationCalculation.INSTANCE);


        calculatedS.addParameter(calculatedDimensionDP);
        calculatedS.addParameter(uncalculatedLocationP);

        Parameter calculatedFromDerivedP = new Parameter(P_CALCULATED_FROM_DERIVED, calculatedS);

        // Add the listeners....
        TestParameterListener sourceBoundsListener = new TestParameterListener();
        sourceBoundsDP.addParameterListener(sourceBoundsListener);

        TestParameterListener parentSPListener = new TestParameterListener();
        parentSP.addParameterListener(parentSPListener);
        parentSP.addParameterContainerListener(parentSPListener);

        TestParameterListener sourceContainerListener = new TestParameterListener();
        paramP.addParameterListener(sourceContainerListener);

        TestParameterListener derivedPListener = new TestParameterListener();
        derivedP.addParameterListener(derivedPListener);

        TestParameterListener calculatedPListener = new TestParameterListener();
        calculatedFromDerivedP.addParameterListener(calculatedPListener);

        TestParameterListener locListener = new TestParameterListener();
        locationP.addParameterListener(locListener);

        // Change something...
        dimensionP.setValue(new CartesianDimensions(200, 200));

        // And make sure everyone got notified!
        assertTrue(sourceBoundsListener.isParamValueChanged());
        assertEquals(1, sourceBoundsListener.getEvents().size());
        assertEquals(P_DERIVED_BOUNDS, sourceBoundsListener.getEvent(0).getSource().getName());

//        assertTrue(parentSPListener.isParamValueChanged());
//        assertEquals(2, parentSPListener.getEvents().size());

//        assertEquals(S_PARENT + "." + P_DERIVED_BOUNDS + "." + P_DIMENSION, parentSPListener.getEvent(0).getEventPath());
//        assertEquals(S_PARENT + "." + P_DIMENSION, parentSPListener.getEvent(1).getEventPath());

//        assertTrue(sourceContainerListener.isParamValueChanged());
//        assertEquals(2, sourceContainerListener.getEvents().size());
//        assertEquals(P_SOURCE_STRATEGY_CONTAINER + "." + S_PARENT + "." + P_DERIVED_BOUNDS + "." + P_DIMENSION, sourceContainerListener.getEvent(0).getEventPath());
//        assertEquals(P_SOURCE_STRATEGY_CONTAINER + "." + S_PARENT + "." + P_DIMENSION, sourceContainerListener.getEvent(1).getEventPath());

        assertTrue(derivedPListener.isParamValueChanged());
        assertEquals(2, derivedPListener.getEvents().size());
        assertEquals(P_DERIVED_PARAMETER + "." + P_SOURCE_STRATEGY_CONTAINER + "." + S_PARENT + "." + P_DERIVED_BOUNDS + "." + P_DIMENSION, derivedPListener.getEvent(0).getEventPath());
        assertEquals(P_DERIVED_PARAMETER + "." + P_SOURCE_STRATEGY_CONTAINER + "." + S_PARENT + "." + P_DIMENSION, derivedPListener.getEvent(1).getEventPath());

        assertTrue(calculatedPListener.isParamValueChanged());
        assertEquals(1, calculatedPListener.getEvents().size());
        assertEquals(P_CALCULATED_FROM_DERIVED, calculatedPListener.getEvent(0).getSource().getName());

        assertFalse(locListener.isParamValueChanged());

        sourceBoundsListener.reset();
        parentSPListener.reset();
        sourceContainerListener.reset();
        derivedPListener.reset();
        calculatedPListener.reset();
        locListener.reset();

        uncalculatedLocationP.setValue(new RectangularCoordinate(666, 666));


        assertTrue(sourceBoundsListener.isParamValueChanged());
        assertTrue(parentSPListener.isParamValueChanged());
        assertTrue(sourceContainerListener.isParamValueChanged());
        assertTrue(derivedPListener.isParamValueChanged());
        assertTrue(calculatedPListener.isParamValueChanged());
        assertTrue(locListener.isParamValueChanged());

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
