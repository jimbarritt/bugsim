/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.population;

import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.AgentClassFilter;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.bugsim.agent.butterfly.ForagerAgentStrategies;
import com.ixcode.bugsim.agent.butterfly.ForagingAgentBehaviour;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

/**
 * Description : Creates individual foragers....
 * Created     : Mar 1, 2007 @ 2:39:34 PM by jim
 *
 *
 */
class ForagerFactory implements IForagerFactory{


    public ForagerFactory(String foragerClassName, ForagerAgentStrategies foragerStrategies, boolean recordHistory) {
        _foragerStrategies = foragerStrategies;
        _recordHistory = recordHistory;



        try {
            _agentClass = Thread.currentThread().getContextClassLoader().loadClass(foragerClassName);
            _foragerConstructor = _agentClass.getConstructor(CONSTRUCTOR_PARAM_TYPES);
            _agentFilter = new AgentClassFilter(_agentClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IMotileAgent createForager(RectangularCoordinate location, double azimuth, ForagingAgentBehaviour behaviour) {

        try {
            Object[] params = new Object[] {
                    location, new Double(azimuth), _foragerStrategies,  new Boolean(_recordHistory), behaviour
            };

            if (log.isDebugEnabled()) {
                log.debug("Created new Forager: " + IntrospectionUtils.getShortClassName(_agentClass) + " : " + location + " : " + azimuth + " : " + behaviour);
            }
            return (IMotileAgent)_foragerConstructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Class getAgentClass() {
        return _agentClass;
    }

    public ForagerAgentStrategies getForagerStrategies() {
        return _foragerStrategies;
    }

    public AgentClassFilter getAgentFilter() {
        return _agentFilter;
    }

    private static final Logger log = Logger.getLogger(ForagerFactory.class);
    private ForagerAgentStrategies _foragerStrategies;


    private boolean _recordHistory;
    private Class _agentClass;


    private static final Class[] CONSTRUCTOR_PARAM_TYPES = new Class[] {
            RectangularCoordinate.class,
            double.class,
            ForagerAgentStrategies.class,
            boolean.class,
            AgentBehaviour.class
    };
    private Constructor _foragerConstructor;

    private AgentClassFilter _agentFilter;
}
