/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.experiment;

import com.ixcode.bugsim.controller.experiment.RandomWalkEdgeEffectExperiment;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentInspectorPanelRegistry {

    private  static final ExperimentInspectorPanelRegistry INSTANCE = new ExperimentInspectorPanelRegistry();
    private ExperimentInspectorPanelRegistry() {
        registerInspectorPanel(RandomWalkEdgeEffectExperiment.class, new CabbageSummaryPanel());
    }

    public static ExperimentInspectorPanelRegistry getInstance() {
        return INSTANCE;
    }

    public void registerInspectorPanel(Class experimentClass, IExperimentInspectorPanel inspectorPanel) {
        _registry.put(experimentClass.getName(), inspectorPanel);
    }

    public IExperimentInspectorPanel getPanel(Class experimentClass) {
        return (IExperimentInspectorPanel)_registry.get(experimentClass.getName());
    }

    private Map _registry = new HashMap();
}
