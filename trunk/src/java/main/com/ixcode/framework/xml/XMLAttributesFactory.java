/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.xml;

import org.xml.sax.Attributes;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class XMLAttributesFactory {

    private XMLAttributesFactory() {

    }


    public XMLAttributes createXMLAttributes(Attributes attributes) {
        XMLAttributes xmlAttributes = new XMLAttributes();
        int count = attributes.getLength();
        for (int i=0;i<count;++i) {
            String attributeName = attributes.getQName(i);
            String attributeValue = attributes.getValue(i);
            xmlAttributes.add(attributeName, attributeValue);
        }
        return xmlAttributes;
    }

    public static XMLAttributesFactory INSTANCE = new XMLAttributesFactory();
}
