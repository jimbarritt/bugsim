/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignal;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.OlfactorySignal;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 */
public interface IAzimuthGenerator {

    CourseChange generateCourseChange(double currentDirection);


    /**
     * in here we might want to do some stuff to change the parameters of the Azimuth and  Move Length generators
     * to either move further / less or be more random / less depending on signal strengths.
     * Really need to make a copy of the azimuth generator with new parameters....
     */
    IAzimuthGenerator modifyAzimuthGenerator(VisualSignal visualSignal, double calculatedAzimuth);

    IAzimuthGenerator modifyAzimuthGenerator(OlfactorySignal olfactorySignal, double calculatedAzimuth);

}
