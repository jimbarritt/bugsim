/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.DerivedCalculationFactory;
import com.ixcode.framework.xml.sax.speedsax.ChildNodeBase;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.PropertyNodeMappingBuilder;
import org.xml.sax.Attributes;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DerivedCalculationNode extends ChildNodeBase {

  public static final String ELEMENT_NAME = "calculation";


    public static final String A_CLASS_NAME = "className";



    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(DerivedCalculationFactory.class);

    static {
        DerivedCalculationNode.BUILDER.addAttributeMapping(ELEMENT_NAME, A_CLASS_NAME);
    }


    public DerivedCalculationNode() {
        this(null);
    }

    public DerivedCalculationNode(IParentNode parentNode) {
        super(DerivedCalculationNode.ELEMENT_NAME, DerivedCalculationNode.BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _derivedCalculationFactory = new DerivedCalculationFactory();
        return _derivedCalculationFactory;
    }



    private DerivedCalculationFactory _derivedCalculationFactory;

}
