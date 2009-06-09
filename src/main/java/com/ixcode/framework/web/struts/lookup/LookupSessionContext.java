/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.struts.lookup;

import com.ixcode.framework.model.lookup.Lookup;
import com.ixcode.framework.web.session.SessionContextBase;

/**
 * Provides context for the lookup page.
 *
 * this is where th apaged query and the current page are kept in between requests.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: LookupSessionContext.java,v 1.1 2004/09/13 11:10:33 rdjbarri Exp $
 */
public class LookupSessionContext extends SessionContextBase {

    public LookupSessionContext() {
    }



    private Lookup _lookup;
}
