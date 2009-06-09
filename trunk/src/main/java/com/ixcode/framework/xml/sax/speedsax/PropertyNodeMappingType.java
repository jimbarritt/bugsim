package com.ixcode.framework.xml.sax.speedsax;

/**
 */
class PropertyNodeMappingType {

    public static final PropertyNodeMappingType ATTRIBUTE = new PropertyNodeMappingType("attribute");
    public static final PropertyNodeMappingType CONTENT = new PropertyNodeMappingType("content");
    
    private PropertyNodeMappingType(String name) {
        _name = name;
    }

    public String toString() {
        return _name;
    }


    private String _name;
}
