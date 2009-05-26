/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.DerivedParameter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.xml.sax.speedsax.*;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class StrategyNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "strategy";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    public static final String A_NAME = "name";
    public static final String A_COMMENT = "comment";


    private static final String STRATEGY_PARAMS_ELEMENT_NAME = "strategyParameters";

    static {
        CHILD_NODE_FACTORIES.put(STRATEGY_PARAMS_ELEMENT_NAME, new ChildNodeCollectionFactory(STRATEGY_PARAMS_ELEMENT_NAME, new String[]{ParameterNode.ELEMENT_NAME, ELEMENT_NAME, DerivedParameterNode.ELEMENT_NAME}, new Class[]{ParameterNode.class, StrategyNode.class, DerivedParameterNode.class}));
    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(StrategyDefinitionParameter.class);

    private static final String N_IMPLEMENTING_CLASS = "implementingClass";

    static {
        BUILDER.addAttributeMapping(ELEMENT_NAME, StrategyNode.A_NAME, StrategyDefinitionParameter.P_NAME, String.class);
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_COMMENT, StrategyDefinitionParameter.P_COMMENT, String.class);
        BUILDER.addNodeMapping(N_IMPLEMENTING_CLASS, StrategyDefinitionParameter.P_VALUE);
    }


    public StrategyNode() {
        this(null);
    }

    public StrategyNode(IParentNode parentNode) {
        super(StrategyNode.ELEMENT_NAME, StrategyNode.CHILD_NODE_FACTORIES, StrategyNode.BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _strategy = new StrategyDefinitionParameter();
        return _strategy;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof StrategyNode) {
            _strategy.addParameter((Parameter)childNode.getCreatedObject());
        }if (childNode instanceof ParameterNode) {
            _strategy.addParameter((Parameter)childNode.getCreatedObject());
        } else if (childNode instanceof DerivedParameterNode) {
            DerivedParameter parameter = (DerivedParameter)childNode.getCreatedObject();
            _strategy.addParameter(parameter);
        } else if (childNode instanceof StrategyDefinitionParameter) {
            StrategyDefinitionParameter parameter = (StrategyDefinitionParameter)childNode.getCreatedObject();
            _strategy.addParameter(parameter);
        }

    }


    

    private StrategyDefinitionParameter _strategy;

}
