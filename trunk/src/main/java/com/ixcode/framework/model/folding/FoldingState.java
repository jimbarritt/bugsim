/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.folding;

/**
 * Typesafe enumeration so that the values in the request make sense rather than just being "true" or "false"
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: FoldingState.java,v 1.1 2004/07/28 10:36:27 rdjbarri Exp $
 */
public class FoldingState {

    public static final FoldingState FOLDED = new FoldingState("folded");
    public static final FoldingState UNFOLDED = new FoldingState("unfolded");


    public static FoldingState parse(String s) {
        if (s.equals(FOLDED.getName())) {
            return FOLDED;
        } else if (s.equals(UNFOLDED.getName())) {
            return UNFOLDED;
        } else {
            throw new IllegalStateException("Could not parse the value '" + s + "' into a valid FoldingState Enumerated value.");
        }
    }

    private FoldingState(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public String toString() {
        return _name;
    }

    private String _name;



}
