/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentTemplateRegistry {

    protected ExperimentTemplateRegistry() {

    }


    public static ExperimentTemplateRegistry getInstance() {
        return INSTANCE;
    }

    public static void setExperimentTemplateInstance(ExperimentTemplateRegistry instance) {
        INSTANCE =instance;
    }

    public void registerTemplate(String templateName, String description, String longDescription,Class defaultFactoryClass, String defaultFactoryMethod, Class configurationClass, String configurationMethod) {
        registerTemplate(templateName, description,longDescription, defaultFactoryClass.getName(), defaultFactoryMethod, configurationClass.getName(), configurationMethod);

    }
    public void registerTemplate(String templateName, String description, String longDescription, String defaultFactoryClass, String defaultFactoryMethod, String configurationClass, String configurationMethod) {
        ExperimentPlanTemplate planTemplate = new ExperimentPlanTemplate(templateName, description,longDescription,configurationClass, configurationMethod, defaultFactoryClass, defaultFactoryMethod);
        registerTemplate(planTemplate);
    }

    public void registerTemplate(ExperimentPlanTemplate planTemplate) {
        _templates.put(planTemplate.getTemplateName(), planTemplate);
        _templateList.add(planTemplate);
    }

    public ExperimentPlanTemplate getTemplate(String name) {
        if (!_templates.containsKey(name)) {
            throw new IllegalArgumentException("No template called '" + name + "' in Registry!");
        }
        return (ExperimentPlanTemplate)_templates.get(name);
    }

    public List getTemplates() {
        return _templateList;
    }




    private static ExperimentTemplateRegistry INSTANCE = new ExperimentTemplateRegistry();
    private Map _templates = new HashMap();
    private List _templateList = new ArrayList();
}
