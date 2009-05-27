/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.lookup;

import com.ixcode.framework.model.query.IServerQueryParameters;

/**
 * Standard parameters that get passed to all lookup server queries - basically containsCoord the lookup
 * whic should tell you most of the contextual information you need suych as the sourc eobject and the lookup info.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: LookupServerQueryParameters.java,v 1.1 2004/09/17 10:58:09 rdjbarri Exp $
 */
public class LookupServerQueryParameters implements IServerQueryParameters {

     public LookupServerQueryParameters(Lookup lookup) {
        _lookup = lookup;
    }

    public Lookup getLookup() {
        return _lookup;
    }

    private Lookup _lookup;
}
