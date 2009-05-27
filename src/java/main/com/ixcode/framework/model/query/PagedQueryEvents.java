/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: PagedQueryEvents.java,v 1.1 2004/07/27 15:03:34 rdjbarri Exp $
 */
public class PagedQueryEvents {

    public static final PagedQueryEvents NEXT_SERVER_PAGE = new PagedQueryEvents("next-server-page");
    public static final PagedQueryEvents PREVIOUS_SERVER_PAGE = new PagedQueryEvents("previous-server-page");
    public static final PagedQueryEvents GOTO_DISPLAY_PAGE = new PagedQueryEvents("goto-display-page");
    public static final PagedQueryEvents FIRST_SERVER_PAGE = new PagedQueryEvents("first-server-page");
    public static final PagedQueryEvents LAST_SERVER_PAGE = new PagedQueryEvents("last-server-page");

    private PagedQueryEvents(String name) {
        _name = name;
    }

    public String toString() {
        return _name;
    }

    public String getName() {
        return _name;
    }

    public static PagedQueryEvents parseString(String in) {
        if (in.equals(NEXT_SERVER_PAGE.getName())) {
            return NEXT_SERVER_PAGE;
        } else if (in.equals(FIRST_SERVER_PAGE.getName())) {
            return FIRST_SERVER_PAGE;
        } else if (in.equals(PREVIOUS_SERVER_PAGE.getName())) {
            return PREVIOUS_SERVER_PAGE;
        } else if (in.equals(LAST_SERVER_PAGE.getName())) {
            return LAST_SERVER_PAGE;
        } else if (in.equals(GOTO_DISPLAY_PAGE.getName())) {
            return GOTO_DISPLAY_PAGE;
        } else {
            throw new IllegalStateException("Could not parse string '" + in + "' into a know PagedQueryEvents");
        }

    }

    private String _name;


}
