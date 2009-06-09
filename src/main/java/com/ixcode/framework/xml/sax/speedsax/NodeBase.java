package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.format.IXMLFormatter;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

import java.util.Iterator;
import java.util.Map;

/**
 * Abstract base class for all nodes - so you dont have to implement everything and
 * manage the stuff like your element name.
 */
public abstract class NodeBase implements INode {


    protected NodeBase(String elementName, Map propertyMappings) {
        _elementName = elementName;
        _propertyMappings = propertyMappings;
    }

    public String getElementName() {
        return _elementName;
    }


    public void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes) {
        if (qName.equals(getElementName())) {
            //@todo It should also be possible to pass in the content to the constructor.
            // maybe by returning null from here ....
            _createdObject = createNewObject(attributes, nodeManager);
            if (_propertyMappings != null && _propertyMappings.containsKey(qName)) {
                populatePropertiesFromAttributes(qName, attributes, nodeManager.getXMLFormatter());
            }
        }


    }

    public void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        handlePropertyEndElement(nodeManager, qName, content);
    }

    protected void handlePropertyEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        if (_propertyMappings != null && _propertyMappings.containsKey(qName)) {
            populatePropertyFromContent(qName, content, nodeManager.getXMLFormatter());
        }
    }

    /**
     *
     * @return a value if you want to.
     * @param attributes
     * @param nodeManager
     */
    protected abstract Object createNewObject(Attributes attributes, INodeManager nodeManager);

    /**
     * Default is to use introspection to populate the property but you can override
     * it for a specific node if you like.
     */
    public void populatePropertyFromContent(String qName, StringBuffer content, IXMLFormatter formatter) {
        NodeMapping mapping = (NodeMapping)_propertyMappings.get(qName);
        if (mapping.hasPropertyMapping()) {
            String value = content.toString();
            mapping.getPropertyContentMapping().setPropertyValueAsString(_createdObject, value, formatter);
        }
    }

    /**
     * Note that we iterate the names from the mapping rather than from what is in the xml
     * this means that it is not mandatory to map all the attributes.
     *
     * a consequence of this is that also means that you might miss some important attributes but it wont fall over if new
     * ones appear in the xml.
     */
    public void populatePropertiesFromAttributes(String qName, Attributes attributes, IXMLFormatter formatter) {
        NodeMapping nodeMapping = (NodeMapping)_propertyMappings.get(qName);

        for (Iterator itr = nodeMapping.getAttributeMappings().iterator(); itr.hasNext();) {
            PropertyAttributeMapping mapping = (PropertyAttributeMapping)itr.next();
            mapping.populateProperty(_createdObject, qName, attributes, formatter);
        }

    }

//  Used to have a warning about unused attributes but not so relevant now - maybe include in nodeMapping somewhere....
//        if (log.isDebugEnabled()) {
//            if (nodeMapping.getAttributeNames().size() != attributes.getLength()) {
//                log.debug("[XML-PARSE-W1] There is a difference between the number of attributes for node '" + qName + "' (" + attributes.getLength() + ") and the number in the mapping '" + nodeMapping.getClass().getName() + "' (" + nodeMapping.getAttributeNames().size() + ")");
//            }
//        }


    protected Integer parseInteger(String stringValue) {
        return new Integer(Integer.parseInt(stringValue));
    }

    public Object getCreatedObject() {
        return _createdObject;
    }


    private String _elementName;
    private Object _createdObject;
    private Map _propertyMappings;
    private static final Logger log = Logger.getLogger(NodeBase.class);


}
