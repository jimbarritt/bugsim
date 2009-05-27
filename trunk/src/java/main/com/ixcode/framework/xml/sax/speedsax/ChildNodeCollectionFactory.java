package com.ixcode.framework.xml.sax.speedsax;

import java.util.HashMap;
import java.util.Map;

/**
 
 */
public class ChildNodeCollectionFactory implements  IChildNodeFactory {

    public ChildNodeCollectionFactory(String collectionElementName, String childElementName, Class childNodeClass) {
        this(collectionElementName, new String[]{childElementName}, new Class[]{childNodeClass});
    }
    public ChildNodeCollectionFactory(String collectionElementName, String[] childElementNames, Class[] childNodeClasses) {
        _childNodeClasses = childNodeClasses;
        _childElementNames = childElementNames;
        _collectionElementName = collectionElementName;
    }


    public INode createNode(IParentNode parentNode) {
        Map childNodeFactories = new HashMap();

        for (int i=0;i<_childElementNames.length;++i) {
            childNodeFactories.put(_childElementNames[i], new ChildNodeFactory(_childNodeClasses[i]));
        }
        return new CollectionNode(_collectionElementName, parentNode, childNodeFactories);
    }


    private String _collectionElementName;

    private String[] _childElementNames;
    private Class[] _childNodeClasses;

}
