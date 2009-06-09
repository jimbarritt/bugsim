/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import com.ixcode.framework.model.query.IServerQueryParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.8 $
 *          $Id: LookupInfo.java,v 1.8 2004/09/17 10:58:06 rdjbarri Exp $
 */
public class LookupInfo {

    public LookupInfo() {
    }

    public List getDisplayPropertyNames() {
        return _displayPropertyNames;
    }

    public void setDisplayPropertyNames(List displayPropertyNames) {
        _displayPropertyNames = displayPropertyNames;
    }

    public void addDisplayPropertyName(String propertyName) {
        _displayPropertyNames.add(propertyName);
    }

    /**
     * @deprecated use getQueryClass instead. I did think that it should be a string to make it easier to persist but we already have getQueryParameters so that argument doesn really make sense.
     */
    public String getQueryClassName() {
        return _queryClass.getName();
    }

    /**
     * @deprecated use setQueryClass instead. I did think that it should be a string to make it easier to persist but we already have getQueryParameters so that argument doesn really make sense.
     * @param queryClassName
     */
    public void setQueryClassName(String queryClassName) {
        try {
            _queryClass = Thread.currentThread().getContextClassLoader().loadClass(queryClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setQueryClass(Class queryClass) {
        _queryClass = queryClass;
    }
    
    public Class getQueryClass() {
        return _queryClass;
    }

    public String toString() {
        return _displayPropertyNames.toString();
    }

    /**
     * Not all lookups will require the lookup assoication to be populated.
     */
    public boolean hasLookupAssociation() {
        return _lookupAssociationPropertyMapping != null;
    }

    public void setLookupAssociationName(String sourceAssociationName) {
        _lookupAssociationPropertyMapping = sourceAssociationName;
    }

    public String getLookupAssociationName() {
        return _lookupAssociationPropertyMapping;
    }

    public void addPropertyMappingInfo(String sourcePropertyName, String lookupPropertyName) {
        _propertyMappingInfos.add(new LookupPropertyMappingInfo(sourcePropertyName, lookupPropertyName));
    }

    public List getPropertyMappingInfos() {
        return _propertyMappingInfos;
    }


    /**
     * @deprecated prefer to pass static context via the lookup parameters on LoookupInfo - this will automatically be then passed to your server query by the LookupHandler. 
     * @return
     */
    public IServerQueryParameters getQueryParameters() {
        return _serverQueryParameters;
    }

    public void setQueryParameters(IServerQueryParameters serverQueryParameters) {
        _serverQueryParameters = serverQueryParameters;
    }

    public ILookupParameters getLookupParameters() {
        return _lookupParameters;
    }

    public void setLookupParameters(ILookupParameters lookupParameters) {
        _lookupParameters = lookupParameters;
    }

    private List _displayPropertyNames = new ArrayList();
    private String _queryClassName;    

    /** @deprecated */
    private IServerQueryParameters _serverQueryParameters;
    private List _propertyMappingInfos = new ArrayList();
    private Class _queryClass;
    private ILookupParameters _lookupParameters;

    private String _lookupAssociationPropertyMapping;
}
