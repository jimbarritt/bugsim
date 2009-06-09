package com.ixcode.framework.xml.sax.speedsax;

import java.util.*;

/**
 */
class XmlXPathHelper {

    public static final XmlXPathHelper INSTANCE = new XmlXPathHelper();

    private XmlXPathHelper() {
    }


    /**
     *
     */
    public String createXPathString(Stack xpath, Stack xpathAttributes, String attributeName, int stackIndex) {
        List elementNames = extractElements(xpath, stackIndex);

        List attributes = (xpathAttributes.size() > 0) ? extractElements(xpathAttributes, stackIndex) : new ArrayList();

        return createXPathString(elementNames, attributes, attributeName);
    }

    private List extractElements(Stack xpath, int stackIndex) {
        List elementNames = new ArrayList();

        if (xpath.size() >0) {
            int endSearchIndex = xpath.size()-1;
            int startSearchIndex = (stackIndex<endSearchIndex) ? (endSearchIndex-stackIndex)+1 : 0;
            for (int i=endSearchIndex;i>=startSearchIndex;--i) {
                elementNames.add(xpath.get(i));
            }
        }
        return elementNames;
    }

    /**
     * @param elementNames - expects this to be last element first.
     * @return the xpath
     */
    public String createXPathString(List elementNames, List attributeValues, String attributeName) {
        StringBuffer sb = new StringBuffer();
        sb.append("/");
        int index = elementNames.size()-1;
        for (ListIterator itr = elementNames.listIterator(elementNames.size()); itr.hasPrevious();) {
            String s = (String)itr.previous();
            sb.append(s);
            if (attributeValues.size() >0 && attributeValues.get(index) != null) {
                sb.append("[@").append(attributeName).append("='");
                sb.append(attributeValues.get(index)).append("']");
            }
            if (itr.hasPrevious()) {
                sb.append("/");
            }
            index--;
        }
        return sb.toString();
    }

    public String createXPathString(Stack xpath) {
        StringBuffer sb = new StringBuffer();
        sb.append("/");
        for (Iterator itr = xpath.iterator(); itr.hasNext();) {
            String element = (String)itr.next();
            sb.append(element);
            if (itr.hasNext()) {
                sb.append("/");
            }
        }
        return sb.toString();
    }


}
