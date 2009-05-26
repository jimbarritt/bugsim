/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.IParameterManipulation;
import com.ixcode.framework.parameter.model.MultipleParameterManipulation;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MultipleParameterManipulationNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "multipleManipulation";


    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    public static final String N_MANIPULATIONS = "manipulations";


    static {
        CHILD_NODE_FACTORIES.put(N_MANIPULATIONS, new ChildNodeCollectionFactory(N_MANIPULATIONS, new String[]{ParameterManipulationNode.ELEMENT_NAME,StrategyManipulationNode.ELEMENT_NAME,  MultipleParameterManipulationNode.ELEMENT_NAME}, new Class[]{ParameterManipulationNode.class, StrategyManipulationNode.class,  MultipleParameterManipulationNode.class}));
    }


    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(MultipleParameterManipulation.class);

    static {
    }


    public MultipleParameterManipulationNode() {
        this(null);
    }

    public MultipleParameterManipulationNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _multipleParameterManipulation = new MultipleParameterManipulation();
        return _multipleParameterManipulation;
    }


    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof ParameterManipulationNode) {
            _multipleParameterManipulation.addParameterManipulation((IParameterManipulation)childNode.getCreatedObject());
        } else if (childNode instanceof MultipleParameterManipulationNode) {
            _multipleParameterManipulation.addParameterManipulation((IParameterManipulation)childNode.getCreatedObject());
        } else if (childNode instanceof StrategyManipulationNode) {
            _multipleParameterManipulation.addParameterManipulation((IParameterManipulation)childNode.getCreatedObject());
        }
    }


    private MultipleParameterManipulation _multipleParameterManipulation;
}
