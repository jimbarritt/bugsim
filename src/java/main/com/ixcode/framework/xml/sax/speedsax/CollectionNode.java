package com.ixcode.framework.xml.sax.speedsax;

import org.xml.sax.Attributes;

import java.util.Map;


/**
 */
public class CollectionNode extends ParentNodeBase {

    CollectionNode(String elementName, IParentNode parentNode, Map childNodeFactories) {
        super(elementName, childNodeFactories, parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
           return null;
    }


    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        getParentNode().addChild(childNode, nodeManager);
    }


    public void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes) {
        super.handleStartElement(nodeManager, qName, attributes);
    }

}
