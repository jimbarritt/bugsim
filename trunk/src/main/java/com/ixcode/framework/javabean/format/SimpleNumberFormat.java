/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: SimpleNumberFormat.java,v 1.1 2004/08/27 10:27:25 rdjbarri Exp $
 */
public abstract class SimpleNumberFormat implements IJavaBeanValueFormat {
    public String format(Object value) {
        return value.toString();
    }


}
