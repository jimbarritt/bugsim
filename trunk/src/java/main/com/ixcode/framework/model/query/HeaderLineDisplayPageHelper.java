/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that manages the state of the display pages for the HeaderLinePagedQuery.
 *
 * Not that we do not decide how to make the pages up in side here - that is the responsibility of
 * the HeaderLinePagedQuery object - we just keep track of the current page size etc.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: HeaderLineDisplayPageHelper.java,v 1.1 2004/08/30 11:29:43 rdjbarri Exp $
 */
class HeaderLineDisplayPageHelper {

    public HeaderLineDisplayPageHelper(int startDisplayPageNumber) {
        _initialDisplayPageNumber = startDisplayPageNumber;
        _currentDisplayPageNumber = startDisplayPageNumber;
        _displayPages.add(_currentDisplayPage);
    }

    public List getDisplayPageNumbers() {
        return _displayPageNumbers;
    }

    public List getDisplayPages() {
        return _displayPages;
    }

    public void addHeader(Object header, int lineCount) {
        _currentDisplayPage.add(header);
        _currentDisplayPageSize += lineCount;
    }

    public int getCurrentDisplayPageSize() {
        return _currentDisplayPageSize;
    }

    public void newDisplayPage() {
        _currentDisplayPage = new ArrayList();
        _displayPages.add(_currentDisplayPage);
        _currentDisplayPageSize = 0;
        _displayPageNumbers.add(new Integer(_currentDisplayPageNumber));
        _currentDisplayPageNumber++;
    }

    public boolean isCurrentDisplayPageEmpty() {
        return _currentDisplayPage.isEmpty();
    }

    public int getStartDisplayPageNumber() {
        return _initialDisplayPageNumber;
    }


    private List _displayPages = new ArrayList();
    private List _displayPageNumbers = new ArrayList();
    private int _initialDisplayPageNumber;
    private int _currentDisplayPageNumber;
    private List _currentDisplayPage = new ArrayList();
    private int _currentDisplayPageSize;
}
