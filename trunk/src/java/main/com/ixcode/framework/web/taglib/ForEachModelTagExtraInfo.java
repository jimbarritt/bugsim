/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ForEachModelTagExtraInfo.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class ForEachModelTagExtraInfo extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {
        String iteratorAlias = data.getAttributeString(ForEachModelTag.PROPERTY_ITERATOR_ALIAS);

        if (iteratorAlias == null) {
            return new VariableInfo[] {};
        }
        return new VariableInfo[]{
            new VariableInfo(iteratorAlias, "java.lang.Object", true, VariableInfo.NESTED)
        };
    }
}
