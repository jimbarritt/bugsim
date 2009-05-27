/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model;

import com.ixcode.framework.model.info.IModelInfo;
import com.ixcode.framework.model.validation.ValidationResult;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

/**
 * Implment this interface and put one into the request for us to use {@link com.ixcode.framework.web.request.RequestHandler }
 * 
 * @author Jim Barritt
  * @version $Revision: 1.16 $
 *          $Id: IModelAdapter.java,v 1.16 2004/09/17 10:58:08 rdjbarri Exp $
 */
public interface IModelAdapter extends IModelInfo {

    /**
     * The framework loops over all the model adapters it knows about until it finds one which can
     * deal with this model class.
     *
     * Note that we pass the <i>class</i> here incase you need to get a model adapter without an instance.
     *
     * @return if you can handle this model then return true
     */
    public boolean canAdapt(Class modelClass);



    /**
     *
     * @param model
     * @param propertyName
     * @param locale
     * @return
     * @throws java.io.IOException because it will be invoked inside the Jsp
     */
    String getPropertyValueAsString(Object model, String propertyName, Locale locale) throws IOException;

    /**    * 
     *
     * @param model
     * @param propertyName
     * @param value
     * @param locale
     * @throws IOException because it will be invoked from the contrller
     */
    ValidationResult setPropertyValueAsString(Object model, String propertyName, String value, Locale locale) throws IOException;

    /**
     * @return a collection of models (<b>To Many</b> association) from a root domain.
     */
    Collection getAssociatedModels(Object model, String associationName) throws IOException;

    /**
     * sets a collection of models (<b>To Many</b> association) from a root domain.
     * @param locale = to i18n the ValidationResult
     */
    ValidationResult setAssociatedModels(Object model, String associationName, Collection models, Locale locale) throws IOException, IOException;

    /**
     * @return a single object - this must be a <b>To One</b> association.
     */
    Object getAssociatedModel(Object model, String associationName) throws IOException;

    /**
     * sets a single object - this must be a <b>To One</b> association.
     * @param locale = required to make sure the validation result is localised.
     */
    ValidationResult setAssociatedModel(Object model, String associationName, Object associatedModel, Locale locale) throws IOException;

    Object getPropertyValue(Object model, String propertyName)throws IOException;

    /**
     * Used mostly by the lookup subsystem to populate values back into the source model.
     * @param locale - needed so that you can format the validation result back into the appropriate locale.
     */
    ValidationResult setPropertyValue(Object model, String propertyName, Object value, Locale locale) throws IOException;

    /**
     *
     * @param model
     * @return must return a NON NULL ValidationResult
     */
    ValidationResult validateModel(Object model);


    /**
     * The framework does not define how to get the id of a model so its up to you to provide this.
     * The JavaBeanModelAdapter allows you to specify the name of a property which it will then try to convert to a string
     * for you. The default is unsurprisingly "id".
     *
     * @param a string id that uniquley identifies this model instance. needed mainly for the selection of items so far.
     * @return
     */
    String getModelId(Object model);


    Class getPropertyType(Object model, String propertyName) ;

    
}
