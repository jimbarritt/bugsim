/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DerivedParameterNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "derivedParameter";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    public static final String A_NAME = "name";
    public static final String A_VALUE = "value";
    public static final String A_TYPE = "type";
    public static final String A_COMMENT = "comment";


    public static final String SOURCE_PARAMS_ELEMENT_NAME = "sourceParameters";

    static {
        CHILD_NODE_FACTORIES.put(DerivedCalculationNode.ELEMENT_NAME, new ChildNodeFactory(DerivedCalculationNode.class));
        //CHILD_NODE_FACTORIES.put(SOURCE_PARAMS_ELEMENT_NAME, new ChildNodeCollectionFactory(SOURCE_PARAMS_ELEMENT_NAME, new String[]{ParameterNode.ELEMENT_NAME, StrategyNode.ELEMENT_NAME, ELEMENT_NAME}, new Class[]{ParameterNode.class, StrategyNode.class, DerivedParameterNode.class}));
        CHILD_NODE_FACTORIES.put(SOURCE_PARAMS_ELEMENT_NAME, new ChildNodeCollectionFactory(SOURCE_PARAMS_ELEMENT_NAME, new String[]{ParameterReferenceNode.ELEMENT_NAME}, new Class[]{ParameterReferenceNode.class}));
    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(Parameter.class);

    static {
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_NAME, DerivedParameter.P_NAME, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_COMMENT, DerivedParameter.P_COMMENT, String.class);
    }


    public DerivedParameterNode() {
        this(null);
    }

    public DerivedParameterNode(IParentNode parentNode) {
        super(DerivedParameterNode.ELEMENT_NAME, DerivedParameterNode.CHILD_NODE_FACTORIES, DerivedParameterNode.BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _derivedParameter = new DerivedParameter();
        return _derivedParameter;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
//        if (childNode instanceof StrategyNode) {
//            _derivedParameter.addSourceParameter((Parameter)childNode.getCreatedObject());
//        } else if (childNode instanceof ParameterNode) {
//            _derivedParameter.addSourceParameter((Parameter)childNode.getCreatedObject());
//        } else if (childNode instanceof DerivedParameterNode) {
//            DerivedParameter parameter = (DerivedParameter)childNode.getCreatedObject();
//            _derivedParameter.addSourceParameter(parameter);
//        } else if (childNode instanceof StrategyDefinitionParameter) {
//            StrategyDefinitionParameter parameter = (StrategyDefinitionParameter)childNode.getCreatedObject();
//            _derivedParameter.addSourceParameter(parameter);
//        }
//
        if (childNode instanceof  ParameterReferenceNode) {
           ParameterReference reference = (ParameterReference)childNode.getCreatedObject();
            _sourceParameters.add(reference);
        } else if (childNode instanceof DerivedCalculationNode) {
            // @todo - see if we can't do this directly somehow....
            DerivedCalculationFactory factory = (DerivedCalculationFactory)childNode.getCreatedObject();
            IDerivedParameterCalculation calc = factory.createCalculation();
            if (calc == null) {
                throw new IllegalStateException("Calculation is null!!");
            }
            _derivedParameter.setCalculation(calc);
        }
    }

    public void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        super.handleEndElement(nodeManager, qName, content);
        _derivedParameter.setSourceParameters(_sourceParameters);
    }


    private DerivedParameter _derivedParameter;

    private List _sourceParameters = new ArrayList();

}
