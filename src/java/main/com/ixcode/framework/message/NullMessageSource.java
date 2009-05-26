/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.message;

import java.util.Locale;

/**
 * Does nothing but return the key.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: NullMessageSource.java,v 1.1 2004/08/30 11:29:45 rdjbarri Exp $
 */
public class NullMessageSource implements IMessageSource{

    public static final IMessageSource INSTANCE = new NullMessageSource();
    public String getMessage(Locale locale, String key) {
        return key;
    }


}
