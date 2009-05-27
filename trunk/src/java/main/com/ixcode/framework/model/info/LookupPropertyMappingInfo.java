/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

/**
 * Maps between 2 properties. Useful for lookups.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: LookupPropertyMappingInfo.java,v 1.2 2004/09/13 11:10:34 rdjbarri Exp $
 */
public class LookupPropertyMappingInfo {

    public LookupPropertyMappingInfo(String sourcePropertyName, String lookupPropertyName) {
        _sourcePropertyName = sourcePropertyName;
        _lookupPropertyName = lookupPropertyName;

    }

    public String getSourcePropertyName() {
        return _sourcePropertyName;
    }

    public String getLookupPropertyName() {
        return _lookupPropertyName;
    }


    private String _sourcePropertyName;
    private String _lookupPropertyName;    
}
