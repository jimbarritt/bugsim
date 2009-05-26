/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: BrowserType.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class BrowserType {

    public static final BrowserType IE = new BrowserType("shittinetExplorer");
    public static final BrowserType MOZILLA = new BrowserType("mozilla");
    public static final BrowserType UNKNOWN = new BrowserType("unknown");
    
    private BrowserType(String name) {
        _name = name;
    }

    private String _name;

}
