/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.parameter.model.Category;
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
public class CategoryNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "category";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    public static final String A_NAME = "name";

    private static final String SUBCATEGORIES_ELEMENT_NAME = "subCategories";

    static {
        CHILD_NODE_FACTORIES.put(SUBCATEGORIES_ELEMENT_NAME, new ChildNodeCollectionFactory(SUBCATEGORIES_ELEMENT_NAME, ELEMENT_NAME, CategoryNode.class));
        CHILD_NODE_FACTORIES.put(ParameterNode.ELEMENT_NAME, new ChildNodeFactory(ParameterNode.class));
        CHILD_NODE_FACTORIES.put(DerivedParameterNode.ELEMENT_NAME, new ChildNodeFactory(DerivedParameterNode.class));
        CHILD_NODE_FACTORIES.put(StrategyNode.ELEMENT_NAME, new ChildNodeFactory(StrategyNode.class));
    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(Category.class);

    static {
        BUILDER.addAttributeMapping(ELEMENT_NAME, A_NAME, Category.P_NAME, String.class);
    }


    public CategoryNode() {
        this(null);
    }

    public CategoryNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _category = new Category();
        return _category;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof CategoryNode) {
            Category category = (Category)childNode.getCreatedObject();
            _category.addSubCategory(category);
        }  else if (childNode instanceof ParameterNode) {
            Parameter parameter = (Parameter)childNode.getCreatedObject();
            _category.addParameter(parameter);
        } else if (childNode instanceof DerivedParameterNode) {
            DerivedParameter parameter = (DerivedParameter)childNode.getCreatedObject();
            _category.addParameter(parameter);
        } else if (childNode instanceof StrategyNode) {
            StrategyDefinitionParameter parameter = (StrategyDefinitionParameter)childNode.getCreatedObject();
            _category.addParameter(parameter);
        }
    }


    private Category _category;


}
