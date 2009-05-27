/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.selection;

import java.util.*;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: SelectionModel.java,v 1.2 2004/08/31 02:25:04 rdjbarri Exp $
 */
public class SelectionModel {

    public SelectionModel() {
    }

    public void addModelToSelection(String id, Object model) {
        if (!isModelSelected(id)) {
            _selection.put(id, model);
            _selectedModels.add(model);
        }
    }

    public void removeModelFromSelection(String id) {
        if (isModelSelected(id)) {
            Object removed = _selection.remove(id);
            _selectedModels.remove(removed);
        }
    }

    public boolean isModelSelected(String id) {
        return _selection.containsKey(id);
    }

    /**
     * By maintaining this we keep the order in which they were added in case it is important.
     */
    public List getSelectedModels() {
        return _selectedModels;
    }

    public List getSelectedModels(Class modelClass) {
        List selected = new ArrayList();
        for (Iterator itr = _selectedModels.iterator(); itr.hasNext();) {
            Object model = itr.next();
            if (modelClass.isAssignableFrom(model.getClass())) {
                selected.add(model);
            }                                     
        }
        return selected;
    }

    public void clearSelection() {
        _selection = new HashMap();
        _selectedModels = new ArrayList();
    }

    private Map _selection = new HashMap();
    private List _selectedModels = new ArrayList();

}
