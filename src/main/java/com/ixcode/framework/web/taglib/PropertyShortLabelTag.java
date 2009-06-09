/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.info.PropertyBundle;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: PropertyShortLabelTag.java,v 1.2 2004/08/27 11:13:27 rdjbarri Exp $
 */
public class PropertyShortLabelTag extends PropertyBundleTagBase {

    public PropertyShortLabelTag() {
    }

    protected void writeTagContent(StringBuffer sb, PropertyBundle propertyBundle) {
        sb.append(propertyBundle.getShortLabel());
    }
}
