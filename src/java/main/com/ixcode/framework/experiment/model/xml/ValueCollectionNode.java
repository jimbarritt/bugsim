/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ValueCollectionNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "valueCollection";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    public static final String A_TYPE = "type";


    static {
        CHILD_NODE_FACTORIES.put(ValueCollectionItemNode.ELEMENT_NAME, new ChildNodeFactory(ValueCollectionItemNode.class));

    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(Parameter.class);

    static {
      //put property mappings here....
    }


    public ValueCollectionNode() {
        this(null);
    }

    public ValueCollectionNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    /**
     * @return
     * @param attributes
     * @param nodeManager
     */
    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _items = instantiateCollection(attributes.getValue(A_TYPE));
        return _items;
    }

    private Collection instantiateCollection(String collectionClassName) {
        try {
            Class collectionClass = Thread.currentThread().getContextClassLoader().loadClass(collectionClassName);
            return (Collection)collectionClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof ValueCollectionItemNode) {
            ValueCollectionItemXMLFactory factory = (ValueCollectionItemXMLFactory)childNode.getCreatedObject();
            Object item = factory.createItem(nodeManager.getXMLFormatter());
            _items.add(item);
        }
    }


    private Collection _items;

}
