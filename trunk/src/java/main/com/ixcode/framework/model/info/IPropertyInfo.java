/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: IPropertyInfo.java,v 1.3 2004/09/17 13:36:00 rdjbarri Exp $
 */
public interface IPropertyInfo {
    boolean isLookup();

    LookupInfo getLookupInfo();

    String getFormatName();

    String getFormatPattern();

    boolean isMandatory(Object model);

    boolean isReadonly(Object model);

    ValueListInfo getValueListInfo();

    String getName();

    boolean hasValueListInfo();

    boolean isHidden(Object model);



    boolean isNumeric();

    String getStyle(Object model);

    String getStyleClasses();

    Class getHtmlWriter();
}
