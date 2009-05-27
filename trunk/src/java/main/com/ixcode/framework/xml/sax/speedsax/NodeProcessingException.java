package com.ixcode.framework.xml.sax.speedsax;

import java.util.Stack;

/**
 */
public class NodeProcessingException extends RuntimeException {

    public NodeProcessingException() {
    }

    public NodeProcessingException(String message) {
        super(message);
    }


    public NodeProcessingException(Stack xpath, String message) {
        this (XmlXPathHelper.INSTANCE.createXPathString(xpath) + " : " + message);
    }

    public NodeProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
