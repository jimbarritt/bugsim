package com.ixcode.framework.xml.sax.speedsax;

import org.xml.sax.Attributes;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 17-Dec-2004
 * Time: 13:30:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class ParentNodeBase extends ChildNodeBase implements IParentNode {


    protected ParentNodeBase(String elementName, Map childNodeFactories, IParentNode parentNode) {
        super(elementName, parentNode);
        _childNodeFactories = childNodeFactories;
    }

    protected ParentNodeBase(String elementName, Map childNodeFactories, Map propertyMappings, IParentNode parentNode) {
        super(elementName, propertyMappings, parentNode);
        _childNodeFactories = childNodeFactories;
    }


    /**
     * @todo implement the ability to have nested recursive objects with the same name but no containing tag, e.g.
     *
     * <category>
     *      <category>
     *          <category>
     *          </category>
     *          <category>
     *          </category>
     *      </category>
     * </category>
     * @param nodeManager
     * @param qName
     * @param attributes
     */
    public void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes) {
        super.handleStartElement(nodeManager, qName, attributes);

        if (_childNodeFactories.containsKey(qName)) {
            IChildNodeFactory childNodeFactory = (IChildNodeFactory)_childNodeFactories.get(qName);
            INode childNode = childNodeFactory.createNode(this);
            nodeManager.setCurrentParentNode(this);
            nodeManager.setCurrentNode(childNode);
            childNode.handleStartElement(nodeManager, qName, attributes);
        }

    }


    private Map _childNodeFactories;
}
