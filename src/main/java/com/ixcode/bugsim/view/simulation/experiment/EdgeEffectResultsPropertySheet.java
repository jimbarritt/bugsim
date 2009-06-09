/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.experiment;

import com.ixcode.bugsim.controller.experiment.*;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.swing.property.PropertyGroupPanel;
import com.ixcode.framework.swing.property.PropertySheetBase;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EdgeEffectResultsPropertySheet extends PropertySheetBase {

    public EdgeEffectResultsPropertySheet(JavaBeanModelAdapter modelAdapter) {
        super("Results", modelAdapter);

        PropertyGroupPanel cabbagePanel = new PropertyGroupPanel("Cabbages");
        _cabbageInspector = new CabbageSummaryPanel();
        cabbagePanel.addPropertyEditor(_cabbageInspector);
        super.add(cabbagePanel);

         PropertyGroupPanel butterflyPanel = new PropertyGroupPanel("Butterflies");
        _butterfliesRemaining = new ReadOnlyPropertyEditor("butterfliesRemaining","Butterflies Remaining", 135);
        _butterfliesRemaining.setDisplayOnly(true);
        _butterfliesAlive = new ReadOnlyPropertyEditor("butterfliesAlive","Buterflies Alive", 135);
        _butterfliesAlive.setDisplayOnly(true);
        butterflyPanel.addPropertyEditor(_butterfliesRemaining);
        butterflyPanel.addPropertyEditor(_butterfliesAlive);
        super.add(butterflyPanel);

    }

    public void loadFromBean(Object instance) {
        super.loadFromBean(instance);
        RandomWalkEdgeEffectProperties experimentProperties = (RandomWalkEdgeEffectProperties)instance;
        RandomWalkEdgeEffectExperiment experiment = (RandomWalkEdgeEffectExperiment)experimentProperties.getExperiment();
        _cabbageInspector.setExperiment(experiment);


        _butterfliesRemaining.setValue("" + experiment.getButterfliesRemaining());

        IExperimentListener l = new ExperimentListenerBase(){
            public void experimentReset(IExperiment experiment) {
                RandomWalkEdgeEffectExperiment exp = (RandomWalkEdgeEffectExperiment)experiment;
                _butterfliesRemaining.setValue("" + exp.getButterfliesRemaining());
                _butterfliesAlive.setValue("" + exp.getButterfliesAlive());
            }

            public void timeStepExecuted(IExperiment experiment, long timestep) {
                RandomWalkEdgeEffectExperiment exp = (RandomWalkEdgeEffectExperiment)experiment;
                _butterfliesRemaining.setValue("" + exp.getButterfliesRemaining());
                _butterfliesAlive.setValue("" + exp.getButterfliesAlive());
            }
        };
        experiment.addExperimentListener(l);
    }



    private CabbageSummaryPanel _cabbageInspector;
    private ReadOnlyPropertyEditor _butterfliesRemaining;
    private ReadOnlyPropertyEditor _butterfliesAlive;
}
