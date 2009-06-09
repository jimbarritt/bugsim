package com.ixcode.framework.experiment.model;

import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.ExperimentPlanTemplate;
import com.ixcode.framework.simulation.experiment.ExperimentTemplateRegistry;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

public class ExperimentPlan extends ModelBase {



    public static final String P_NAME = "name";
    public static final String P_TRIAL_NAME = "trialName";
    public static final String P_DESCRIPTION = "description";
    public static final String P_EXPERIMENT_NAME = "experimentName";
    public static final String P_OUTPUT_DIR_NAME = "outputDirName";

    public ExperimentPlan() {
        _parameterMap = new ParameterMap();
    }

    public ExperimentPlan(String name) {
        _parameterMap = new ParameterMap();
        _name = name;
    }


    public ParameterMap getParameterTemplate() {
        return _parameterMap;
    }

    public void addParameterManipulationSequence(List manipulationSequence) {
            _manipulationSequences.add(manipulationSequence);
            for (Iterator itr = manipulationSequence.iterator(); itr.hasNext();) {
                IParameterManipulation manipulation = (IParameterManipulation)itr.next();
                manipulation.setParent(this);
            }
    }

    public void addParameterManipulationSequence(Parameter manipulatedP, double[] values) {
        List manipulationSequence = new ArrayList();
        for (int i = 0; i < values.length; ++i) {
            manipulationSequence.add(new ParameterManipulation(manipulatedP, values[i]));
        }
        addParameterManipulationSequence(manipulationSequence);
    }


    /**
     * Parameter manipulations are basically always a combination of a Parameter and a value to set.
     * We store them in a list, so for example, if we have parameters A and B we might want to set:
     * <p/>
     * A=10
     * <p/>
     * B=40
     * <p/>
     * So we create a list of these manipulations.
     * <p/>
     * Further we might want to set :
     * <p/>
     * A=10, A=30, A=35
     * <p/>
     * B=40, B=50, B=60
     * <p/>
     * <p/>
     * but have every combination of these configured:
     * <p/>
     * A=10, B=40
     * A=30, B=40
     * A=35, B=40
     * <p/>
     * A=10, B=50
     * A=30, B=50
     * A=35, B=50
     * <p/>
     * A=10, B=60
     * A=30, B=60
     * A=35, B=60
     * <p/>
     * So we put a list of lists into the parameter map.
     *
     * @return
     */
    public List getParameterManipulationSequences() {
        return _manipulationSequences;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        Object oldVal = _name;
        _name = name;
        super.firePropertyChangeEvent(P_NAME, oldVal, _name);
    }

    public void setDescription(String description) {
        Object oldVal = _description;
        _description = description;
        super.firePropertyChangeEvent(P_DESCRIPTION, oldVal, _description);
    }


    public String getDescription() {
        return _description;
    }

    public void setTrialName(String trialName) {
        Object oldVal = _trialName;
        _trialName = trialName;
        super.firePropertyChangeEvent(P_TRIAL_NAME, oldVal, _trialName);
    }

    public String getTrialName() {
        return _trialName;
    }

    public String getTemplateName() {
        return _templateName;
    }

    public void setTemplateName(String templateName) {
        _templateName = templateName;
        _planTemplate = ExperimentTemplateRegistry.getInstance().getTemplate(_templateName);
    }

    public ExperimentPlanTemplate getTemplate() {
        return _planTemplate;
    }

    public void rewireManipulationParameterReferences() {
        for (Iterator itr = _manipulationSequences.iterator(); itr.hasNext();) {
            List sequence = (List)itr.next();
            rewireManipulationSequence(sequence);
        }
    }

    private void rewireManipulationSequence(List sequence) {
        for (Iterator itr = sequence.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            manipulation.rewireParameterReferences(_parameterMap);
            manipulation.setParent(this);
        }
    }

    /**
     * Under certain circumstances it may not have been possible to have bound all the parameters
     * in the map - for example when loading from XML. in this case we can
     * *force* a bind of everything.
     */
    public void bindDerivedParameters() {
        if (log.isDebugEnabled()) {
            log.debug("[bind] : Rebinding All Derived Parameters...");
        }
        List derivedParameter = _parameterMap.getAllDerivedParameters();
        for (Iterator itr = derivedParameter.iterator(); itr.hasNext();) {
            DerivedParameter parameter = (DerivedParameter)itr.next();
            parameter.bind();
        }
    }

    private ParameterMap _parameterMap;

    private List _manipulationSequences = new ArrayList();


    public String getOutputDirName() {
        return _outputDirName;
    }

    public void setOutputDirName(String outputDirName) {
        _outputDirName = outputDirName;
    }

    public String getExperimentName() {
        return _experimentName;
    }

    public void setExperimentName(String experimentName) {
        _experimentName = experimentName;
    }

    private String _name;
    private static final Logger log = Logger.getLogger(ExperimentPlan.class);
    private String _description;
    private String _trialName;
    private String _templateName;
    private ExperimentPlanTemplate _planTemplate;


    private String _outputDirName;
    private String _experimentName;
}
 
