/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the basics tou just implement the bit that does the work!
 * 
 * @author Jim Barritt
 * @version $Revision: 1.10 $
 *          $Id: PagedQuery.java,v 1.10 2004/09/17 10:58:07 rdjbarri Exp $
 */
public class PagedQuery implements IPagedQuery {


    public PagedQuery(IServerQuery serverQuery, int serverPageSize, int displayPageSize) {
        _serverQuery = serverQuery;
        _serverQueryParameters = NullServerQueryParameters.INSTANCE;
        _serverPageSize = serverPageSize;
        _displayPageSize = displayPageSize;
    }
    public PagedQuery(IServerQuery serverQuery, int serverPageSize, int displayPageSize, IServerQueryParameters serverQueryParameters) {
        _serverQuery = serverQuery;
        _serverQueryParameters = serverQueryParameters;
        _serverPageSize = serverPageSize;
        _displayPageSize = displayPageSize;
    }


    /**
     * @return a list of ints which represent the page numbers that can currently be viewed given by the number of display pages per serverpage
     */
    public int getDisplayPageCount() {
        int extraRows = (_currentServerPage.size() == 0)? 0 : _currentServerPage.size() % _displayPageSize;
        int numberWholePages = (_currentServerPage.size() == 0)? 0 :_currentServerPage.size() / _displayPageSize;
        return  (extraRows ==0) ? numberWholePages : numberWholePages + 1;
    }

    public int getStartDisplayPageNumber() {
        return _startDisplayPageNumber;
    }

    public int getCurrentDisplayPageNumber() {
        return _currentDisplayPageNumber;
    }


    /**
     * Work it out based on row numbers starting from 1, but the array list needs indexes which start at 0.
     */
    public void gotoDisplayPage(int pageNumber) {
        if (_currentServerPage.size() == 0) {
            _currentDisplayPage = new ArrayList();
        } else {
            int pageIndex = getPageIndex(pageNumber);
            int startIndex = (pageIndex * _displayPageSize);
            int endIndex = (startIndex + _displayPageSize) > _currentServerPage.size() ? _currentServerPage.size() : startIndex + _displayPageSize;
            _currentDisplayPage = _currentServerPage.subList(startIndex, endIndex);
        }
        _currentDisplayPageNumber = pageNumber;
    }

    private int getPageIndex(int pageNumber) {
        return ((Integer)_displayPageIndex.get(new Integer(pageNumber))).intValue();
    }

    public boolean isForwardOnly() {
        return false;
    }

    public List getCurrentDisplayPage() {
        return _currentDisplayPage;
    }


    public void firstServerPage() {
        _currentServerPageNumber = 1;
        try {
            gotoServerPage(_currentServerPageNumber);
        } catch (EndOfResultsException e) {
            throw new QueryRuntimeException("EndOfResultsException thrown whilst trying to access the first server page!", e );
        }

        initialiseDisplayPageIndex(1);
        gotoDisplayPage(_startDisplayPageNumber);
    }


    public void nextServerPage() throws EndOfResultsException {
        _currentServerPageNumber++;
        int newDisplayStartNumber = _startDisplayPageNumber + getDisplayPageCount();
        gotoServerPage(_currentServerPageNumber);
        initialiseDisplayPageIndex(newDisplayStartNumber);
        gotoDisplayPage(_startDisplayPageNumber);
    }

    /**
     * @todo maybe some optimisations available here.
     */
    private void initialiseDisplayPageIndex(int newStartDisplayPageNumber) {
        _startDisplayPageNumber = newStartDisplayPageNumber;
        int pageCount = getDisplayPageCount();
        _displayPageIndex = new HashMap(pageCount);
        _displayPageNumbers = new ArrayList();
        int pageNumber = _startDisplayPageNumber;
        for (int iPage =0 ; iPage<pageCount;++iPage) {
            Integer displayNumber = new Integer(pageNumber);
            _displayPageIndex.put(displayNumber, new Integer(iPage));
            _displayPageNumbers.add(displayNumber);
            pageNumber++;
        }
    }

    public void previousServerPage() throws EndOfResultsException {
        _currentServerPageNumber--;

        if (_currentServerPageNumber < 1) {
            _currentServerPageNumber = 1;
        }
        gotoServerPage(_currentServerPageNumber);
        initialiseDisplayPageIndex(_startDisplayPageNumber - getDisplayPageCount());
        gotoDisplayPage(_startDisplayPageNumber);
    }

    /**
     * A bit more complex incase the number of rows dont make an exact set of whole pages,
     * e.g. if page size is 100 and there are 145 rows.
     * in this case we actually want to go to server page 2.
     *
     * The same applies for the
     */
    public void lastServerPage() throws EndOfResultsException {
        if (!_serverQuery.supportsRowCount()) {
            throw new QueryRuntimeException("The server query " + _serverQuery + " does not support rowCount so we cannot go to the last page.");
        }
        int extraRows = _serverQuery.getRowCount() % _serverPageSize;
        int numberOfWholeServerPages = _serverQuery.getRowCount() /  _serverPageSize;
        _currentServerPageNumber = (extraRows == 0) ? numberOfWholeServerPages : numberOfWholeServerPages + 1;

        gotoServerPage(_currentServerPageNumber);

        int extraDisplayRows = _serverQuery.getRowCount() % _displayPageSize;
        int numberOfWholeDisplayPages = _serverQuery.getRowCount() / _displayPageSize;
        int lastDisplayPageNumber = (extraDisplayRows == 0) ? numberOfWholeDisplayPages : numberOfWholeDisplayPages + 1;


        initialiseDisplayPageIndex((lastDisplayPageNumber - getDisplayPageCount()) + 1);

        gotoDisplayPage(_startDisplayPageNumber);
    }

    /**
     * @return the total number of rows for this query
     */
    public int getTotalRowCount() {
        return _serverQuery.getRowCount();
    }

    public boolean supportsRowCount() {
        return _serverQuery.supportsRowCount();
    }

    private void gotoServerPage(int pageNumber) throws EndOfResultsException {
        int startIndex = (pageNumber-1) * _serverPageSize;
        _currentServerPage = _serverQuery.executeQuery(startIndex, _serverPageSize, _serverQueryParameters);
    }

    public boolean hasPreviousServerPage() {
        return _currentServerPageNumber > 1;
    }

    public boolean hasNextServerPage() {
        return _serverQuery.isLastPage(_currentServerPageNumber, _serverPageSize);
    }

    public List getDisplayPageNumbers() {
        return _displayPageNumbers;
    }

    public int getDisplayPagesPerServerPage() {
        int extraRows = _serverPageSize % _displayPageSize;
        int wholeDisplayPages = _serverPageSize / _displayPageSize;
        return (extraRows == 0) ? wholeDisplayPages : wholeDisplayPages +1;
    }

    public int getDisplayPageSize() {
        return _displayPageSize;
    }


    public void setQueryParameters(IServerQueryParameters serverQueryParameters) {
        _serverQueryParameters = serverQueryParameters;
    }

    public IServerQueryParameters getQueryParameters() {
        return _serverQueryParameters;
    }

    private List _currentDisplayPage = new ArrayList();
    private int _currentDisplayPageNumber = -1;

    private List _currentServerPage = new ArrayList();
    private int _currentServerPageNumber = -1;

    private IServerQuery _serverQuery;
    private int _serverPageSize;
    private int _displayPageSize;
    private int _startDisplayPageNumber;
    private IServerQueryParameters _serverQueryParameters;
    private Map _displayPageIndex;
    private List _displayPageNumbers;
}
