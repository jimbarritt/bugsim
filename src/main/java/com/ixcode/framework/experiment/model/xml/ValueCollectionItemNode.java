/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.xml.sax.speedsax.ChildNodeBase;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.PropertyNodeMappingBuilder;
import org.xml.sax.Attributes;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ValueCollectionItemNode extends ChildNodeBase {

  public static final String ELEMENT_NAME = "item";


    public static final String A_VALUE = "value";
    public static final String A_TYPE = "type";



    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ValueCollectionItemXMLFactory.class);

    static {
        ValueCollectionItemNode.BUILDER.addAttributeMapping(ValueCollectionItemNode.ELEMENT_NAME, A_TYPE);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_VALUE, ValueCollectionItemXMLFactory.P_VALUE, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_TYPE, ValueCollectionItemXMLFactory.P_TYPE, String.class);

    }


    public ValueCollectionItemNode() {
        this(null);
    }

    public ValueCollectionItemNode(IParentNode parentNode) {
        super(ValueCollectionItemNode.ELEMENT_NAME, ValueCollectionItemNode.BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _itemFactory = new ValueCollectionItemXMLFactory();
        return _itemFactory;
    }



    private ValueCollectionItemXMLFactory _itemFactory;

}
