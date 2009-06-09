/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;

import java.util.Map;
import java.util.Random;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class UniformAzimuthGenerator extends AzimuthGeneratorBase {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);
    }


    public UniformAzimuthGenerator(Random random) {
            super(random);
        }

        public UniformAzimuthGenerator() {
        }

    public CourseChange generateCourseChange(double currentDirection) {
        return Geometry.createCourseChange(currentDirection,  Geometry.generateUniformRandomAzimuthChange(super.getRandom()));
    }


}
