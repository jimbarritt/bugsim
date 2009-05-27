package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;
import org.xml.sax.Attributes;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 16-Dec-2004
 * Time: 13:02:08
 * To change this template use File | Settings | File Templates.
 */
public interface IParentNode extends IChildNode {

    void addChild(IChildNode childNode, INodeManager nodeManager);

    /**
     * @todo think this should be on IChildNode ?
     * @param qName
     * @param content
     * @param formatter
     */
    void populatePropertyFromContent(String qName, StringBuffer content, IXMLFormatter formatter);

    void populatePropertiesFromAttributes(String qName, Attributes attributes, IXMLFormatter formatter);
}
