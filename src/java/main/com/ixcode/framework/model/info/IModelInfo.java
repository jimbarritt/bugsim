/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import java.util.List;
import java.util.Locale;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.10 $
 *          $Id: IModelInfo.java,v 1.10 2004/09/17 10:58:06 rdjbarri Exp $
 */
public interface IModelInfo {

    /**
     *
     * @param model
     * @return a string identifier for the "type" of the model which can then be used to get a model info
     */
    String getModelType(Object model);

    List getPropertyNames(String modelType);


    // -----------------------------------------------------------------------------------------------------------------
    // i18n / Descriptive Labels
    // -----------------------------------------------------------------------------------------------------------------

    ModelBundle getModelBundle(String modelType, Locale locale);

    /**
     *
     * @param modelType
     * @param propertyName
     * @param locale
     * @return CANNOT return null!! we expect a bundle to be returned everytime even if it is a default one.
     */
    PropertyBundle getPropertyBundle(String modelType, String propertyName, Locale locale);

    ValueListBundle getValueListBundle(Object model, String propertyName, Locale locale);


    boolean isPropertyReadOnly(Object model, String propertyName);

    boolean isPropertyMandatory(Object model, String propertyName);

    /**
     * @todo decide if this should be isVisible or isVisible - isVisible is more traditional ?     
     */
    boolean isPropertyHidden(Object model, String propertyName);

    /**
     * This will affect the styling of the property when it is rendered generically.
     */
    boolean isPropertyNumeric(Object model, String propertyName);

    /**
     * If you want to override the particular styling for a given property, implement this.
     * They will be put at the end.
     */
    String getPropertyStyleClasses(Object model, String propertyName);

    /**
     * Use this to return individual style settings - this might be to change the size of a text area box for example.     
     */
    String getPropertyStyle(Object model, String propertyName);

    boolean isPropertyLookup(Object model, String propertyName);

    LookupInfo getLookupInfo(Object model, String propertyName);

    boolean isPropertyValueList(Object model, String propertyName);

    ValueListInfo getValueListInfo(Object model, String propertyName);

    /**
     * Return a class which can be used to agent this property in html.
     * This allows you to choose based on whatever criteria you like which writer to use for
     * your property.
     *
     * The default is Textbox writer.
     */
    Class getPropertyHtmlWriter(Object model, String propertyName);


    /**  
     * @param modelType
     * @param propertyname
     * @return
     */
    Class getPropertyType(String modelType, String propertyname) ;
}
