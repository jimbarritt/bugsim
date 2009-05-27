/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.message;

import com.ixcode.framework.javabean.format.JavaBeanFormatMessageKeys;
import com.ixcode.framework.message.IMessageSource;

import java.util.Locale;

/**
 * Hard coded message source to provide us with some basic starter functionality.
 *
 * You can replace this with one that loads from a dictionary or resources if you like.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: JavaBeanMessageSource.java,v 1.2 2004/08/27 10:27:26 rdjbarri Exp $
 */
public class JavaBeanMessageSource extends JavaMessageSourceBase {

    public static final IMessageSource INSTANCE = new JavaBeanMessageSource();

    public JavaBeanMessageSource() {
        super(Locale.UK);
        registerMessages_enGB();
        registerMessages_zhCN();
    }


    private void registerMessages_enGB() {
        Locale locale = Locale.UK;

        super.registerMessage(locale, JavaBeanFormatMessageKeys.KEY_PARSE_FAILURE, JavaBeanMessages_enGB.TEXT_PARSE_FAILURE);
        super.registerMessage(locale, JavaBeanFormatMessageKeys.KEY_PARSE_NUMBER_FAILURE , JavaBeanMessages_enGB.TEXT_PARSE_NUMBER_FAILURE);

    }

    private void registerMessages_zhCN() {
        Locale locale = Locale.CHINA;
        super.registerMessage(locale, JavaBeanFormatMessageKeys.KEY_PARSE_FAILURE, JavaBeanMessages_zhCN.TEXT_PARSE_FAILURE);
        super.registerMessage(locale, JavaBeanFormatMessageKeys.KEY_PARSE_NUMBER_FAILURE , JavaBeanMessages_zhCN.TEXT_PARSE_NUMBER_FAILURE);
    }




}
