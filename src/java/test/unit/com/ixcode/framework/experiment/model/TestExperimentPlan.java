/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.parameter.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class TestExperimentPlan extends ExperimentPlan {
    public static final String MANIPULATED_PARAM_1 = "basic.manipulatedParam1";
    public static final String MANIPULATED_PARAM_2 = "basic.manipulatedParam2";
    public static final String MANIPULATED_PARAM_3 = "basic.manipulatedParam3";
    public static final String MANIPULATED_PARAM_4 = "basic.manipulatedParam4";
    public static final String DERIVED_PARAM = "basic.param4X1000";
    public static final String ALGORITHM_PARAM = "basic.containsAlgorithm";
    public static final String ALGORITHM1 = "basic.containsAlgorithm.algorithm1";
    public static final String ALGORITHM2 = "basic.containsAlgorithm.algorithm2";
    public static final String ALGORITHM1_PARAM_1 = ALGORITHM1 + ".algParam1";
    public static final String ALGORITHM2_PARAM_1 = ALGORITHM2 + ".algParam1";


    public TestExperimentPlan(long numberOfTimesteps) {
        super("TestExp");
        ParameterMap params = super.getParameterTemplate();
        Category basic = new Category("basic");
        params.addCategory(basic);

        _numberTimesteps = new Parameter("numberOfTimesteps", new Long(numberOfTimesteps));
        basic.addParameter(_numberTimesteps);



        _manipulatedParam1 = new Parameter("manipulatedParam1", "DEFAULT1");
        _manipulatedParam2 = new Parameter("manipulatedParam2", "DEFAULT2");
        _manipulatedParam3 = new Parameter("manipulatedParam3", "DEFAULT3");
        _manipulatedParam4 = new Parameter("manipulatedParam4", -20L);
        _algorithm1 = new StrategyDefinitionParameter("algorithm1", "com.blah.blah.SomeClass");
        _algParam1 = new Parameter("algParam1", "valueX");
        _algorithm1.addParameter(_algParam1);
        _algorithm2 = new StrategyDefinitionParameter("algorithm2", "com.blah.blah.SomeOtherClass");
        _algParam2 = new Parameter("algParam1", "valueY");
        _algorithm2.addParameter(_algParam2);

        _manipulatedParam5 = new Parameter("containsAlgorithm", _algorithm1);



        basic.addParameter(_manipulatedParam1);
        basic.addParameter(_manipulatedParam2);
        basic.addParameter(_manipulatedParam3);
        basic.addParameter(_manipulatedParam4);
        basic.addParameter(_manipulatedParam5);

        addDerivedParameter(basic, _manipulatedParam4);

        List algParamManipulations = new ArrayList();
        algParamManipulations.add(createAlgorithm1Manipulation("XXX1", true));
        algParamManipulations.add(createAlgorithm1Manipulation("XXX2", false));
        algParamManipulations.add(createAlgorithm2Manipulation("YYY1", true));
        algParamManipulations.add(createAlgorithm2Manipulation("YYY2", false));
        super.addParameterManipulationSequence(algParamManipulations);

        List param1Manipulations = new ArrayList();


        param1Manipulations.add(createMultipleManipulation("A1", "Z3", 10L));
        param1Manipulations.add(createMultipleManipulation("B1", "Q3", 20L));
        param1Manipulations.add(createMultipleManipulation("C1", "X3", 30L));
        super.addParameterManipulationSequence(param1Manipulations);

        List param2Manipulations = new ArrayList();
        param2Manipulations.add(new ParameterManipulation(_manipulatedParam2, "A2"));
        param2Manipulations.add(new ParameterManipulation(_manipulatedParam2, "B2"));
        param2Manipulations.add(new ParameterManipulation(_manipulatedParam2, "C2"));
        super.addParameterManipulationSequence(param2Manipulations);


    }

    private IParameterManipulation createAlgorithm1Manipulation(String value, boolean changedStructure) {
        MultipleParameterManipulation manip = new MultipleParameterManipulation();
        manip.addParameterManipulation(new ParameterManipulation(_manipulatedParam5, _algorithm1));
        manip.addParameterManipulation(new ParameterManipulation(_algParam1, value));

        return manip;
    }

    private IParameterManipulation createAlgorithm2Manipulation(String value, boolean changedStructure) {
        MultipleParameterManipulation manip = new MultipleParameterManipulation();
        manip.addParameterManipulation(new ParameterManipulation(_manipulatedParam5, _algorithm2));
        manip.addParameterManipulation(new ParameterManipulation(_algParam2, value));

        return manip;
    }

    private void addDerivedParameter(Category basic, final Parameter sourceParam) {
        final String sourceParamName = sourceParam.getName();
        IDerivedParameterCalculation derivedConfig = new IDerivedParameterCalculation() {

            public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
                forwardingMap.addForward(sourceParamName);
            }

            public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
                Parameter param = sourceParams.getParameter(sourceParamName);
                return new Long(param.getLongValue() * 1000);
            }
        };
        DerivedParameter derived = new DerivedParameter("param4X1000", derivedConfig);
                derived.addSourceParameter(sourceParam);
        basic.addParameter(derived);
    }

    private IParameterManipulation createMultipleManipulation(String param1, final String param3, final long param4) {
        MultipleParameterManipulation multiM = new MultipleParameterManipulation();
        multiM.addParameterManipulation(new ParameterManipulation(_manipulatedParam1, param1));

        IDerivedManipulationConfiguration config = new IDerivedManipulationConfiguration(){
            public void configureDerivedParameter(Parameter currentDerivedParameter, List currentSourceParameters) {
                Parameter source = (Parameter)currentSourceParameters.get(0);
                String derivedValue = source.getStringValue() + " DERIVED! - " + param3;
                currentDerivedParameter.setValue(derivedValue);
            }
        };

        DerivedParameterManipulation derived = new DerivedParameterManipulation(_manipulatedParam3, config);
        derived.addSourceParameter(_manipulatedParam1);


        multiM.addParameterManipulation(derived);
        multiM.addParameterManipulation(new ParameterManipulation(_manipulatedParam4, param4));

        return multiM;
    }

    public long getNumberOfTimesteps() {
        return ((Long)_numberTimesteps.getValue()).longValue();
    }


    private Parameter _numberTimesteps;
    private Parameter _manipulatedParam1;
    private Parameter _manipulatedParam2;

    private Parameter _manipulatedParam3;

    private Parameter _manipulatedParam4;
    private StrategyDefinitionParameter _algorithm1;
    private StrategyDefinitionParameter _algorithm2;
    private Parameter _manipulatedParam5;
    private Parameter _algParam1;
    private Parameter _algParam2;
}
