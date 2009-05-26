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
public class StrategyManipulationNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "strategyManipulation";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();


    static {
        StrategyManipulationNode.CHILD_NODE_FACTORIES.put(ParameterReferenceNode.ELEMENT_NAME, new ChildNodeFactory(ParameterReferenceNode.class));
        StrategyManipulationNode.CHILD_NODE_FACTORIES.put(StrategyNode.ELEMENT_NAME, new ChildNodeFactory(StrategyNode.class));
    }




    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ParameterManipulation.class);

    static {
    }


    public StrategyManipulationNode() {
        this(null);
    }

    public StrategyManipulationNode(IParentNode parentNode) {
        super(StrategyManipulationNode.ELEMENT_NAME, StrategyManipulationNode.CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _manipulation = new ParameterManipulation();
        return _manipulation;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof ParameterReferenceNode) {
            _manipulation.setParameter((Parameter)childNode.getCreatedObject());
        } else if (childNode instanceof StrategyNode) {
            _manipulation.setValueToSet(childNode.getCreatedObject());
        }
    }



    private ParameterManipulation _manipulation;
}
