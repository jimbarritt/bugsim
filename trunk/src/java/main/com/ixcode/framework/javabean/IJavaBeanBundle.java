/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean;

import com.ixcode.framework.model.info.ModelBundle;
import com.ixcode.framework.model.info.PropertyBundle;
import com.ixcode.framework.model.info.ValueListBundle;

import java.util.Locale;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: IJavaBeanBundle.java,v 1.1 2004/08/11 12:08:26 rdjbarri Exp $
 */
public interface IJavaBeanBundle {

    ModelBundle getModelBundle(Locale locale);
    PropertyBundle getPropertyBundle(String propertyName, Locale locale);

    ValueListBundle getValueListBundle(String propertyName, Locale locale);

}
