/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter;

import com.ixcode.bugsim.experiment.parameter.forager.*;
import com.ixcode.bugsim.experiment.parameter.landscape.*;
import com.ixcode.bugsim.experiment.parameter.resource.*;
import com.ixcode.bugsim.experiment.parameter.simulation.*;
import com.ixcode.framework.parameter.model.*;


/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BugsimParameterMap {

    public static void addDefaultParameters(ParameterMap params) {
        Category simulationC = SimulationCategory.createDefaultS();
        Category environmentC = BugsimParameterMap.createEnvironmentC();
        Category landscapeC = LandscapeCategory.createLandscapeCategory(100);
        Category resourceC = ResourceCategory.createDefaultC();
        Category foragerC = ForagerCategory.createDefaultC();
        Category competitorC = CompetitorCategory.createDefaultC();
        Category parasitoidC= ParasitoidCategory.createDefaultC();


        params.addCategory(simulationC);
        params.addCategory(environmentC);
        environmentC.addSubCategory(landscapeC);
        environmentC.addSubCategory(resourceC);

        params.addCategory(foragerC);
        params.addCategory(competitorC);
        params.addCategory(parasitoidC);

    }

    public BugsimParameterMap(ParameterMap parameterMap, boolean forwardEvents) {
        _parameterMap = parameterMap;

        _simulationCategory = new SimulationCategory(getParameterMap().findCategory(SimulationCategory.CATEGORY_NAME), parameterMap, forwardEvents);
        _environmentCategory = getParameterMap().findCategory(C_ENVIRONMENT);
        _landscapeCategory = new LandscapeCategory(_environmentCategory.findCategory(LandscapeCategory.C_LANDSCAPE), parameterMap, forwardEvents);
        _resourceCategory = new ResourceCategory(_environmentCategory.findCategory(ResourceCategory.C_RESOURCE), parameterMap, forwardEvents);
        _foragerCategory = new ForagerCategory(getParameterMap().findCategory(ForagerCategory.CATEGORY_NAME), parameterMap, forwardEvents);
        _competitorCategory = new CompetitorCategory(getParameterMap().findCategory(ForagerCategory.CATEGORY_NAME), parameterMap, forwardEvents);
        _parasitoidCategory = new ParasitoidCategory(getParameterMap().findCategory(ForagerCategory.CATEGORY_NAME), parameterMap, forwardEvents);

        _forwardEvents = forwardEvents;


    }
    public ParameterMap getParameterMap() {
        return _parameterMap;
    }

    public Category getCategory(String name) {
        return getParameterMap().findCategory(name);
    }

    public SimulationCategory getSimulationCategory() {
        return _simulationCategory;
    }

    /**
     * @todo refactor so that landscape category is at the top level with Simulation and remove Environment and replace it with "Resources"
     * @return
     */
    public LandscapeCategory getLandscapeCategory() {
        return _landscapeCategory;
    }


    public ResourceCategory getResourceCategory() {
        return _resourceCategory;
    }


    public boolean isForwardEvents() {
        return _forwardEvents;
    }

    public ForagerCategory getForagerCategory() {
        return _foragerCategory;
    }


    public Category getEnvironmentCategory() {
        return _environmentCategory;
    }

    public CompetitorCategory getCompetitorCategory() {
        return _competitorCategory;
    }

    public static Category createEnvironmentC() {
        return new Category(C_ENVIRONMENT);
    }

    public ParasitoidCategory getParasitoidCategory() {
        return _parasitoidCategory;
    }

    private SimulationCategory _simulationCategory;
    private LandscapeCategory _landscapeCategory;
    public static final String C_ENVIRONMENT = "environment";
    private Category _environmentCategory;
    private ResourceCategory _resourceCategory;
    private ParameterMap _parameterMap;
    private boolean _forwardEvents;
    private ForagerCategory _foragerCategory;
    private CompetitorCategory _competitorCategory;
    private ParasitoidCategory _parasitoidCategory;
}
