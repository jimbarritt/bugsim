/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.5 $
 *          $Id: ServerQueryStub.java,v 1.5 2004/09/17 10:58:07 rdjbarri Exp $
 */
public class ServerQueryStub implements IServerQuery {

    public ServerQueryStub(int size) {
        initialiseData(size);
    }

    /**
     * notice that we dont -1 from the size of the list for the end index.
     * This is because sublist works in what i think is a counter intuitive way.
     *
     * that is : if you ask for sublist(0, 9) you get items 0, 1, 2, 3, 4, 5, 6, 7, 8
     *
     * i.e. the toIndex parameter is not inclusive. so if you want all of them you make the toIndex the same as the size().
     */
    public List executeQuery(int startIndex, int pageSize, IServerQueryParameters parameters) {
        int endIndex = (startIndex + pageSize > _data.size()) ? _data.size() : startIndex + pageSize;
        return _data.subList(startIndex, endIndex);
    }

    public int getRowCount() {
        return _data.size();
    }

    public boolean isLastPage(int pageNumber, int serverPageSize) {
        int rows = getRowCount();
        int extraRows = (rows==0) ? 0 : rows % serverPageSize;
        int numberWholePages = (rows==0) ? 0 : rows / serverPageSize;
        int pages = (extraRows == 0) ? numberWholePages : numberWholePages + 1;

        return pageNumber < pages;
    }

    public boolean supportsRowCount() {
        return true;
    }

    /**
     * Override this to provide your own stub with data of your choosing.
     */
    protected void initialiseData(int size) {
        _data = new ArrayList();
        for (int i=0;i<size;++i) {
            _data.add(createDataItem(i));
        }
    }

    protected Object createDataItem(int index) {
        return new Integer(index+1);
    }

    protected List _data;


}
