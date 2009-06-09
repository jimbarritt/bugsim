package com.ixcode.framework.xml.sax.speedsax;

import com.ixcode.framework.xml.sax.speedsax.INode;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 16-Dec-2004
 * Time: 17:04:10
 * To change this template use File | Settings | File Templates.
 */
public class NodeRegistry {

    public static NodeRegistry getInstance() {
        return INSTANCE;
    }

    private NodeRegistry() {
    }

    public void registerNode(INode node) {
        _nodeMap.put(node.getElementName(), node);
    }

    public INode getNodeForElement(String qname)  {
        return (INode)_nodeMap.get(qname);
    }

    public void unregisterNode(INode nodeStub) {
        _nodeMap.remove(nodeStub.getElementName());
    }


    private static final NodeRegistry INSTANCE = new NodeRegistry();

    private Map _nodeMap = new HashMap();
}
