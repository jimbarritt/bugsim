/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.javabean.IntrospectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanTemplate {
    public static final String P_LONG_DESCRIPTION = "longDescription";
    public static final String P_TEMPLATE_NAME = "templateName";
    public static final String P_SHORT_DESCRIPTION = "shortDescription";

    public ExperimentPlanTemplate() {
    }

    public ExperimentPlanTemplate(String templateName, String description, String longDescription, String templateClassName, String templateMethodName, String defaultPlanFactoryClass, String defaultPlanFactoryMethod) {
        _templateName = templateName;
        _description = description;
        _longDescription = longDescription;
        _templateClassName = templateClassName;
        _templateMethodName = templateMethodName;
        _defaultPlanFactoryClass = defaultPlanFactoryClass;
        _defaultPlanFactoryMethod = defaultPlanFactoryMethod;
    }

    public ExperimentPlan createPlan() {
        ExperimentPlan plan = createDefaultPlan();

        configurePlan(_templateClassName, _templateMethodName, plan);

        return plan;
    }

    private ExperimentPlan createDefaultPlan() {
        try {
            Method factoryMethod = IntrospectionUtils.getMethod(_defaultPlanFactoryClass, _defaultPlanFactoryMethod, new Class[] {});
            return (ExperimentPlan)factoryMethod.invoke(null, new Object[]{});
        } catch(InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void configurePlan(String templateClassName, String methodName, ExperimentPlan plan) {
        try {
            Method configureMethod = IntrospectionUtils.getMethod(templateClassName, methodName, new Class[]{ExperimentPlan.class}) ;
            configureMethod.invoke(null, new Object[]{plan});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public String getTemplateName() {
        return _templateName;
    }

    public String getTemplateClassName() {
        return _templateClassName;
    }

    public String getTemplateMethodName() {
        return _templateMethodName;
    }

    public String getDefaultPlanFactoryClass() {
        return _defaultPlanFactoryClass;
    }

    public String getDefaultPlanFactoryMethod() {
        return _defaultPlanFactoryMethod;
    }

    public String getShortDescription() {
        return _description;
    }

    public String getLongDescription() {
        return _longDescription;
    }

    public void setTemplateName(String templateName) {
        _templateName = templateName;
    }

    public void setLongDescription(String longDescription) {
        _longDescription = longDescription;
    }

    public void setShortDescription(String description) {
        _description = description;
    }


    private String _templateName;
    private String _templateClassName;
    private String _templateMethodName;

    private String _defaultPlanFactoryClass;
    private String _defaultPlanFactoryMethod;
    private String _description;
    private String _longDescription;

}
