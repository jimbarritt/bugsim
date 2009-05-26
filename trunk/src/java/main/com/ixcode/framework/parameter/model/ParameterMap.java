package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.*;

public class ParameterMap implements IParameterModel{

    public Parameter findParameter(String fullyQualifiedName) {
        Parameter found = null;
        Stack nameStack = createQualifiedNameStack(fullyQualifiedName);

        for (Iterator itr = _categories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();

            Object obj = category.findObject(nameStack);
            if (obj != null) {
                if (!(obj instanceof Parameter)) {
                    throw new IllegalArgumentException("The object returned for qualified name '" + fullyQualifiedName + "' was not a parameter!");
                }
                found = (Parameter)obj;
                break;
            }

        }

        return found;
    }

    public static Stack createQualifiedNameStack(String fullyQualifiedName) {
        Stack nameStack = new Stack();

        StringTokenizer st = new StringTokenizer(fullyQualifiedName, ".");

        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String currentTok = st.nextToken();
            tokens.add(currentTok);
        }

        for (ListIterator itr = tokens.listIterator(tokens.size()); itr.hasPrevious();) {
            String tok = (String)itr.previous();
            nameStack.push(tok);
        }
        return nameStack;
    }

    public IParameterModel findParentCalled(String name) {
        return null;
    }

    public String getName() {
        return "Parameter Map";
    }

    public String getFullyQualifiedName() {
        return "/";
    }

    

    public boolean hasParent() {
        return false;
    }

    public IParameterModel getParent() {
        return null;
    }

//    private Parameter findParameterInAlgorithm(StringTokenizer st, AlgorithmParameter parent) {
//        Parameter found = null;
//        while (st.hasMoreTokens()) {
//            String currentTok = st.nextToken();
////            Parameter
//        }
//        return found;
//    }

    public Category findCategory(String name) {
        Category found = null;
        for (Iterator itr = _categories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            if (category.getName().equals(name)) {
                found = category;
                break;
            }
        }
        return found;
    }

    public void addCategory(Category category) {
        _categories.add(category);
        category.setRoot(this);
    }

    public List getCategories() {
        return _categories;
    }


    public List getAllParameters() {
        List allParams = new ArrayList();
        for (Iterator itr = _categories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            allParams.addAll(category.getAllParameters());
        }
        return allParams;
    }

    public List getAllParameterReferences() {
        List parameterReferences = new ArrayList();

        List derivedParameters = getAllDerivedParameters();
        for (Iterator itr = derivedParameters.iterator(); itr.hasNext();) {
            DerivedParameter derivedParameter = (DerivedParameter)itr.next();
            parameterReferences.add(derivedParameter.getSourceParameters().getParameterReferences());
        }
        return parameterReferences;
    }

    public List getAllDerivedParameters() {
        List derivedParameters = new ArrayList();
        for (Iterator itr = getAllParameters().iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (parameter instanceof DerivedParameter) {
                derivedParameters.add(parameter);
            }
        }
        return derivedParameters;
    }

    public void dereferenceAllDerivedParameters() {
        List derivedParameters = getAllDerivedParameters();
        for (Iterator itr = derivedParameters.iterator(); itr.hasNext();) {
            DerivedParameter dp  = (DerivedParameter)itr.next();
            dp.dereference();

        }
    }

    // Dont do this anymore because we leave them as references!
//    public void rewireParameterReferences() {

//        for (Iterator itr = getAllParameterReferences().iterator(); itr.hasNext();) {
//            ParameterReference parameterRef = (ParameterReference)itr.next();
//            String fqName = parameterRef.getFullyQualifiedName();
//            Parameter realParameter = findParameter(fqName);
//            if (realParameter == null) {
//                throw new IllegalStateException("Could not locate parameter reference: " + fqName);
//            }
//
//            DerivedParameter parent = parameterRef.getParentDerivedParameter();
//            if (parent == null) {
//                throw new IllegalStateException("ParameterReference should have had its parent DerivedParameter set! : " + fqName);
//            }
//
//            parent.replaceSourceParameter(parameterRef, realParameter);
//
//        }
//    }

    


    private List _categories = new ArrayList();

    private static final Logger log = Logger.getLogger(ParameterMap.class);

}
 
