/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 24, 2007 @ 3:17:05 PM by jim
 */
public class ManipulationSequenceNode extends ParentNodeBase {
    public static final String ELEMENT_NAME = "manipulationSequence";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();


    static {
        CHILD_NODE_FACTORIES.put(ParameterManipulationNode.ELEMENT_NAME, new ChildNodeFactory(ParameterManipulationNode.class));
        CHILD_NODE_FACTORIES.put(StrategyManipulationNode.ELEMENT_NAME, new ChildNodeFactory(StrategyManipulationNode.class));
        CHILD_NODE_FACTORIES.put(MultipleParameterManipulationNode.ELEMENT_NAME, new ChildNodeFactory(MultipleParameterManipulationNode.class));

    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(String.class);

    static {
    }


    public ManipulationSequenceNode() {
        this(null);
    }

    public ManipulationSequenceNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }


    /***
     * DONT FORGET that we just add this list object to the list of sequences and then add the maniuplations directly to it
     * This is the ACTUAL OBJECT which is in the plan.
     * @param attributes
     * @param nodeManager
     * @return
     */
    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _manipulationSequence = new ArrayList();
        return _manipulationSequence;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof ParameterManipulationNode) {
            _manipulationSequence.add(childNode.getCreatedObject());
        } else if (childNode instanceof MultipleParameterManipulationNode) {
            _manipulationSequence.add(childNode.getCreatedObject());
        }  else if (childNode instanceof StrategyManipulationNode) {
            _manipulationSequence.add(childNode.getCreatedObject());
        }

    }

    public Object getCreatedObject() {
        return super.getCreatedObject();
    }

    private List _manipulationSequence;
}
