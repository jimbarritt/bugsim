/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.info.ModelBundle;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ModelLabelTag.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class ModelLabelTag extends ModelBundleTagBase {

    public ModelLabelTag() {
    }

    protected void writeTagContent(StringBuffer sb, ModelBinding binding, ModelBundle modelBundle) {
        sb.append(modelBundle.getLabel());
    }
}
