/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.lookup;

import com.ixcode.framework.model.info.LookupInfo;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: Lookup.java,v 1.3 2004/08/06 13:35:17 rdjbarri Exp $
 */
public final class Lookup {

    /**
     *
     * @param id
     * @param sourceURL
     * @param sourceFormId  the actual HTML id of the form so it can be found later via JScript.
     * @param sourceFormContext this is the binding context of the form so you can reconstruct the model binding URL - it is possible to have multiple models in the request under seperate "formContext"'s
     * @param model
     * @param propertyName
     * @param lookupInfo
     */
    public Lookup(int id, String sourceFormId, String sourceFormContext,
                  Object model, String propertyName, LookupInfo lookupInfo, String sourceModelXPath) {
        _id = id;

        _sourceFormId = sourceFormId;
        _sourceFormContext = sourceFormContext;
        _sourceModelXPath =sourceModelXPath;
        _propertyName = propertyName;
        _sourceModel = model;
        _lookupInfo = lookupInfo;

    }

    public int getId() {
        return _id;
    }

    public Object getSourceModel() {
        return _sourceModel;
    }

    public String getPropertyName() {
        return _propertyName;
    }


    public LookupInfo getLookupInfo() {
        return _lookupInfo;
    }


    public String getSourceFormId() {
        return _sourceFormId;
    }


    public String getSourceFormContext() {
        return _sourceFormContext;
    }


    public String getSourceModelXPath() {
        return _sourceModelXPath;
    }

    private int _id;
    private String _sourceFormId;
    private String _sourceFormContext;
    private String _sourceModelXPath;
    private Object _sourceModel;
    private String _propertyName;
    private LookupInfo _lookupInfo;
}
