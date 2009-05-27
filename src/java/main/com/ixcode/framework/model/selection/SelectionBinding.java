/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.selection;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: SelectionBinding.java,v 1.1 2004/07/26 16:13:44 rdjbarri Exp $
 */
class SelectionBinding {

    public SelectionBinding(String modelId, boolean selected, Object model) {
        _isSelected = selected;
        _model = model;
        _modelId = modelId;
    }


    public void setSelected(boolean selected) {
        _isSelected = selected;
    }

    public boolean isSelected() {
        return _isSelected;
    }

    public Object getModel() {
        return _model;
    }

    public String getModelId() {
        return _modelId;
    }

    private String _modelId;
    private Object _model;
    private boolean _isSelected;
}
