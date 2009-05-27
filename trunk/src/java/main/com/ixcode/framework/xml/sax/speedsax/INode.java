package com.ixcode.framework.xml.sax.speedsax;

import org.xml.sax.Attributes;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 16-Dec-2004
 * Time: 13:02:00
 * To change this template use File | Settings | File Templates.
 */
public interface INode {

    String getElementName();




    Object getCreatedObject();

    void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes);

    void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content);
}
