/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import com.ixcode.framework.javabean.TextAlignment;

/**
 * A set of string resources that are associated with a property - expected to be internationalised.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: PropertyBundle.java,v 1.2 2004/08/17 17:08:03 rdjbarri Exp $
 */
public class PropertyBundle {

    public PropertyBundle(String label, String shortLabel) {
        _label = label;
        _shortLabel = shortLabel;
    }

    public PropertyBundle(String label, String shortLabel, int displaySize) {
        this(label, shortLabel, displaySize, TextAlignment.LEFT);
    }
    public PropertyBundle(String label, String shortLabel, int displaySize, TextAlignment textAlign) {

        _displaySize = displaySize;
        _label = label;
        _shortLabel = shortLabel;
        _textAlignment = textAlign;
    }

    public String getLabel() {
        return _label;
    }

    public String getShortLabel() {
        return _shortLabel;
    }

    /**
     * Note we could possibly implement a Short Display size for tables.
     * @return the default display size for this property - used when generically formatting a form.
     */
    public int getDisplayCharacterCount() {
        return _displaySize;
    }


    public void setDisplayCharacterCount(int displaySize) {
        _displaySize = displaySize;
    }


    public TextAlignment getTextAlignment() {
        return _textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        _textAlignment = textAlignment;
    }

    private int _displaySize = 20;

    private String _label;
    private String _shortLabel;


    private TextAlignment _textAlignment;
}
