/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.datatype;

import java.util.*;

/**
 * Description : implements the type safe enumerator pattern (inser ref!)
 * 
 */
public abstract class TypeSafeEnum {


    protected TypeSafeEnum(String name) {
        _name = name;
        registerEnum(this);
    }

    public String getName() {
        return _name;
    }

    public String toString() {
        return _name;
    }

    public List getEnumValues() {
        return (List)ENUMERATIONS.get(this.getClass().getName());
    }

    private static void registerEnum(TypeSafeEnum enumeration) {
        if (!ENUMERATIONS.containsKey(enumeration.getClass().getName())) {
             ENUMERATIONS.put(enumeration.getClass().getName(), new ArrayList());
        }
        List values  = (List)ENUMERATIONS.get(enumeration.getClass().getName());
        values.add(enumeration);

    }

    public static TypeSafeEnum resolve(Class enumClass, String name) {
        List values = (List)ENUMERATIONS.get(enumClass.getName());
        TypeSafeEnum enumRet = null;
        for (Iterator itr = values.iterator(); (itr.hasNext() && enumRet==null);) {
            TypeSafeEnum enumeration= (TypeSafeEnum)itr.next();
            if (enumeration.getName().equals(name)) {
                enumRet = enumeration;
            }
        }
        return enumRet;
    }
    

    private String _name;

    private static final Map ENUMERATIONS = new HashMap();

}
