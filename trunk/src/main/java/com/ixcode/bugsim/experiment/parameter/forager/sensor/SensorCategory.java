/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.sensor;

import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.SignalSensorOlfactoryStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual.VisualSensorStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual.VisualSensorStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual.FieldOfViewVisualStrategyDefinition;
import com.ixcode.bugsim.model.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.SensorParameters;

import java.util.ArrayList;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 12:16:22 PM by jim
 */
public class SensorCategory extends CategoryDefinition {

    public static Category createSensorC() {

        Category sensoryApparatusC = new Category(C_SENSOR);

        StrategyDefinitionParameter visualS = FieldOfViewVisualStrategyDefinition.createDefaultStrategyS();
        Parameter visionP = new Parameter(P_VISUAL, visualS);
        sensoryApparatusC.addParameter(visionP);

        StrategyDefinitionParameter olfactionS = new StrategyDefinitionParameter(SignalSensorOlfactoryStrategyDefinition.S_SIGNAL_SENSOR_OLFACTION, SignalSensorOlfactoryStrategy.class.getName());
        SensorParameters.addSensorParameters(olfactionS, new ArrayList());

        Parameter olfactionP = new Parameter(P_OLFACTORY, olfactionS);
        sensoryApparatusC.addParameter(olfactionP);
        return sensoryApparatusC;

    }
    public SensorCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);

        StrategyDefinitionParameter visualS = super.getParameter(P_VISUAL).getStrategyDefinitionValue();
        VisualSensorStrategyBase visualStrategy = (VisualSensorStrategyBase)VisualSensorStrategyFactory.getRegistry().constructStrategy(visualS, params, forwardEvents);
        super.addStrategyDefinition(P_VISUAL, visualStrategy);

        StrategyDefinitionParameter olfactoryS = super.getParameter(P_OLFACTORY).getStrategyDefinitionValue();
        OlfactorySensorStrategyBase olfactoryStrategy = (OlfactorySensorStrategyBase)OlfactorySensorStrategyFactory.getRegistry().constructStrategy(olfactoryS, params, forwardEvents);
        super.addStrategyDefinition(P_OLFACTORY, olfactoryStrategy);
    }


    public VisualSensorStrategyBase getVisualSensorStrategy() {
        return (VisualSensorStrategyBase)super.getStrategyDefinition(P_VISUAL);
    }

    public void setVisualSensorStrategy(VisualSensorStrategyBase visualStrategy) {
        super.replaceStrategyDefinition(P_VISUAL, visualStrategy);
    }

    public OlfactorySensorStrategyBase getOlfactorySensorStrategy() {
        return (OlfactorySensorStrategyBase)super.getStrategyDefinition(P_OLFACTORY);
    }


    public void setOlfactorySensorStrategy(OlfactorySensorStrategyBase strategy) {
        super.replaceStrategyDefinition(P_OLFACTORY, strategy);
    }

    public static final String C_SENSOR = "sensor";

    public static final String P_VISUAL = "visual";
    public static final String P_OLFACTORY = "olfactory";
}
