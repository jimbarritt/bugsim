package com.ixcode.framework.experiment.model;

import com.ixcode.framework.math.IntegerMath;
import com.ixcode.framework.parameter.model.IParameterManipulation;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapFactory;
import com.ixcode.framework.util.MatrixBuilder;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ExperimentIterator {


    public ExperimentIterator(ExperimentPlan plan, MultipleProcessController multipleProcessController) {
        _experimentPlan = plan;
        _multipleProcessController = multipleProcessController;

        initialiseIterationConfig(_experimentPlan, _multipleProcessController);

    }


    public void nextIteration() {
        _currentParameters = _parameterFactory.createFromTemplate(_experimentPlan.getParameterTemplate());

        _currentConfigurationIndex++;
        _currentIteration=_configurationIndexes[_currentConfigurationIndex];

        configureParameters(_currentParameters);



    }


    public ParameterMap getCurrentParameters() {
        return _currentParameters;
    }

    public boolean hasNextIteration() {
        return (getConfigurationIndexCount() != 0) && (getCurrentConfigurationIndex() < getConfigurationIndexCount()-1);
    }

    public int getCurrentIteration() {
        return _currentIteration+1;
    }

    /**
     * We execute iterations in the order and number as stored in the index - maybe not executing all of them
     * @return
     */
    public int getCurrentConfigurationIndex() {
        return _currentConfigurationIndex;
    }

    public int getConfigurationIndexCount() {
            return _configurationIndexes.length;
        }

    public int getIterationCount() {
        return _iterationConfigurations.size();
    }



    private void initialiseIterationConfig(ExperimentPlan experimentPlan, MultipleProcessController multipleProcessController) {
        List parameterManipulations = experimentPlan.getParameterManipulationSequences();

        MatrixBuilder b = new MatrixBuilder();
        _iterationConfigurations = b.build(parameterManipulations);

        _configurationIndexes = multipleProcessController.getConfigurationIndexes(_iterationConfigurations.size());
        _configurationIndexSet = new HashSet();
        for (int i=0;i<_configurationIndexes.length;++i) {
            _configurationIndexSet.add(new Integer(_configurationIndexes[i]));
        }


        if (IntegerMath.maxOf(_configurationIndexes) > _iterationConfigurations.size()) {
            throw new IllegalStateException("Problem with interation indexes - not enough iterations!: " + _configurationIndexes);
        }
        if (log.isInfoEnabled()) {
            log.info("Going to run the following iterations: " + (IntegerMath.minOf(_configurationIndexes)+1) + " to " + (IntegerMath.maxOf(_configurationIndexes)+1));
        }
        _currentConfigurationIndex = -1;
        debugConfig();

    }

    public void debugConfig() {
        if (log.isInfoEnabled()) {



            for (int i=0;i<_configurationIndexes.length;++i) {
                List manips = (List)_iterationConfigurations.get(_configurationIndexes[i]);
                log.info("Iteration " + (_configurationIndexes[i]+1) + ": ");
                for (Iterator itrManip = manips.iterator(); itrManip.hasNext();) {
                    IParameterManipulation manipulation = (IParameterManipulation)itrManip.next();
                    log.info("    " + manipulation);
                }
            }
        }
    }


    private void configureParameters(ParameterMap currentParameters) {
        List currentConfig = (List)_iterationConfigurations.get(_currentIteration);


        for (Iterator itr = currentConfig.iterator(); itr.hasNext();) {
            IParameterManipulation manip = (IParameterManipulation)itr.next();
            manip.configure(currentParameters);
        }
        _parameterStructureChanged = hasParameterStructureChanged(_iterationConfigurations, _currentIteration, currentConfig);
    }

    /**
     * @param iterationConfigurations
     * @param currentIteration
     * @param currentConfig
     * @return
     * @todo Might be better to actually compare the structures although it will take longer.
     */
    private boolean hasParameterStructureChanged(List iterationConfigurations, int currentIteration, List currentConfig) {
        boolean hasChanged = false;
        if (currentIteration == 0) {
            hasChanged = true;
        } else {
            List lastIterationsConfig = (List)iterationConfigurations.get(currentIteration - 1);

            if (lastIterationsConfig.size() != currentConfig.size()) {
                hasChanged = true;
            } else {
                int i = 0;
                for (Iterator itr = lastIterationsConfig.iterator(); itr.hasNext();) {
                    IParameterManipulation lastManipulation = (IParameterManipulation)itr.next();
                    IParameterManipulation currentManipulation = (IParameterManipulation)currentConfig.get(i);
                    if (!lastManipulation.equals(currentManipulation)) {
                        hasChanged = currentManipulation.isParameterStructureChanged();
                        if (hasChanged) {
                            break;
                        }
                    }
                    ++i;
                }
            }
        }
        return hasChanged;

    }

    public boolean isParameterStructureChanged() {
//  return _parameterStructureChanged;
        return true; // force it to print a new file each iteration
    }

    public int[] getIterationIndexes() {
        return _configurationIndexes;
    }

    public boolean containsIterationIndex(int iteration) {
        return _configurationIndexSet.contains(new Integer(iteration));
    }


    private Set _configurationIndexSet;
    private ExperimentPlan _experimentPlan;

    private ParameterMapFactory _parameterFactory = new ParameterMapFactory();

    private List _iterationConfigurations;

    private int _currentIteration = 0;

    private ParameterMap _currentParameters;
    private boolean _parameterStructureChanged;

    private static final Logger log = Logger.getLogger(ExperimentIterator.class);
    private int[] _configurationIndexes;
    private int _currentConfigurationIndex =0;
    private MultipleProcessController _multipleProcessController;
}
 
