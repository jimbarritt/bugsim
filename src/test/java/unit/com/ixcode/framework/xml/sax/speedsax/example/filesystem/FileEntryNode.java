package com.ixcode.framework.xml.sax.speedsax.example.filesystem;

import com.ixcode.framework.xml.sax.speedsax.ChildNodeBase;
import com.ixcode.framework.xml.sax.speedsax.INodeManager;
import com.ixcode.framework.xml.sax.speedsax.IParentNode;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.FileEntry;
import org.xml.sax.Attributes;

/**
 */
public class FileEntryNode extends ChildNodeBase {

    public static final String ELEMENT_NAME = "file";


    public FileEntryNode(IParentNode parentNode) {
        super(ELEMENT_NAME, parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        return new FileEntry();
    }

    public void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes) {
        super.handleStartElement(nodeManager, qName, attributes);
    }

    public void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        super.handleEndElement(nodeManager, qName, content);
    }


}
