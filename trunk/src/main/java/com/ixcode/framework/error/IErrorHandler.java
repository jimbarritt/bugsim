/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.error;

/**
 * Implement this interface when you want to be notified of errors
 * (e.g. exceptions) that have occured during some process, rather than
 * being forced to catch them and deal with them in a try catch block.
 *
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: IErrorHandler.java,v 1.1 2004/08/27 11:13:30 rdjbarri Exp $
 */
public interface IErrorHandler {

    void onError(Throwable t);

}
