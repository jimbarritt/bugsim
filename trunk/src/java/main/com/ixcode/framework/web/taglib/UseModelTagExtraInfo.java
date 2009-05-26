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
 * @version $Revision: 1.2 $
 *          $Id: UseModelTagExtraInfo.java,v 1.2 2004/08/31 02:25:01 rdjbarri Exp $
 */
public class UseModelTagExtraInfo extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {
        return new VariableInfo[]{
            new VariableInfo(data.getAttributeString("alias"), "java.lang.Object", true, VariableInfo.AT_BEGIN)
        };
    }
}
