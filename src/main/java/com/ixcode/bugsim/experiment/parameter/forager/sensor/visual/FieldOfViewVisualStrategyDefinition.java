/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.sensor.visual;

import com.ixcode.bugsim.agent.butterfly.FieldOfViewVisualStrategy;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 5, 2007 @ 3:32:10 PM by jim
 */
public class FieldOfViewVisualStrategyDefinition extends VisualSensorStrategyBase {

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(100, 100, 0, 0);
    }

    public static StrategyDefinitionParameter createStrategyS(double fieldDepth, double fieldWidth, double signalThreshold, double luminosityGamma) {
        StrategyDefinitionParameter strategyS = new StrategyDefinitionParameter(FieldOfViewVisualStrategyDefinition.S_FIELD_OF_VIEW_VISION, FieldOfViewVisualStrategy.class.getName());
        strategyS.addParameter(new Parameter(FieldOfViewVisualStrategyDefinition.P_FIELD_DEPTH, fieldDepth));
        strategyS.addParameter(new Parameter(FieldOfViewVisualStrategyDefinition.P_FIELD_WIDTH, fieldWidth));
        strategyS.addParameter(new Parameter(FieldOfViewVisualStrategyDefinition.P_SIGNAL_THRESHOLD, signalThreshold));
        strategyS.addParameter(new Parameter(FieldOfViewVisualStrategyDefinition.P_LUMINOSITY_GAMMA, luminosityGamma));

        return strategyS;
    }

    public FieldOfViewVisualStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public FieldOfViewVisualStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getFieldDepth() {
        return super.getParameter(P_FIELD_DEPTH).getDoubleValue();
    }

    public void setFieldDepth(double fieldDepth) {
        super.getParameter(P_FIELD_DEPTH).setValue(fieldDepth);
    }

    public double getFieldWidth() {
        return super.getParameter(P_FIELD_WIDTH).getDoubleValue();
    }

    public void setFieldWidth(double fieldDepth) {
        super.getParameter(P_FIELD_WIDTH).setValue(fieldDepth);
    }

    public double getSignalThreshold() {
        return super.getParameter(P_SIGNAL_THRESHOLD).getDoubleValue();
    }

    public void setSignalThreshold(double fieldDepth) {
        super.getParameter(P_SIGNAL_THRESHOLD).setValue(fieldDepth);
    }
    public double getLuminosityGamma() {
       return super.getParameter(P_LUMINOSITY_GAMMA).getDoubleValue();
    }

    public void setLuminosityGamma(double gamma) {
        super.getParameter(P_LUMINOSITY_GAMMA).setValue(gamma);
    }

    public static final String P_LUMINOSITY_GAMMA = "luminosityGamma";


    public static final String S_FIELD_OF_VIEW_VISION = "fieldOfViewVision";

    public static final String P_FIELD_DEPTH = "fieldDepth";
    public static final String P_FIELD_WIDTH = "fieldWidth";
    public static final String P_SIGNAL_THRESHOLD = "signalThreshold";
}                                                                                   
