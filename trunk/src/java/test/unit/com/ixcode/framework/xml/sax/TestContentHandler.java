package com.ixcode.framework.xml.sax;


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 15-Dec-2004
 * Time: 05:09:13
 * To change this template use File | Settings | File Templates.
 */
public class TestContentHandler extends DefaultHandler {

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.print("<" + localName + ">");
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println("</" + localName + ">");
    }

//    private static
//
//    private int _indent;

    private static final StringBuffer PADDING = new StringBuffer();

    static {
        for (int i=0;i<1000;++i) {
            PADDING.append(' ');
        }
    }

}
