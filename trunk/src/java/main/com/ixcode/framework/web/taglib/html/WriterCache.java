/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import javax.servlet.jsp.JspException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: WriterCache.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public class WriterCache {

    public static WriterCache getInstance() {
        return SingletonHolder.theInstance;
    }

    private WriterCache() {
    }

    public IHtmlWriter getWriter(Class writerClass) throws JspException {
        if (!_cache.containsKey(writerClass.getName())) {
             IHtmlWriter writer = instantiateWriter(writerClass);
            _cache.put(writerClass.getName(), writer);
        }
        return (IHtmlWriter)_cache.get(writerClass.getName());
    }

    private IHtmlWriter instantiateWriter(Class writerClass) throws JspException {
        try {
            return (IHtmlWriter)writerClass.newInstance();
        } catch (InstantiationException e) {
            throw new JspException(e);
        } catch (IllegalAccessException e) {
            throw new JspException(e);
        }
    }

    private static class SingletonHolder {
        static final WriterCache theInstance = new WriterCache();
    }

    private Map _cache = new HashMap();
}
