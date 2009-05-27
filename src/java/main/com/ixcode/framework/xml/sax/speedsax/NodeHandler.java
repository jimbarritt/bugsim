package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.BasicXMLFormatter;
import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 16-Dec-2004
 * Time: 09:36:06
 *
 * * @todo sort it out so that you dont have to put collections inside sub elements
 * To change this template use File | Settings | File Templates.
 */
public class NodeHandler extends DefaultHandler implements INodeManager {


    public NodeHandler(IParentNode rootNode) {
        this(rootNode, new BasicXMLFormatter());
    }
    public NodeHandler(IParentNode rootNode, IXMLFormatter formatter) {
        _currentNode = rootNode;
        _rootNode = rootNode;
        _formatter = formatter;
        _xpathAttributeName = "name";
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        _content = new StringBuffer();
        _xpathStack.push(qName);
        if (_xpathAttributeName != null) {
            _xpathAttributeStack.push(attributes.getValue(_xpathAttributeName));
        }
        if (log.isDebugEnabled()) {
            log.debug("startElement: " + XmlXPathHelper.INSTANCE.createXPathString(_xpathStack,_xpathAttributeStack, _xpathAttributeName,  3));
        }
        _currentNode.handleStartElement(this, qName, attributes);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
         _currentNode.handleEndElement(this, qName, _content);
        _xpathStack.pop();
        if (_xpathAttributeName != null) {
            _xpathAttributeStack.pop();
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        StringBuffer sb = new StringBuffer(length);
        sb.append(ch, start, length);

        _content.append(sb);
    }

    public void setCurrentNode(INode node) {
        _currentNode = node;
    }

    public INode getCurrentNode() {
        return _currentNode;
    }

    public void setCurrentParentNode(IParentNode parentNode) {
        _currentParentNode = parentNode;
    }

    public IParentNode getCurrentParentNode() {
        return _currentParentNode;
    }


    public IParentNode getRootNode() {
        return _rootNode;
    }

    public IXMLFormatter getXMLFormatter() {
        return _formatter;
    }

    private INode _currentNode;
    private StringBuffer _content;
    private IParentNode _rootNode;
    private IParentNode _currentParentNode;
    private static final Logger log = Logger.getLogger(NodeHandler.class);
    private Stack _xpathStack = new Stack();
    private IXMLFormatter _formatter;
    private String _xpathAttributeName;
    private Stack _xpathAttributeStack = new Stack();
}
