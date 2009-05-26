/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : Allows you to run multiple experiments, varying the parameters as you go.
 * @deprecated
 */
public abstract class ExperimentLoopBase implements IExperimentLoop{


    protected ExperimentLoopBase(String id, String name, long numberOfRuns) {
        _numberOfRuns = numberOfRuns;
        _id = id;
        _name = name;

    }

    public void initialiseExperimentProperties(long loopIndex, IExperiment experiment) {
        ExperimentProperties properties = experiment.getProperties();
        if (loopIndex == 0) {
            System.out.println("LoopIndex : " + loopIndex + " initialising loop");
            for (Iterator itr = _propertyChangers.iterator(); itr.hasNext();) {
                ILoopPropertyValueIterator propertyValueIterator = (ILoopPropertyValueIterator)itr.next();
//                properties.setPropertyValue(propertyValueIterator.getPropertyName(), propertyValueIterator.getStartValue());
//                System.out.println("Setting property " + propertyValueIterator.getPropertyName() + " to " + propertyValueIterator.getStartValue());
            }
        } else {
            System.out.println("Incrementing properties loop index : " + loopIndex);
             for (Iterator itr = _propertyChangers.iterator(); itr.hasNext();) {
                ILoopPropertyValueIterator propertyValueIterator = (ILoopPropertyValueIterator)itr.next();
                Object current = properties.getPropertyValue(propertyValueIterator.getPropertyName());
                properties.setPropertyValue(propertyValueIterator.getPropertyName(), propertyValueIterator.next(current));
            }
        }
    }




    /**
     * If ALL the loops have ended then we finish.
     * @param loopIndex
     * @param experiment
     * @return
     */
    public boolean nextIteration(long loopIndex, IExperiment experiment) {
        boolean end = isLoopComplete(loopIndex, experiment);

        fireNextLoopIterationEvent(loopIndex, experiment);

        return end;
    }

    public boolean isLoopComplete(long currentLoopIndex, IExperiment experiment) {
        boolean end = true;
        ExperimentProperties properties = experiment.getProperties();

        for (Iterator itr = _propertyChangers.iterator(); itr.hasNext();) {
            ILoopPropertyValueIterator iterator = (ILoopPropertyValueIterator)itr.next();
            Object value = properties.getPropertyValue(iterator.getPropertyName());
            if (iterator.hasNext(value)) {
                end = false;
                break;
            }
        }
        return end;
    }


    public String getName() {
        return _name;
    }

    public String getId() {
        return _id;
    }

    public void addPropertyChanger(String propertyName, Object startValue, Object maxValue, Object increment) {
        ILoopPropertyValueIterator valueIterator = createChanger(propertyName, startValue, maxValue, increment);
        _propertyChangers.add(valueIterator);
    }

    private ILoopPropertyValueIterator createChanger(String propertyName, Object startValue, Object maxValue, Object increment) {
        Class changerClass = LoopPropertyChangerRegistry.getInstance().getChangerClass(startValue.getClass());
        ILoopPropertyValueIterator iterator;
        try {
            Constructor c =changerClass.getConstructor(new Class[] {String.class,  Object.class,  Object.class,  Object.class});
            iterator = (ILoopPropertyValueIterator)c.newInstance(new Object[] {propertyName, startValue, maxValue, increment});

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        return iterator;
    }

    public void addSubLoop(IExperimentLoop subLoop) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void addLoopListener(IExperimentLoopListener loopListener) {
        if (!_listeners.contains(loopListener)) {
            _listeners.add(loopListener);
        }
    }

    public void removeLoopListener(IExperimentLoopListener loopListener) {
        _listeners.remove(loopListener);
    }

    private void fireNextLoopIterationEvent(long loopindex, IExperiment experiment) {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentLoopListener l = (IExperimentLoopListener)itr.next();
            l.nextIteration(loopindex, this, experiment);
        }
    }

    private List _listeners = new ArrayList();
    private List _propertyChangers = new ArrayList();
    private List _subLoops = new ArrayList();
    private long _numberOfRuns;
    private String _id;
    private String _name;
}
