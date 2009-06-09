package com.ixcode.framework.xml.sax.speedsax.example.filesystem;

import com.ixcode.framework.xml.sax.speedsax.IChildNode;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.ParentNodeBase;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.Description;
import org.xml.sax.Attributes;

import java.util.HashMap;

/**
 */
public class DescriptionNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "description";

    public DescriptionNode(IParentNode parentNode) {
        super(ELEMENT_NAME, new HashMap(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        return new Description();
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {

    }
}
