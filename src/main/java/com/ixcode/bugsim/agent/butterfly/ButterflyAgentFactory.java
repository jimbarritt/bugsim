/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly;

import com.ixcode.framework.simulation.model.agent.IAgentFactory;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculator;
import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyAgentFactory implements IAgentFactory {


    public ButterflyAgentFactory() {


    }

    public IPhysicalAgent createAgent(Location location) {
        return new ButterflyAgent(location,  0,0, 5, new RandomWalkStrategy(new Random(), 20, 20),
                new EggLayingForagingStrategy(), new LimitedEggsAdultMortalityStrategy(), false, ForagingAgentBehaviour.RELEASE,
                new FieldOfViewVisualStrategy(90, 100), new SignalSensorOlfactoryStrategy(), new ArrayList());

    }

    private GravitationalCalculator _gravitationalCalculator = new GravitationalCalculator(new ExponentialDecaySignalFunction(2, 1, .004), 0.000001);;
}
