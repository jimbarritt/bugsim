/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import junit.framework.TestCase;

import java.util.List;

/**
 * TestCase for class : DerivedParameter
 * Created     : Jan 30, 2007 @ 11:25:17 AM by jim
 */
public class DerivedParameterTestCase extends TestCase {


    public void testEventForward_SimpleParameter() {
        Parameter sourceP = new Parameter(P_SOURCE, 666d);

        List sp = DirectDerivationCalculation.createSourceParameters(sourceP);
        DerivedParameter derivedP = new DerivedParameter(P_DERIVED, sp, DirectDerivationCalculation.INSTANCE);


        TestParameterListener l =new TestParameterListener();
        derivedP.addParameterListener(l);

        sourceP.setValue(333d);

        assertTrue(l.isParamValueChanged());
        assertEquals(1, l.getEvents().size());
        assertEquals(P_DERIVED + "." + ParameterReference.createReferenceName(P_SOURCE) + "." + P_SOURCE, l.getEvent(0).getEventPath());
        assertEquals(new Double(666).doubleValue(), ((Double)l.getEvent(0).getOldValue()).doubleValue(), 0.000001);
        assertEquals(new Double(333).doubleValue(), ((Double)l.getEvent(0).getNewValue()).doubleValue(), 0.000001);
    }

    private static final String P_SOURCE = "sourceP";
    private static final String P_DERIVED = "derivedP";
}
