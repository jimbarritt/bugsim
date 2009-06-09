/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experiment2a;

import com.ixcode.bugsim.model.agent.butterfly.population.SimplePopulationFactory;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.experiment.IBugsimExperiment;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.PropertyBinding;
import com.ixcode.framework.swing.property.PropertyGroupPanel;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class Experiment2aProgressPanel extends PropertyGroupPanel {

    public Experiment2aProgressPanel(IBugsimExperiment experiment) throws JavaBeanException {
        super("EdgeEffect experiment progress");
        IModelAdapter modelAdapter = new JavaBeanModelAdapter();

        ReadOnlyPropertyEditor remaining = new ReadOnlyPropertyEditor(SimplePopulationFactory.P_FORAGERS_REMAINING_FORMATTED, "Butterflies Remaining", 150);
        PropertyBinding.bindEditor(remaining, modelAdapter, experiment.getPopulationWeb());
        super.addPropertyEditor(remaining);


        ReadOnlyPropertyEditor alive = new ReadOnlyPropertyEditor(SimplePopulationFactory.P_FORAGERS_ALIVE_FORMATTED, "Butterflies Alive", 150);
        PropertyBinding.bindEditor(alive, modelAdapter, experiment.getPopulationWeb());
        super.addPropertyEditor(alive);



        ReadOnlyPropertyEditor eggCount = new ReadOnlyPropertyEditor(EggCounter.PROPERTY_EGG_COUNT_FORMATTED, "Egg Count", 150);
        PropertyBinding.bindEditor(eggCount, modelAdapter, experiment.getPopulationWeb().getForagerPopulationFactory().getEggCounter());
        super.addPropertyEditor(eggCount);




    }

}
