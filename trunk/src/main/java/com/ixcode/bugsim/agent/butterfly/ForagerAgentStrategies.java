/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.ILarvalMortalityStrategy;

import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 2:47:40 PM by jim
 */
public class ForagerAgentStrategies {

    public ForagerAgentStrategies(IMovementStrategy movement, IAdultMortalityStrategy adultMortality, ILarvalMortalityStrategy larvalMortalityStrategy,
                                  IForagingStrategy foraging,
                                  IVisionStrategy vision, IOlfactionStrategy olfaction,
                                  List motivation) {

        _movementStrategy = movement;
        _adultMortalityStrategy = adultMortality;
        _foragingStrategy = foraging;
        _visionStrategy = vision;
        _olfactionStrategy = olfaction;
        _motivationStrategies = motivation;
        _larvalMortalityStrategy = larvalMortalityStrategy;
    }

    public IMovementStrategy getMovementStrategy() {
        return _movementStrategy;
    }

    public IAdultMortalityStrategy getMortalityStrategy() {
        return _adultMortalityStrategy;
    }

    public IForagingStrategy getForagingStrategy() {
        return _foragingStrategy;
    }

    public IVisionStrategy getVisionStrategy() {
        return _visionStrategy;
    }

    public IOlfactionStrategy getOlfactionStrategy() {
        return _olfactionStrategy;
    }

    public List getMotivationStrategies() {
        return _motivationStrategies;
    }

    public ILarvalMortalityStrategy getLarvalMortalityStrategy() {
        return _larvalMortalityStrategy;
    }

    private IMovementStrategy _movementStrategy;
    private IAdultMortalityStrategy _adultMortalityStrategy;
    private  ILarvalMortalityStrategy _larvalMortalityStrategy;
    private IForagingStrategy _foragingStrategy;
    public IVisionStrategy _visionStrategy;
    public IOlfactionStrategy _olfactionStrategy;
    public List _motivationStrategies;
}
