/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model;

/**
 * Thrown when something serious happens - should be a coding exception rather than something the user can
 * do or some state in which the application finds itself from a business process.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ModelAdapterRuntimeException.java,v 1.1 2004/09/15 11:05:33 rdjbarri Exp $
 */
public class ModelAdapterRuntimeException extends RuntimeException {

    public ModelAdapterRuntimeException(Throwable cause) {
        super(cause);
    }
    
    private static final String __SSRES__USER = "Not used";

    

    private static final String EXCEPTION_ID = "2000-8";
}
