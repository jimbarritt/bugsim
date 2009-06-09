/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.ParameterReference;
import com.ixcode.framework.xml.sax.speedsax.ChildNodeBase;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.PropertyNodeMappingBuilder;
import org.xml.sax.Attributes;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ParameterReferenceNode extends ChildNodeBase {

    public static final String ELEMENT_NAME = "parameterReference";

    public static final String A_NAME = "name";
    public static final String A_FQ_NAME = "fullyQualifiedName";

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(ParameterReference.class);

    static {
//        ParameterReferenceNode.BUILDER.addAttributeMapping(ELEMENT_NAME, A_FQ_NAME, ParameterReference.P_FQ_NAME, String.class);
    }


    public ParameterReferenceNode(IParentNode parentNode) {
        super(ParameterReferenceNode.ELEMENT_NAME, ParameterReferenceNode.BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        String name= super.getAttributeValue(attributes, A_NAME);
        String fullyQualifiedName = super.getAttributeValue(attributes, A_FQ_NAME);
        return new ParameterReference(name, fullyQualifiedName);
    }


}
