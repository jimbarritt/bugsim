/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment1d;

import com.ixcode.framework.swing.property.PropertyGroupPanel;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment1dProgressPanel extends PropertyGroupPanel {

    public Experiment1dProgressPanel(ISimulationExperiment experiment) throws JavaBeanException {
        super("Experiment 1d progress");
        IModelAdapter modelAdapter = new JavaBeanModelAdapter();




//        ReadOnlyPropertyEditor eggCount = new ReadOnlyPropertyEditor(EggCounter.PROPERTY_EGG_COUNT_FORMATTED, "Egg Count", 150);
//        PropertyBinding.bindEditor(eggCount, modelAdapter, experiment.getEggCounter());
//        super.addPropertyEditor(eggCount);




    }

}
