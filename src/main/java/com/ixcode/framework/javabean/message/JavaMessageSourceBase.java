/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.message;

import com.ixcode.framework.message.IMessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This message sourc elooks for messages that are hardcoded in java classes.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: JavaMessageSourceBase.java,v 1.1 2004/08/27 10:27:26 rdjbarri Exp $
 */
public abstract class JavaMessageSourceBase implements IMessageSource {

    public JavaMessageSourceBase(Locale defaultLocale) {
        _defaultLocale = defaultLocale;
    }

    public String getMessage(Locale locale, String key) {
        Map localisedMessages = null;
        if (_messages.containsKey(locale)) {
            localisedMessages = (Map)_messages.get(locale);
        } else if (_messages.containsKey(_defaultLocale)) {
            localisedMessages = (Map)_messages.get(_defaultLocale);
        }

        return (localisedMessages == null) ? null : (String)localisedMessages.get(key);
    }

    public void registerMessage(Locale locale, String key, String message) {
        if (!_messages.containsKey(locale)) {
            _messages.put(locale, new HashMap());
        }
        Map localisedMessages = (Map)_messages.get(locale);
        localisedMessages.put(key, message);
    }


    private Map _messages = new HashMap();
    private Locale _defaultLocale;
}
