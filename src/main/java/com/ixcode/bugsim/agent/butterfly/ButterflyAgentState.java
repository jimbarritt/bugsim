/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.butterfly;

import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.bugsim.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyAgentState {


    public ButterflyAgentState(long id, long age, Location location, ForagingAgentBehaviour behaviour, double direction, long numberOfEggs, CabbageAgent currentCabbage, Move lastMove) {
        _behaviour = behaviour;
        _heading = direction;
        _eggCount = numberOfEggs;
        _butterflyId = id;
        _location = location;
        _age = age;
        _currentCabbage = currentCabbage;
        _lastMove = lastMove;

    }

    public ForagingAgentBehaviour getBehaviour() {
        return _behaviour;
    }

    public double getHeading() {
        return _heading;
    }

    public long getEggCount() {
        return _eggCount;
    }

    public long getButterflyId() {
        return _butterflyId;
    }

    public Location getLocation() {
        return _location;
    }

    public long getAge() {
        return _age;
    }

    public Move getLastMove() {
        return _lastMove;
    }

    public CabbageAgent getCurrentCabbage() {
        return _currentCabbage;
    }

    public String toString() {
        return "age=" + _age + ", behaviour=" + _behaviour + ", heading=" + _heading + ", angleOfChange=" + _angleOfChange;
    }
    private ForagingAgentBehaviour _behaviour;
    private double _heading;
    private long _eggCount;
    private long _butterflyId;
    private Location _location;
    private long _age;
    private CabbageAgent _currentCabbage;
    private double _angleOfChange;
    private Move _lastMove;
}
