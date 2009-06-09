/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.simulation.experiment.ExperimentPlanTemplate;
import com.ixcode.framework.xml.sax.speedsax.ChildNodeBase;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.PropertyNodeMappingBuilder;
import org.xml.sax.Attributes;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanTemplateNode extends ChildNodeBase {

    public static final String ELEMENT_NAME = "template";


    public static final String A_NAME = "name";
    public static final String A_DESCRIPTION = "description";


    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ExperimentPlanTemplate.class);

    static {
        // Have to add the node mapping first if want to map the content...
        // but we dont need these because we only need the name...
//        BUILDER.addNodeMapping(ELEMENT_NAME, ExperimentPlanTemplate.P_LONG_DESCRIPTION);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_NAME, ExperimentPlanTemplate.P_TEMPLATE_NAME, String.class);
//        BUILDER.addAttributeMapping(ELEMENT_NAME, A_DESCRIPTION, ExperimentPlanTemplate.P_SHORT_DESCRIPTION, String.class);

    }


    public ExperimentPlanTemplateNode(IParentNode parentNode) {
        super(ELEMENT_NAME, BUILDER.getNodeMappings(), parentNode);
    }


    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _experimentPlanTemplate = new ExperimentPlanTemplate();
        return _experimentPlanTemplate;
    }



    private ExperimentPlanTemplate _experimentPlanTemplate;
}
