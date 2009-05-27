/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

import com.ixcode.framework.model.IModelAdapter;

import java.io.IOException;
import java.util.*;

/**
 * This class is used when you want to page based on the number of lines but
 * your collection containsCoord the headers for a given set of objects.
 *
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: HeaderLinePagedQuery.java,v 1.4 2004/09/17 10:58:07 rdjbarri Exp $
 */
public class HeaderLinePagedQuery implements IPagedQuery {

    public HeaderLinePagedQuery(IServerQuery serverQuery, int serverPageSize, int displayPageSize, IModelAdapter modelAdapter, String linesAssociationName) {
        this(serverQuery, serverPageSize, displayPageSize, modelAdapter, linesAssociationName, NullServerQueryParameters.INSTANCE);
    }

    /**
     * Not that the display page size will be approximate because if you have a single header with many lines it will
     * not be split accross pages.
     */
    public HeaderLinePagedQuery(IServerQuery serverQuery, int serverPageSize, int displayPageSize, IModelAdapter modelAdapter, String linesAssciationName, IServerQueryParameters serverQueryParameters) {
        _serverQuery = serverQuery;
        _serverPageSize = serverPageSize;
        _displayPageSize = displayPageSize;
        _serverQueryParameters = serverQueryParameters;
        _modelAdapter = modelAdapter;
        _linesAssociationName = linesAssciationName;
        _currentServerPageNumber = _startDisplayPageNumber;
    }

    /**
     * Because we are never sure about the display page size until after we calculate it
     * we remember them in here so we can go back and roughly get the numbers right.
     * It wont work properly if we reach the beginning of the rowset, but in theory
     * if we think we ar eon page 3 and there is only 1 page left, we will get an EndOfResultsException
     */
    private int getDisplayPageCount(int serverPageNumber) {
        return ((Integer)_displayPageCountCache.get(new Integer(serverPageNumber))).intValue();
    }

    public int getDisplayPageCount() {
        return _displayPages.size();
    }

    public int getStartDisplayPageNumber() {
        return _startDisplayPageNumber;
    }

    /**
     * YOu can work this out because if you have say display pages
     *
     * 5   6   7   8
     *
     * the indexes will be
     *
     * 0   1   2   3
     *
     * and the start page wil be
     *
     * 5
     */
    public int getCurrentDisplayPageNumber() {
        return _startDisplayPageNumber + _currentDisplayPageIndex;
    }

    public void gotoDisplayPage(int pageNumber) {
        _currentDisplayPageIndex = getPageIndex(pageNumber);
    }

    private int getPageIndex(int pageNumber) {
        return ((Integer)_displayPageIndex.get(new Integer(pageNumber))).intValue();
    }

    public List getCurrentDisplayPage() {
        return (List)_displayPages.get(_currentDisplayPageIndex);
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
        for (int iPage = 0; iPage < pageCount; ++iPage) {
            Integer displayNumber = new Integer(pageNumber);
            _displayPageIndex.put(displayNumber, new Integer(iPage));
            _displayPageNumbers.add(displayNumber);
            pageNumber++;
        }
        _displayPageCountCache.put(new Integer(_currentServerPageNumber), new Integer(pageCount));
    }

    public void firstServerPage() {
        _displayPageCountCache = new HashMap();
        _currentServerPageNumber = 1;
        try {
            gotoServerPage(_currentServerPageNumber);
        } catch (EndOfResultsException e) {
            throw new QueryRuntimeException("EndOfResults trying to get the first page!",e );
        }
        _startDisplayPageNumber = 1;

        initialiseDisplayPages(_startDisplayPageNumber);
    }

    /**
     * This is where the real work happens - it iterates accros each of the objects in the header collection
     * until it has built up enough line objects to fill a page, then it adds the page to the pages collection.
     * Obviously some headers may contain more lines than a page in which case you will get all of them - it cannot
     * split lines accross pages.
     * <p/>
     * If the number of lines in a single header are greater than the display page size it will do a page break.
     */
    private void initialiseDisplayPages(int startDisplayPageNumber) {
        try {

            HeaderLineDisplayPageHelper pageHelper = new HeaderLineDisplayPageHelper(startDisplayPageNumber);

            for (Iterator itr = _currentServerPage.iterator(); itr.hasNext();) {
                Object header = itr.next();

                Collection lines = _modelAdapter.getAssociatedModels(header, _linesAssociationName);
                if (lines.size() > _displayPageSize && !pageHelper.isCurrentDisplayPageEmpty() && itr.hasNext()) {
                    pageHelper.newDisplayPage();
                }

                pageHelper.addHeader(header, lines.size());

                if ((pageHelper.getCurrentDisplayPageSize() >= _displayPageSize) && itr.hasNext()) {
                    pageHelper.newDisplayPage();
                }
            }
            _displayPages = pageHelper.getDisplayPages();
            _displayPageNumbers = pageHelper.getDisplayPageNumbers();
            initialiseDisplayPageIndex(_startDisplayPageNumber);
        } catch (IOException e) {
            throw new QueryRuntimeException("Problem with metadata during paging request", e);
        }
    }

    public void nextServerPage() throws EndOfResultsException {
        _previousDisplayPageCount = _displayPages.size();
        _currentServerPageNumber++;
        _startDisplayPageNumber = _startDisplayPageNumber + getDisplayPageCount();
        gotoServerPage(_currentServerPageNumber);
        initialiseDisplayPages(_startDisplayPageNumber);
        gotoDisplayPage(_startDisplayPageNumber);
    }

    /**
     * There is a slight problem here if the recordset changes whilst we are navigating.
     *
     * If rows are inserted / removed at the beginning of the rowset, the previous number of display pages
     * will be innaccurate.
     */
    public void previousServerPage() throws EndOfResultsException {
        _currentServerPageNumber--;
        _startDisplayPageNumber = _startDisplayPageNumber - getDisplayPageCount(_currentServerPageNumber);
        gotoServerPage(_currentServerPageNumber);
        initialiseDisplayPages(_startDisplayPageNumber);
    }

    private void gotoServerPage(int pageNumber) throws EndOfResultsException {
        int startIndex = (pageNumber - 1) * _serverPageSize;
        _currentServerPage = _serverQuery.executeQuery(startIndex, _serverPageSize, _serverQueryParameters);
    }



    public void lastServerPage() throws EndOfResultsException {
        if (!_serverQuery.supportsRowCount()) {
            throw new IllegalStateException("The server query " + _serverQuery + " does not support rowCount so we cannot go to the last page.");
        }
        int extraRows = _serverQuery.getRowCount() % _serverPageSize;
        int numberOfWholeServerPages = _serverQuery.getRowCount() / _serverPageSize;
        _currentServerPageNumber = (extraRows == 0) ? numberOfWholeServerPages : numberOfWholeServerPages + 1;

        gotoServerPage(_currentServerPageNumber);

        initialiseDisplayPages(_startDisplayPageNumber);

        gotoDisplayPage(_startDisplayPageNumber);

    }

    public int getTotalRowCount() {
        return _serverQuery.getRowCount();
    }

    /**
     * We cannot support a row count because we work out how many display pages there are dynamically which
     * means we dont know the total number of display pages even if we did know the row count.
     */
    public boolean supportsRowCount() {
        return false;
    }

    public boolean isForwardOnly() {
        return false;
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
        return (extraRows == 0) ? wholeDisplayPages : wholeDisplayPages + 1;
    }

    public int getDisplayPageSize() {
        return getCurrentDisplayPage().size();
    }

    public void setQueryParameters(IServerQueryParameters serverQueryParameters) {
        _serverQueryParameters = serverQueryParameters;
    }

    private IServerQuery _serverQuery;
    private int _serverPageSize;
    private int _displayPageSize;
    private IServerQueryParameters _serverQueryParameters;
    private List _currentServerPage;
    private IModelAdapter _modelAdapter;
    private String _linesAssociationName;
    private List _displayPages;
    private int _startDisplayPageNumber;
    private List _displayPageNumbers;
    private int _currentDisplayPageIndex;
    private int _currentServerPageNumber;
    private Map _displayPageIndex;
    private int _previousDisplayPageCount;
    private Map _displayPageCountCache = new HashMap();
}
