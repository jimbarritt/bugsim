/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.simulation.experiment.ExperimentPlanTemplate;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanNode extends ParentNodeBase implements IRootNode{

    public static final String ELEMENT_NAME = "experimentPlan";

    public static final String N_DESCRIPTION = "description";

    public static final String A_NAME = "name";
    public static final String A_TRIAL_NAME = "trialName";
    public static final String A_EXPERIMENT_NAME = "experimentName";
    public static final String A_OUTPUT_DIR_NAME = "outputDirName";


    private static final Map CHILD_NODE_FACTORIES = new HashMap();


    private static final String N_CATEGORIES = "categories";
    private static final String N_PARAMETER_MANIPULATIONS = "parameterManipulations";

    static {
        CHILD_NODE_FACTORIES.put(ExperimentPlanTemplateNode.ELEMENT_NAME, new ChildNodeFactory(ExperimentPlanTemplateNode.class));
        CHILD_NODE_FACTORIES.put(N_CATEGORIES, new ChildNodeCollectionFactory(N_CATEGORIES, CategoryNode.ELEMENT_NAME, CategoryNode.class));
        CHILD_NODE_FACTORIES.put(N_PARAMETER_MANIPULATIONS, new ChildNodeCollectionFactory(N_PARAMETER_MANIPULATIONS, ManipulationSequenceNode.ELEMENT_NAME, ManipulationSequenceNode.class));

    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ExperimentPlan.class);

    static {
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_NAME, ExperimentPlan.P_NAME, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_TRIAL_NAME, ExperimentPlan.P_TRIAL_NAME, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_EXPERIMENT_NAME, ExperimentPlan.P_EXPERIMENT_NAME, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_OUTPUT_DIR_NAME, ExperimentPlan.P_OUTPUT_DIR_NAME, String.class);
        BUILDER.addNodeMapping(N_DESCRIPTION);
    }


    public ExperimentPlanNode() {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), null);
    }


    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _experimentPlan = new ExperimentPlan();
        return _experimentPlan;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof ExperimentPlanTemplateNode) {
            ExperimentPlanTemplate template = (ExperimentPlanTemplate)childNode.getCreatedObject();
            _experimentPlan.setTemplateName(template.getTemplateName());
        } else if (childNode instanceof CategoryNode) {
            Category category = (Category)childNode.getCreatedObject();
            _experimentPlan.getParameterTemplate().addCategory(category);
        } else if (childNode instanceof ManipulationSequenceNode) {
            _experimentPlan.addParameterManipulationSequence((List)childNode.getCreatedObject());
        }
    }

    public void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        super.handleEndElement(nodeManager, qName, content);
        _experimentPlan.bindDerivedParameters();
    }


    private ExperimentPlan _experimentPlan;


}
