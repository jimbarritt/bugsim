/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.lookup;


import com.ixcode.framework.model.query.IPagedQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.5 $
 *          $Id: LookupContext.java,v 1.5 2004/09/10 13:33:05 rdjbarri Exp $
 */
public class LookupContext {

    public LookupContext() {
    }


    public void registerLookup(Lookup lookup) {
        if (log.isDebugEnabled()) {
            log.debug("<registerLookup> : registering lookup with id " + lookup.getId() + " model '" + lookup.getSourceModelXPath() + "' property '" + lookup.getPropertyName() + "'");
        }
        _lookups.put(new Integer(lookup.getId()), lookup);
    }

    public Lookup getLookup(int id) {
        Integer key = new Integer(id);
        if (!_lookups.containsKey(key)) {
            throw new IllegalStateException("Could not find lookup for key '" + key + "' in lookup context");
        }
        return (Lookup)_lookups.get(key);
    }

    public int getNextLookupId() {
        return ++_nextLookupId;
    }

    public boolean hasPagedQuery() {
        return _pagedQuery != null;
    }

    public IPagedQuery getPagedQuery() {
        return _pagedQuery;
    }

    public void setPagedQuery(IPagedQuery pagedQuery) {
        _pagedQuery = pagedQuery;
    }

    public void resetPagedQuery() {
        _pagedQuery = null;
    }

    private Map _lookups = new HashMap();
    private int _nextLookupId;
    private IPagedQuery _pagedQuery;
    private static Log log = LogFactory.getLog(LookupContext.class);

}
