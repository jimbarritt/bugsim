/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.experiment.model.ExperimentPlanFile;
import com.ixcode.framework.xml.sax.speedsax.IChildNode;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.ParentNodeBase;
import com.ixcode.framework.xml.sax.speedsax.PropertyNodeMappingBuilder;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanFileXMLNode extends ParentNodeBase {


    public static final String ELEMENT_NAME = "experimentPlan";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();



    static {
//        CHILD_NODE_FACTORIES.put(ExperimentPlanDescriptionNode.ELEMENT_NAME, new ChildNodeFactory(ExperimentPlanDescriptionNode.class));
    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ExperimentPlanFile.class);

    static {
        BUILDER.addNodeMapping("description");
    }


    public ExperimentPlanFileXMLNode() {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), null);
    }


    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _experimentPlanFile = new ExperimentPlanFile();
        return _experimentPlanFile;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {

    }


    private ExperimentPlanFile _experimentPlanFile;

}
