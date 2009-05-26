/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DerivedCalculationFactory {

    public DerivedCalculationFactory() {
    }

    public IDerivedParameterCalculation createCalculation() {
        try {
            return (IDerivedParameterCalculation)_class.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getClassName() {
        return _className;
    }

    public void setClassName(String className) {
        _className = className;
        try {
            _class = Thread.currentThread().getContextClassLoader().loadClass(_className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String _className;
    private Class _class;
}
