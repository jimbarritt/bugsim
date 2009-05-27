package com.ixcode.framework.xml.sax.speedsax;

import org.xml.sax.Attributes;

import java.util.Map;

/**
 */
public abstract class ChildNodeBase extends NodeBase implements IChildNode {

    public ChildNodeBase(String elementName, IParentNode parentNode) {
        this(elementName, null, parentNode);
    }
    public ChildNodeBase(String elementName, Map propertyMappings, IParentNode parentNode) {
        super(elementName, propertyMappings);
        _parentNode = parentNode;

    }

    public IParentNode getParentNode() {
        return _parentNode;
    }

    public void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes) {
        super.handleStartElement(nodeManager, qName, attributes);
        if (qName.equals(getElementName())&& hasParentNode()) {
            _parentNode.addChild(this, nodeManager);
        }
    }

    public boolean hasParentNode() {
        return _parentNode != null;
    }


    /**
     *
     * <aaa>
     *      <bbb>
     *      </bbb>
     *      <ccc>
     *          <ddd>
     *          </ddd>
     *          <ddd>
     *          </ddd>
     *          <ddd>
     *          </ddd>
     *      </ccc>
     * </aaa>
     *
     * In the case of the tags </bbb> and </ccc> we wish to pass control back to the parent node.
     *
     * in </ddd> we want the current tag to remain in control but we leave this upto the parent     
     *
     */
    public void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        super.handleEndElement(nodeManager, qName, content);
        if (_parentNode != null && (qName.equals(_parentNode.getElementName()) ||
                qName.equals(getElementName()))) {
            popChildFromStack(nodeManager);
        }

    }

    /**
     * Takes us off the stack and replaces us with the parent as the current node.
     */
    private void popChildFromStack(INodeManager nodeManager) {
        nodeManager.setCurrentParentNode(_parentNode.getParentNode());
        nodeManager.setCurrentNode(_parentNode);
    }

    private boolean isSingleChild() {
        return false;
    }

    public String getAttributeValue(Attributes attributes, String qname) {
        Object value = attributes.getValue(qname);
        if (value == null) {
            throw new IllegalStateException("No attribute found called '" + qname + "' node: " + this.getClass().getName());
        }
        return (String)value;
    }


    private IParentNode _parentNode;
}
