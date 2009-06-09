/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

import java.util.List;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: IServerQuery.java,v 1.4 2004/09/17 10:58:07 rdjbarri Exp $
 */
public interface IServerQuery {


    List executeQuery(int startIndex, int pageSize, IServerQueryParameters parameters) throws EndOfResultsException;

    boolean supportsRowCount();

    int getRowCount();

    /**
     * This is here so that if you dont support row coutn you could do something else.
     */
    boolean isLastPage(int pageNumber, int serverPageSize);
}
