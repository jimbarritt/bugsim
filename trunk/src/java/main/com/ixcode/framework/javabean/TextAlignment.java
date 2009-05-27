/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.javabean;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class TextAlignment {

    public static final TextAlignment LEFT = new TextAlignment("left") ;
    public static final TextAlignment RIGHT = new TextAlignment("right");
    private TextAlignment(String name) {
        _name = name;
    }


    public String getName() {
        return _name;
    }
    public String toString() {
        return _name;
    }
    private String _name;
}
