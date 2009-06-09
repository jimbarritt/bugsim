/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ParameterNode extends ParentNodeBase {

  public static final String ELEMENT_NAME = "parameter";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    public static final String A_NAME = "name";
    public static final String A_VALUE = "value";
    public static final String A_TYPE = "type";
    public static final String A_COMMENT = "comment";




    static {
        CHILD_NODE_FACTORIES.put(StrategyNode.ELEMENT_NAME, new ChildNodeFactory(StrategyNode.class));
        CHILD_NODE_FACTORIES.put(ValueCollectionNode.ELEMENT_NAME, new ChildNodeFactory(ValueCollectionNode.class));
        CHILD_NODE_FACTORIES.put(ParameterListNode.ELEMENT_NAME, new ChildNodeFactory(ParameterListNode.class));
    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(Parameter.class);

    static {
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_NAME, Parameter.P_NAME, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_COMMENT, Parameter.P_COMMENT, String.class);
        BUILDER.addValueAttributeMapping(ELEMENT_NAME, Parameter.P_VALUE, A_VALUE, A_TYPE);
    }


    public ParameterNode() {
        this(null);
    }

    public ParameterNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _parameter = new Parameter();
        return _parameter;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof StrategyNode) {
            _parameter.setValue(childNode.getCreatedObject());
        }  else if (childNode instanceof ValueCollectionNode) {
            _parameter.setValue(childNode.getCreatedObject());
        } else if (childNode instanceof ParameterListNode) {
            _parameter.setValue(childNode.getCreatedObject());
        }
    }


    private Parameter _parameter;

}
