/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

/**
 * Null implementation of the query parameter interface - hey there are no parameters to see here !!
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: NullServerQueryParameters.java,v 1.1 2004/09/17 10:58:07 rdjbarri Exp $
 */
public class NullServerQueryParameters implements IServerQueryParameters {

    public static final IServerQueryParameters INSTANCE = new NullServerQueryParameters();

    public NullServerQueryParameters() {
    }
}
