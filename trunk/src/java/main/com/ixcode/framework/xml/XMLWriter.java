/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.xml;

import com.ixcode.framework.javabean.format.IJavaBeanValueFormat;
import com.ixcode.framework.javabean.format.JavaBeanFormatter;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Locale;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class XMLWriter {
    protected static void openTag(int indent, PrintWriter out, XMLAttributes attributes, String tagName) {
        openTag(indent, out, attributes, tagName, false, true);
    }

    protected static void openTag(int indent, PrintWriter out, XMLAttributes attributes, String tagName, boolean close, boolean newLine) {

        String padding = createIndent(indent);
        out.print(padding);
        out.print("<" + tagName);

        exportAttributes(out, attributes);

        if (close) {
            if (newLine) {
                out.println(" />");
            } else {
                out.print(" />");
            }
        } else {
            if (newLine) {
                out.println(">");
            } else {
                out.print(">");
            }
        }


    }

    protected static void closeTag(int indent, PrintWriter out, String tagName) {
        String padding = createIndent(indent);
        out.println(padding + "</" + tagName + ">");

    }

    protected static void openComment(int indent, PrintWriter out, String comment, boolean close) {
        String padding = createIndent(indent);
        out.print("<!--" + comment);
        if (close) {
            out.println("-->");
        }
    }

    private static void exportAttributes(PrintWriter out, XMLAttributes attributes) {
        for (Iterator itr = attributes.getAttributeNames().iterator(); itr.hasNext();) {
            String key = (String)itr.next();
            out.print(" " + key + "=\"" + formatValue(attributes.getValue(key)) + "\"");
            if (itr.hasNext()) {
                out.print(" ");
            }
        }
    }

    public static String formatValue(Object value) {
        if (value != null) {
            IJavaBeanValueFormat format = FORMATTER.getFormat(Locale.UK, value.getClass());
            return format.format(value);
        } else {
            return "NULL";
        }

    }

    private static String createIndent(int indent) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            sb.append("    ");
        }
        return sb.toString();
    }

    public static JavaBeanFormatter getFormatter() {
        return FORMATTER;
    }
    public static void setFormatter(JavaBeanFormatter formatter) {
        FORMATTER = formatter;
    }

    private static JavaBeanFormatter FORMATTER = new JavaBeanFormatter();
    protected static final XMLAttributes EMPTY_ATTRIBUTES = new XMLAttributes();
}
