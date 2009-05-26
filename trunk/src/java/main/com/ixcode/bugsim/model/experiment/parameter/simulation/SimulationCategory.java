/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.parameter.simulation;

import com.ixcode.bugsim.BugsimMain;
import com.ixcode.bugsim.model.experiment.parameter.simulation.timescale.TimescaleStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.simulation.timescale.TimescaleStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.simulation.timescale.ContinuousTimescaleStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.simulation.timescale.DiscreteTimescaleStrategyDefinition;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.model.Simulation;

import java.util.Map;
import java.util.HashMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SimulationCategory extends CategoryDefinition {

    public SimulationCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);

        TimescaleStrategyBase timescaleStrategy = (TimescaleStrategyBase)createStrategyDefinition(P_TIMESCALE, getTimescaleS());
        super.addStrategyDefinition(P_TIMESCALE, timescaleStrategy);

    }

    public SimulationCategory(ParameterMap params, boolean forwardEvents) {
        super(params.findCategory(CATEGORY_NAME), params, forwardEvents);
    }



    
    public static Category createDefaultS() {
        return createStrategyS(10, false, false, false, false, false, DiscreteTimescaleStrategyDefinition.createDefaultStrategyS(), false);
    }

    public static Category createStrategyS(long replicates, boolean archiveRemovedAgents, boolean archiveEscapedAgents, boolean recordBoundaryCrossings,
                                           boolean enableCompetitors, boolean enableParasitoids, StrategyDefinitionParameter timescaleS, boolean recordDispersalStatistics) {
        Category simulationC = new Category(CATEGORY_NAME);

        simulationC.addParameter(new Parameter(P_NUMBER_OF_REPLICATES, replicates));
        simulationC.addParameter(new Parameter(P_ARCHIVE_REMOVED_AGENTS, archiveRemovedAgents));
        simulationC.addParameter(new Parameter(P_ARCHIVE_ESCAPED_AGENTS, archiveEscapedAgents));
        simulationC.addParameter(new Parameter(P_RECORD_BOUNDARY_CROSSINGS, recordBoundaryCrossings));
        simulationC.addParameter(new Parameter(P_RECORD_DISPERSAL_STATISTICS, recordDispersalStatistics));
        simulationC.addParameter(new Parameter(P_ENABLE_COMPETITORS, enableCompetitors));
        simulationC.addParameter(new Parameter(P_ENABLE_PARASITOIDS, enableParasitoids));

        simulationC.addParameter(new Parameter(P_TIMESCALE, timescaleS));
        return simulationC;
    }

    // ================================================================================================================
    // Static accessors
    // ================================================================================================================


    public static Category getSimulationCategory(ParameterMap params) {
        return params.findCategory(CATEGORY_NAME);
    }

    public static Parameter getParameter(ParameterMap params, String name) {
        return getSimulationCategory(params).findParameter(name);

    }

    public static boolean getArchiveRemovedAgents(ParameterMap params) {
        return getParameter(params, P_ARCHIVE_REMOVED_AGENTS).getBooleanValue();

    }





    public static boolean getIncludeEscapedAgentsInHistory(ParameterMap params) {
        return getParameter(params, P_ARCHIVE_ESCAPED_AGENTS).getBooleanValue();
    }

    public static long getNumberOfReplicates(ParameterMap params) {
        return getParameter(params, P_NUMBER_OF_REPLICATES).getLongValue();
    }


    public static boolean getRecordBoundaryCrossings(ParameterMap params) {
        return getParameter(params, P_RECORD_BOUNDARY_CROSSINGS).getBooleanValue();
    }



    public static void setNumberOfReplicates(ParameterMap params, long replicates) {
        getParameter(params, P_NUMBER_OF_REPLICATES).setValue(replicates);
    }

    public static void setRecordBoundaryCrossings(ParameterMap params, boolean value) {
        getParameter(params,P_RECORD_BOUNDARY_CROSSINGS).setValue(value);
    }

    public static void setArchiveRemovedAgents(ParameterMap params, boolean value) {
         Parameter archiveRemovedAgents = params.findCategory(CATEGORY_NAME).findParameter(P_ARCHIVE_REMOVED_AGENTS);
         archiveRemovedAgents.setValue(value);
     }


    public static void setIncludeEscapedAgentsInHistory(ParameterMap params, boolean value) {
        getParameter(params, P_ARCHIVE_ESCAPED_AGENTS).setValue(value);
    }



    // ================================================================================================================
    // Accessors
    // ================================================================================================================

    public boolean getArchiveRemovedAgents() {
        return getParameter(P_ARCHIVE_REMOVED_AGENTS).getBooleanValue();
    }
    public boolean getArchiveEscapedAgents() {
        return getParameter(P_ARCHIVE_ESCAPED_AGENTS).getBooleanValue();
    }
    public boolean getRecordBoundaryCrossings() {
        return getParameter(P_RECORD_BOUNDARY_CROSSINGS).getBooleanValue();
    }




    public long getNumberOfReplicates() {
        return getParameter(P_NUMBER_OF_REPLICATES).getLongValue();
    }



    public void setArchiveRemovedAgents(boolean value) {
        getParameter(P_ARCHIVE_REMOVED_AGENTS).setValue(value);
    }


    public void setArchiveEscapedAgents(boolean value) {
        super.setParameterValue(P_ARCHIVE_ESCAPED_AGENTS, value);
    }


    public void setRecordBoundaryCrossings(boolean value) {
        Parameter a = getParameter(P_RECORD_BOUNDARY_CROSSINGS);
        a.setValue(value);

    }



    public void setNumberOfReplicates(long replicants) {
        super.setParameterValue(P_NUMBER_OF_REPLICATES,replicants);
    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition created = null;
        if (parameterName.equals(P_TIMESCALE)) {
            return TimescaleStrategyFactory.createTimescaleStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
          created = super.createStrategyDefinition(parameterName, strategyS);
        }
        return created;
    }


    public TimescaleStrategyBase getTimescale() {
        return (TimescaleStrategyBase)super.getStrategyDefinition(P_TIMESCALE);
    }

    public void setTimescaleS(StrategyDefinitionParameter timescaleS) {
        super.replaceStrategyDefinitionParameter(P_TIMESCALE, timescaleS);
    }


    public StrategyDefinitionParameter getTimescaleS() {
        return super.getParameter(P_TIMESCALE).getStrategyDefinitionValue();
    }


    public boolean getEnableCompetitors() {
        return super.getParameter(P_ENABLE_COMPETITORS).getBooleanValue();
    }
    public void setEnableCompetitors(boolean enableCompetitors) {
        super.getParameter(P_ENABLE_COMPETITORS).setValue(enableCompetitors);
    }

    public boolean getEnableParasitoids() {
        return super.getParameter(P_ENABLE_PARASITOIDS).getBooleanValue();
    }

    public void setEnableParasitoids(boolean enableParasitoids) {
        super.getParameter(P_ENABLE_PARASITOIDS).setValue(enableParasitoids);
    }

    public void setRecordDispersalStatistics(boolean recordDispersal) {
        super.getParameter(P_RECORD_DISPERSAL_STATISTICS).setValue(recordDispersal);
    }

    public boolean isRecordDispersalStatistics() {
        return super.getParameter(P_RECORD_DISPERSAL_STATISTICS).getBooleanValue();
    }

    public static void setSimulation(Map initialisationObjects, Simulation simulation) {
        initialisationObjects.put(CATEGORY_NAME, simulation);
    }

    public static Map createSimulationStrategyInitialisation(Simulation simulation) {
        Map map = new HashMap();
        setSimulation(map, simulation);
        return map;
    }

    public static Simulation getSimulation(Map initialisationObjects) {
        return (Simulation)initialisationObjects.get(CATEGORY_NAME);
    }

    public static boolean getRecordDispersalStatistics(ParameterMap params) {
        return getParameter(params, P_RECORD_DISPERSAL_STATISTICS).getBooleanValue();        
    }

    public static final String CATEGORY_NAME = "simulation";


    public static final String P_NUMBER_OF_REPLICATES = "numberOfReplicates";
    public static final String P_ARCHIVE_ESCAPED_AGENTS = "archiveEscapedAgents";
    public static final String P_ARCHIVE_REMOVED_AGENTS = "archiveRemovedAgents";
    public static final String P_RECORD_BOUNDARY_CROSSINGS = "recordBoundaryCrossings";
    public static final String P_RECORD_DISPERSAL_STATISTICS = "recordDispersalStatistics";
    public static final String P_TIMESCALE = "timescale";
    public static final String P_ENABLE_COMPETITORS = "enableCompetitors";
    public static final String P_ENABLE_PARASITOIDS = "enableParasitoids";




}
