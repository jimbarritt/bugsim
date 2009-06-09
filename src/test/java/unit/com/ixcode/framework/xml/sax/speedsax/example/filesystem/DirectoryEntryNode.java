package com.ixcode.framework.xml.sax.speedsax.example.filesystem;

import com.ixcode.framework.xml.sax.speedsax.*;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.DirectoryEntry;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.FileEntry;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.SecurityEntry;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class DirectoryEntryNode extends ParentNodeBase {

    public static final String ELEMENT_NAME = "directory";
    private static final String DIRECTORIES_ELEMENT_NAME = "directories";
    private static final String FILES_ELEMENT_NAME = "files";

    private static final Map CHILD_NODE_FACTORIES = new HashMap();

    static {
        CHILD_NODE_FACTORIES.put(FILES_ELEMENT_NAME, new ChildNodeCollectionFactory(FILES_ELEMENT_NAME, "file", FileEntryNode.class));
        CHILD_NODE_FACTORIES.put(DescriptionNode.ELEMENT_NAME, new ChildNodeFactory(DescriptionNode.class));
        CHILD_NODE_FACTORIES.put(DIRECTORIES_ELEMENT_NAME, new ChildNodeCollectionFactory(DIRECTORIES_ELEMENT_NAME, ELEMENT_NAME, DirectoryEntryNode.class));
        CHILD_NODE_FACTORIES.put(SecurityEntryNode.ELEMENT_NAME, new ChildNodeFactory(SecurityEntryNode.class));
    }

    private static final PropertyNodeMappingBuilder BUILDER = new PropertyNodeMappingBuilder(DirectoryEntry.class);

    static {
        BUILDER.addAttributeMapping(ELEMENT_NAME, "name");
    }


    public DirectoryEntryNode() {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), null);
    }

    public DirectoryEntryNode(IParentNode parentNode) {
        super(ELEMENT_NAME, CHILD_NODE_FACTORIES, BUILDER.getNodeMappings(), parentNode);
    }

    protected Object createNewObject(Attributes attributes, INodeManager nodeManager) {
        _directoryEntry = new DirectoryEntry();
        return _directoryEntry;
    }

    public void addChild(IChildNode childNode, INodeManager nodeManager) {
        if (childNode instanceof DescriptionNode) {
//            _directoryEntry.setDescription((Description)childNode.getCreatedObject());
        } else if (childNode instanceof FileEntryNode) {
            _directoryEntry.addFile((FileEntry)childNode.getCreatedObject());
        } else if (childNode instanceof DirectoryEntryNode) {
            _directoryEntry.addDirectory((DirectoryEntry)childNode.getCreatedObject());
        }else if(childNode instanceof SecurityEntryNode) {
            _directoryEntry.setSecurityEntry((SecurityEntry)childNode.getCreatedObject());
        } else if (childNode instanceof CollectionNode) {
            CollectionNode collectionNode = (CollectionNode)childNode;
            if (collectionNode.getElementName() == DIRECTORIES_ELEMENT_NAME) {
//                _directoryEntry.setDirectories((List)childNode.getCreatedObject());
            } else if (collectionNode.getElementName().equals(FILES_ELEMENT_NAME)) {
//                _directoryEntry.setFiles((List)childNode.getCreatedObject());
            }
        }
    }

    public void handleStartElement(INodeManager nodeManager, String qName, Attributes attributes) {
        super.handleStartElement(nodeManager, qName, attributes);
    }

    public void handleEndElement(INodeManager nodeManager, String qName, StringBuffer content) {
        super.handleEndElement(nodeManager, qName, content);
    }

    private DirectoryEntry _directoryEntry;

}
