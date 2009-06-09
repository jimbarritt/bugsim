/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: PagedQueryNavigationPreviousLabelTag.java,v 1.1 2004/08/30 11:29:40 rdjbarri Exp $
 */
public class PagedQueryNavigationPreviousLabelTag extends PagedQueryNavigationLabelTagBase {

    protected void updateLabel(PagedQueryNavigationTag parent, String labelText) {
        parent.setPreviousLabel(labelText);
    }
}
