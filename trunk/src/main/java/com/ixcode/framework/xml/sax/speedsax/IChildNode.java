package com.ixcode.framework.xml.sax.speedsax;

/**
 */
public interface IChildNode extends INode {

    IParentNode getParentNode();

    boolean hasParentNode();
}
