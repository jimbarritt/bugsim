package com.ixcode.framework.xml.sax.speedsax.example.filesystem;

import com.ixcode.framework.xml.sax.speedsax.IChildNode;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.ParentNodeBase;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.SecurityEntry;
import org.xml.sax.Attributes;

import java.util.HashMap;

/**
 */
public class SecurityEntryNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "security";

    public SecurityEntryNode(IParentNode parentNode) {
        super(ELEMENT_NAME, new HashMap(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        return new SecurityEntry();
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {

    }
}
