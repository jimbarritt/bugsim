/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.folding;

import java.util.ArrayList;
import java.util.List;

/**
 * Note that it is up to the caller to decide what id's to use - they
 * dont nescessarily have to be Model ids - it could be the id's of DIV's on a page for example
 * as long as they are unique.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: FoldingModel.java,v 1.2 2004/07/28 10:36:27 rdjbarri Exp $
 */
public class FoldingModel {

    public FoldingModel() {
    }

    public void setFolded(String id) {
        _unfolded.remove(id);
    }

    public void setUnfolded(String id) {
        if (!_unfolded.contains(id)) {
            _unfolded.add(id);
        }
    }


    public FoldingState getFoldingState(String modelId) {
        if (_unfolded.contains(modelId)) {
            return FoldingState.UNFOLDED;
        } else {
            return FoldingState.FOLDED;
        }
    }

    private List _unfolded = new ArrayList();
}
