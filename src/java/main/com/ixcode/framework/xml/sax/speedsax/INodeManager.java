package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 17-Dec-2004
 * Time: 11:05:12
 * To change this template use File | Settings | File Templates.
 */
public interface INodeManager {

    void setCurrentNode(INode node);

    IParentNode getRootNode();

    void setCurrentParentNode(IParentNode parentNode);

    IParentNode getCurrentParentNode();

    INode getCurrentNode();

    IXMLFormatter getXMLFormatter();
}
