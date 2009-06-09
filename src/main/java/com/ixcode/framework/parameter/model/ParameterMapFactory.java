package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParameterMapFactory {

    public ParameterMap createFromTemplate(ParameterMap param) {

        return copyParameters(param);
    }

    private ParameterMap copyParameters(ParameterMap source) {
        ParameterMap copyParameters = new ParameterMap();
        List derivedParameters = new ArrayList();

        for (Iterator itr = source.getCategories().iterator(); itr.hasNext();) {
            Category srcCategory = (Category)itr.next();
            Category copyCategory = copyCategory(srcCategory, derivedParameters);
            copyParameters.addCategory(copyCategory);
        }

        // Dont think we need to do this anymore because everything is a parameter Reference anyway
//        rewireDerivedParameters(derivedParameters, copyParameters);

        return copyParameters;
    }

//  cOMMENTED THIS ALL OUT WHEN REFACTORED TO  MAKE DERIVED PARAMETERS USE PARAMETER REFERENCES - NO LONGER NESCESSARY TO
    // MAINTAIN THE REFERENCES ALL THE TIME PLUS CAN MAKE FORWARD REFERENCES NOW IF YOU LIKE.
//    private static void rewireDerivedParameters(List derivedParameters, ParameterMap copyParameters) {
//
//        for (Iterator itr = derivedParameters.iterator(); itr.hasNext();) {
//            DerivedParameter derivedParameter = (DerivedParameter)itr.next();
//            rewireDerivedParameter(derivedParameter, copyParameters);
//
//        }
//    }
//
    /**
     * We have in here a list of parameters that may or may not come from somwehwere else.
     * When we copy everything across we need to replace those that are derived form elsewhere with
     * the real fully qualified name of the parameter.
     * @param derivedParameter
     * @param copyParameters
     */

//    private static void rewireDerivedParameter(DerivedParameter derivedParameter, ParameterMap copyParameters) {
//        List copySourceParameters = new ArrayList();
//        List sourceParameters = derivedParameter.getSourceParameterReferences();
//
//        for (Iterator itr = sourceParameters.iterator(); itr.hasNext();) {
//            Object o = itr.next();
//            if ((o instanceof Parameter)) {
//                rewireSourceParameter((Parameter)o, derivedParameter, copyParameters, copySourceParameters);
//            } else {
//                throw new ClassCastException("You must only place Parameter objects in the source parameters - found " + o.getClass().getName());
//            }
//        }
//
//        derivedParameter.setSourceParameters(copySourceParameters);
//    }
//
//    private static void rewireSourceParameter(Parameter sourceParameter, DerivedParameter parentDerivedParameter, ParameterMap parameterMap, List copySourceParameters) {

//        if (sourceParameter == null) {
//            throw new IllegalStateException("Cannot rewire a null parameter! : " + parentDerivedParameter);
//        }
//        if (sourceParameter.hasParent()) {  // It is a parameter that lives outside this derived parameter
//            Parameter copyParam = parameterMap.findParameter(sourceParameter.getFullyQualifiedName());
//            if (copyParam == null) {
//                throw new IllegalStateException("FQN of : " + sourceParameter.getFullyQualifiedName() + "Could not find part of the derived parameter we are trying to copy!! : " + parentDerivedParameter + " looking for");
//            }
//            copySourceParameters.add(copyParam);
//        } else { // The parameter is specific to this derived parameter so can be copied straight across
//            copySourceParameters.add(sourceParameter);
//        }
//    }


    private Category copyCategory(Category srcCategory, List derivedParameters) {
        Category copyCategory = new Category(srcCategory.getName());

        for (Iterator itr = srcCategory.getParameters().iterator(); itr.hasNext();) {
            Parameter srcParameter = (Parameter)itr.next();
            Parameter copyParameter = copyParameter(srcParameter, derivedParameters);
            copyCategory.addParameter(copyParameter);
        }

        for (Iterator itr = srcCategory.getSubCategories().iterator(); itr.hasNext();) {
            Category srcSubCategory = (Category)itr.next();
            Category copySubCategory = copyCategory(srcSubCategory, derivedParameters);
            copyCategory.addSubCategory(copySubCategory);
        }
        return copyCategory;
    }

    private Parameter copyParameter(Parameter srcParameter, List derivedParameters) {
        Parameter copyParam;
        if (srcParameter instanceof StrategyDefinitionParameter) {
            copyParam = copyAlgorithmParameter((StrategyDefinitionParameter)srcParameter, derivedParameters);
        } else if (srcParameter.containsStrategy()) {
            StrategyDefinitionParameter copyValue = copyAlgorithmParameter((StrategyDefinitionParameter)srcParameter.getValue(), derivedParameters);
            copyParam = new Parameter(srcParameter.getName(), copyValue);
        } else if (srcParameter instanceof DerivedParameter) {
            DerivedParameter srcDerived = (DerivedParameter)srcParameter;
            copyParam = DerivedParameter.copyConstruct(srcDerived);

            derivedParameters.add(copyParam);
        } else {
            copyParam = new Parameter(srcParameter.getName(), srcParameter.getValue());
        }
        return copyParam;
    }



    private StrategyDefinitionParameter copyAlgorithmParameter(StrategyDefinitionParameter srcAlgorithmParameter, List derivedParameters) {
        StrategyDefinitionParameter copyAlgorithmParameter = new StrategyDefinitionParameter(srcAlgorithmParameter.getName(), srcAlgorithmParameter.getValue());
        for (Iterator itr = srcAlgorithmParameter.getParameters().iterator(); itr.hasNext();) {
            Parameter srcParameter = (Parameter)itr.next();
            Parameter copyParameter = copyParameter(srcParameter, derivedParameters);
            copyAlgorithmParameter.addParameter(copyParameter);
        }
        return copyAlgorithmParameter;
    }


// Commented out with refactoring of DerivedParameters to use prameter references...
    /**
     * Not the most efficient, but shoudl neve have THAT many params and only gets called 1 each itr
     *
     * @param currentParameters
     */
//    public void rewireDerivedParameters(ParameterMap currentParameters) {
//        List derivedParameters = currentParameters.getAllDerivedParameters();
//        if (BugsimMain.isDebug()) {
//            if (log.isInfoEnabled()) {
//                log.info("Parameters Before rewiring of derived parameters:");
//                ParameterMapDebug.debugParams(currentParameters);
//            }
//
//        }
//        rewireDerivedParameters(derivedParameters, currentParameters);
//    }


  


    private static final Logger log = Logger.getLogger(ParameterMapFactory.class);
}
 
