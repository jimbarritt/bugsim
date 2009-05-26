/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ParameterListNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "parameterList";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();




    static {
         CHILD_NODE_FACTORIES.put(ELEMENT_NAME, new ChildNodeCollectionFactory(ELEMENT_NAME, new String[]{ParameterNode.ELEMENT_NAME, StrategyNode.ELEMENT_NAME, DerivedParameterNode.ELEMENT_NAME}, new Class[]{ParameterNode.class, StrategyNode.class, DerivedParameterNode.class}));

    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(List.class);

    static {
      //put property mappings here....
    }


    public ParameterListNode() {
        this(null);
    }

    public ParameterListNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    /**
     * @return
     * @param attributes
     * @param nodeManager
     */
    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _parameters = new ArrayList();
        return _parameters;
    }



    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof StrategyNode) {
            _parameters.add(childNode.getCreatedObject());
        } else if (childNode instanceof ParameterNode) {
            _parameters.add(childNode.getCreatedObject());
        } if (childNode instanceof DerivedParameterNode) {
            _parameters.add(childNode.getCreatedObject());
        }
    }


    private Collection _parameters;

}
