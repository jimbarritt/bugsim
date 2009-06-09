/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import javax.swing.*;


/**
 * A set of i18n'able resources associated with a given model type.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ModelBundle.java,v 1.1 2004/08/04 16:38:34 rdjbarri Exp $
 */
public class ModelBundle {

    public ModelBundle(String label) {
        _label = label;
    }

    public ModelBundle(String label, Icon icon) {
        _icon = icon;
        _label = label;
    }


    public Icon getIcon() {
        return _icon;
    }

    public String getLabel() {
        return _label;
    }

    private String _label;
    private Icon _icon;

}
