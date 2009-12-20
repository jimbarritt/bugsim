/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.signal.surface;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 2:21:21 PM by jim
 */
public abstract class SignalSurfaceStrategyBase extends StrategyDefinition {

    public static StrategyDefinitionParameter createSignalSurfaceS(String name, String className, boolean includeSurvey,double surveyResolution, String surfaceName)  {
        StrategyDefinitionParameter surfaceS = new StrategyDefinitionParameter(name, className);
        surfaceS.addParameter(new Parameter(P_INCLUDE_SURVEY, includeSurvey));
        surfaceS.addParameter(new Parameter(P_SURVEY_RESOLUTION, surveyResolution));
        surfaceS.addParameter(new Parameter(P_SURFACE_NAME, surfaceName));
        return surfaceS;
    }

    public SignalSurfaceStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
        _includeSurvey = super.getParameter(P_INCLUDE_SURVEY).getBooleanValue();
    }

    public boolean getIncludeSurvey() {
        return _includeSurvey;
    }

    public void setIncludeSurvey(boolean includeSurvey) {
        _includeSurvey = includeSurvey;
        super.getParameter(P_INCLUDE_SURVEY).setValue(includeSurvey);
    }


    public String getSurfaceName() {
        return super.getParameter(P_SURFACE_NAME).getStringValue();
    }

    public void setSurfaceName(String name) {
        super.getParameter(P_SURFACE_NAME).setValue(name);
    }

    public double getSurveyResolution() {
            return super.getParameter(P_SURVEY_RESOLUTION).getDoubleValue();
        }

    public void setSurveyResolution(double resolution) {
        super.getParameter(P_SURVEY_RESOLUTION).setValue(resolution);
    }

    public static final String P_SURFACE_NAME = "surfaceName";

    public static final String P_INCLUDE_SURVEY = "includeSurvey";
    public static final String P_SURVEY_RESOLUTION = "surveyResolution";
    private boolean _includeSurvey;
}
