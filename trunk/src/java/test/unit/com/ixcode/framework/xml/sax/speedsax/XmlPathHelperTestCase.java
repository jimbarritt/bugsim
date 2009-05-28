/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.xml.sax.speedsax;

import junit.framework.TestCase;

import java.util.Stack;

/**
 * TestCase for class : XmlPathHelper
 */
public class XmlPathHelperTestCase extends TestCase {

    public void testFormatStack() {
        Stack s = new Stack();
        s.push("level1");
        s.push("level2");
        s.push("level3");
        s.push("level4");
        s.push("level5");

        Stack a = new Stack();

        String actual = XmlXPathHelper.INSTANCE.createXPathString(s, a, "noattribute", 30);
        assertEquals("/level1/level2/level3/level4/level5", actual);

        actual = XmlXPathHelper.INSTANCE.createXPathString(s,a, "noattribute", 3);
        assertEquals("/level3/level4/level5", actual);

        actual = XmlXPathHelper.INSTANCE.createXPathString(s,a, "noattribute", 1);
        assertEquals("/level5", actual);

        actual = XmlXPathHelper.INSTANCE.createXPathString(s,a, "noattribute", 0);
        assertEquals("/", actual);
    }

}
