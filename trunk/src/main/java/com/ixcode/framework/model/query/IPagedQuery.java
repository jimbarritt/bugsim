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
 * @version $Revision: 1.5 $
 *          $Id: IPagedQuery.java,v 1.5 2004/09/17 10:58:07 rdjbarri Exp $
 */
public interface IPagedQuery {
    int getDisplayPageCount();

    int getStartDisplayPageNumber();

    int getCurrentDisplayPageNumber();

    void gotoDisplayPage(int pageNumber);

    List getCurrentDisplayPage();

    void firstServerPage();

    void nextServerPage() throws EndOfResultsException;

    void previousServerPage() throws EndOfResultsException;

    void lastServerPage() throws  EndOfResultsException;

    int getTotalRowCount();

    boolean supportsRowCount();

    boolean isForwardOnly();

    boolean hasPreviousServerPage();

    boolean hasNextServerPage();

    List getDisplayPageNumbers();

    int getDisplayPagesPerServerPage();

    int getDisplayPageSize();

    void setQueryParameters(IServerQueryParameters serverQueryParameters);
}
