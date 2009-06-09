/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.javabean.format;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

/**
 * Provides the basic dat formatting in short date form.
 *
 *
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: SimpleListFormat.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class SimpleCollectionFormat implements IJavaBeanValueFormat {

    public SimpleCollectionFormat() {

    }



    public String format(Object value) {
        Collection c = (Collection)value;
        return "Collection[" + value.getClass().getName() + "] size=" + c.size(); 
    }

    public Object parse(String value) throws JavaBeanParseException {
        try {
            return DateFormat.getDateInstance(DateFormat.SHORT, _locale).parse(value);
        } catch (ParseException e) {
            throw new JavaBeanParseException("", "Could not parse date {0} ", value, e);
        }
    }


    private Locale _locale;
    private int _dateStyle;
}
