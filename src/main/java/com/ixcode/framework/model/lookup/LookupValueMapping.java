/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.lookup;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: LookupValueMapping.java,v 1.1 2004/06/14 15:41:00 rdjbarri Exp $
 */
public class LookupValueMapping {

    public LookupValueMapping(String xpath, String value) {
        _value = value;
        _xpath = xpath;
    }

    public String getValue() {
        return _value;
    }

    public String getXpath() {
        return _xpath;
    }

    private String _xpath;
    private String _value;
}
