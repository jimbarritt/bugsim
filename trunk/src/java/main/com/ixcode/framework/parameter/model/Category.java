package com.ixcode.framework.parameter.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class Category implements IParameterModel{

    public boolean hasParent() {
        return getParentCategory() != null;
    }

    public IParameterModel getParent() {
        return getParentCategory();
    }


    public Category() {
    }

    public Category(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public IParameterModel findParentCalled(String name) {
        IParameterModel found = null;
        if (_name.equals(name)) {
            found = this;
        } else if (hasParent()) {
            found = getParent().findParentCalled(name) ;
        }
        return found;
    }


    public void setName(String name) {
        _name = name;
    }

    public List getParameters() {
        return _parameters;
    }

    public List getSubCategories() {
        return _subCategories;
    }

    public void addParameter(Parameter parameter) {
        _parameters.add(parameter);
        parameter.setParentCategory(this);
    }

    public void addSubCategory(Category subCategory) {
        _subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    public Category findSubCategory(String name) {
        Category found = null;
        for (Iterator itr = _subCategories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            if (category.getName().equals(name)) {
                found = category;
                break;
            }
        }
        return found;
    }

    public Parameter findParameter(String name) {
        Parameter found = null;
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (parameter.findParameter(name)!= null) {
                found = parameter;
                break;
            }
        }
        return found;
    }

    public String getFullyQualifiedName() {
        Category current = this;
        String fullName = current.getName();
        while (current.getParentCategory() != null) {
            current = current.getParentCategory();
            fullName = current.getName() + "." + fullName;
        }
        return fullName;
    }



    public Category getParentCategory() {
        return _parent;
    }

    /**
     * @todo probably need to do the opposit aswell although at this point we have no plans to be able to dynamically replace categoreis!@!
     * @param parent
     */
    public void setParent(Category parent) {
        _parent = parent;
        if (_parent.isConnectedToParameterMap()) {
            fireConnectedEvents(this);
        }
    }

    private void fireConnectedEvents(Category category) {
        for (Iterator itr = category.getParameters().iterator(); itr.hasNext();) {
                Parameter parameter = (Parameter)itr.next();
                parameter.fireParameterConnectedEvent(new Stack());
            }

        for (Iterator itr = category.getSubCategories().iterator(); itr.hasNext();) {
            Category subCategory= (Category)itr.next();
            fireConnectedEvents(subCategory);
        }
    }                                                 


    public void setRoot(ParameterMap parent) {
        _root = parent;
        fireConnectedEvents(this);
    }

    public List getAllParameters() {
        List allParams = new ArrayList();
        addParameters(allParams, _parameters);

        for (Iterator itr = _subCategories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            allParams.addAll(category.getAllParameters());
        }
        return allParams;
    }

    private void addParameters(List allParams, List currentParameters) {
        for (Iterator itr = currentParameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            allParams.add(parameter);

            if (parameter instanceof StrategyDefinitionParameter) {
                addAllAlgorithmParameters(allParams, (StrategyDefinitionParameter)parameter);
            } else if (parameter.containsStrategy()) {
                allParams.add(parameter.getValue());
                addAllAlgorithmParameters(allParams, (StrategyDefinitionParameter)parameter.getValue());
            }
        }
    }

    private void addAllAlgorithmParameters(List allParams, StrategyDefinitionParameter algorithmParameter) {

        addParameters(allParams, algorithmParameter.getParameters());
    }

    public String toString() {
        return _name;
    }

    /**
     * Recursively searches for an object
     * @param nameStack
     * @return
     */
    public Object findObject(Stack nameStack) {
        String currentLevelName = (String)nameStack.peek();
        Object found = null;
        if (currentLevelName.equals(_name)) {
            nameStack.pop();
            found = recurseCategories(nameStack);
            if (found == null) {
                found = recurseParameters(nameStack);
            }
        }
        return found;
    }

    private Object recurseParameters(Stack nameStack) {
        Object found = null;
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            found = parameter.findObject(nameStack);
            if (found != null) {
                break;
            }
        }
        return found;
    }

    private Object recurseCategories(Stack nameStack) {
        Object found = null;
        for (Iterator itr = _subCategories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            found = category.findObject(nameStack);
            if (found != null) {
                break;
            }
        }
        return found;
    }

    public Category findCategory(String name) {
        Category found = null;
        for (Iterator itr = _subCategories.iterator(); itr.hasNext();) {
            Category category = (Category)itr.next();
            if (category.getName().equals(name)) {
                found = category;
                break;
            } 
        }
        return found;
    }

    public ParameterMap getParameterMap() {
        ParameterMap parameterMap = null;
        if (_parent != null) {
            parameterMap = _parent.getParameterMap();
        } else if (_root != null) {
            parameterMap = _root;
        }
        return parameterMap;
    }

    public boolean isConnectedToParameterMap() {
        return getParameterMap() != null;
    }



    private String _name;
    private List _parameters = new ArrayList();
    private List _subCategories = new ArrayList();
    private Category _parent;
    private ParameterMap _root;
    public static final String P_NAME = "name";
}
 
