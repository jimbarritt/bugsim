/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterManipulation;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ParameterManipulationNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "parameterManipulation";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();


    static {
        CHILD_NODE_FACTORIES.put(ParameterReferenceNode.ELEMENT_NAME, new ChildNodeFactory(ParameterReferenceNode.class));
    }

    public static final String A_VALUE_TO_SET = "valueToSet";
    public static final String A_VALUE_TYPE = "valueType";
//    public static final String A_STRUCTURE_CHANGED = "structureChanged";


    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ParameterManipulation.class);

    static {
        ParameterManipulationNode.BUILDER.addValueAttributeMapping(ELEMENT_NAME, ParameterManipulation.P_VALUE_TO_SET, A_VALUE_TO_SET, A_VALUE_TYPE);
//        ParameterManipulationNode.BUILDER.addAttributeMapping(ELEMENT_NAME, ParameterManipulation.P_STRUCTURE_CHANGED, A_STRUCTURE_CHANGED, Boolean.class);
    }


    public ParameterManipulationNode() {
        this(null);
    }

    public ParameterManipulationNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _manipulation = new ParameterManipulation();
        return _manipulation;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof ParameterReferenceNode) {
            _manipulation.setParameter((Parameter)childNode.getCreatedObject());
        }
    }


    private ParameterManipulation _manipulation;
}
