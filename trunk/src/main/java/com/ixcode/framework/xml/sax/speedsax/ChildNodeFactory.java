package com.ixcode.framework.xml.sax.speedsax;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 */
public class ChildNodeFactory implements IChildNodeFactory {  

    public ChildNodeFactory(Class childNodeClass) {
        _childNodeClass = childNodeClass;

        try {
            _constructor = _childNodeClass.getConstructor(new Class[] {IParentNode.class});
        } catch (NoSuchMethodException e) {
            throw new NodeProcessingException("Could not find a constructor for " + _childNodeClass.getName() + " which takes an IParentNode", e);
        }
    }

    public INode createNode(IParentNode parentNode) {
        try {
            return (INode)_constructor.newInstance(new Object[] {parentNode});
        } catch (InstantiationException e) {
            throw new NodeProcessingException("Could not instantiate node " + _childNodeClass.getName() + " " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new NodeProcessingException("Could not instantiate node (Security Violation) " + _childNodeClass.getName() + " " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new NodeProcessingException("Could not instantiate node" + _childNodeClass.getName() + " : " + e.getTargetException().getMessage(), e);
        }
    }

    protected Class getChildNodeClass() {
        return _childNodeClass;
    }

    private Class _childNodeClass;
    private Constructor _constructor;
}
