/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryFactory {

    public BoundaryFactory() {

    }

    private static void registerClass(Class boundaryClass, BoundaryShape shape) {
        CLASS_MAP.put(shape.getName(), boundaryClass);
    }

    public static Class getBoundaryClassForShape(BoundaryShape shape) {
        if (!CLASS_MAP.containsKey(shape.getName())) {
            throw new IllegalArgumentException("No BOundary Class Registered for shape: " + shape);
        }
        return (Class)CLASS_MAP.get(shape.getName());
    }

    private static final Map CLASS_MAP = new HashMap();
    static {
         registerClass(LinearBoundary.class,  BoundaryShape.LINEAR);
        registerClass(CircularBoundary.class,  BoundaryShape.CIRCULAR);
        registerClass(RectangularBoundary.class,  BoundaryShape.RECTANGULAR);
    }
}
