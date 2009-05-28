package com.ixcode.framework.xml.sax.speedsax.example.filesystem;

/**
 */

import com.ixcode.framework.xml.sax.speedsax.NodeHandler;
import com.ixcode.framework.xml.sax.speedsax.NodeHandlerTestHelper;
import com.ixcode.framework.xml.sax.speedsax.example.filesystem.model.DirectoryEntry;
import junit.framework.TestCase;

public class DirectoryEntryNodeTestCase extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
        _directoryEntryNode = new DirectoryEntryNode();
        _nodeHandler = new NodeHandler(_directoryEntryNode);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParseSimpleCollection() throws Exception {
        NodeHandlerTestHelper.executeParse(this, "test_filesystem_simple_collection.xml", _nodeHandler);
        DirectoryEntry actual = (DirectoryEntry)_nodeHandler.getRootNode().getCreatedObject();

        assertNotNull("Root Directory", actual);

    }

    public void testParseComplex() throws Exception {
        NodeHandlerTestHelper.executeParse(this, "test_filesystem.xml", _nodeHandler);
        DirectoryEntry actual = (DirectoryEntry)_nodeHandler.getRootNode().getCreatedObject();

        assertNotNull("Root Directory", actual);

    }

    public void testParseRecursive() throws Exception {

        NodeHandlerTestHelper.executeParse(this, "test_filesystem_recursive.xml", _nodeHandler);
        DirectoryEntry actual = (DirectoryEntry)_nodeHandler.getRootNode().getCreatedObject();

        assertNotNull("Root Directory", actual);
    }

    DirectoryEntryNode _directoryEntryNode;
    private NodeHandler _nodeHandler;
}