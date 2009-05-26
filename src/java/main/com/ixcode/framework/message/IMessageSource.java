/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.message;

import java.util.Locale;

/**
 * Represents a source of messages. All it says is that you should pass
 * a key and a locale.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: IMessageSource.java,v 1.1 2004/08/04 16:38:33 rdjbarri Exp $
 */
public interface IMessageSource {

    String getMessage(Locale locale, String key);


}
