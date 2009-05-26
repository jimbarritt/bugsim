/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.experiment.model.ExperimentPlan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 8, 2007 @ 3:55:23 PM by jim
 */
public class ParameterManipulations {

    public ParameterManipulations(ExperimentPlan plan, ParameterMap parameterMap, List manipulations) {
        _parameterMap = parameterMap;
        _manipulationSequencesRaw = manipulations;
        _plan = plan;
        initManipulationSequences(_manipulationSequencesRaw);
    }

    private void initManipulationSequences(List sequences) {
        _manipulationSequenceObjects = new ArrayList();

        for (Iterator itr = sequences.iterator(); itr.hasNext();) {
            List list = (List)itr.next();
            ParameterManipulationSequence seq = new ParameterManipulationSequence(_parameterMap, list);
            _manipulationSequenceObjects.add(seq);
        }
    }

    public ParameterMap getParameterMap() {
        return _parameterMap;
    }

    public List getManipulationSequenceObjects() {
        return _manipulationSequenceObjects;
    }

    public void addManipulationSequence(ParameterManipulationSequence sequence) {
        _manipulationSequenceObjects.add(sequence);
        _manipulationSequencesRaw.add(sequence.getManipulations());
    }

    public void replaceManipulationSequence(ParameterManipulationSequence oldSequence, ParameterManipulationSequence newSequence) {
         int i = _manipulationSequenceObjects.indexOf(oldSequence);
        _manipulationSequenceObjects.remove(i);
        _manipulationSequencesRaw.remove(i);

        _manipulationSequenceObjects.add(i, newSequence);
        _manipulationSequencesRaw.add(i, newSequence.getManipulations());
    }

    public void addManipulationSequence(Parameter manipulatedP, Object value) {
        ParameterManipulation manip = new ParameterManipulation(manipulatedP, manipulatedP.getValue());
        List manips = new ArrayList();
        manips.add(manip);
        ParameterManipulationSequence sequence = new ParameterManipulationSequence(_parameterMap, manips);
        addManipulationSequence(sequence);
    }

    public void removeManipulationSequences(int[] indexes) {
        List toRemoveSequences = new ArrayList();
        List toRemoveManipulations = new ArrayList();
        for (int i=0;i<indexes.length;++i) {
            toRemoveSequences.add(_manipulationSequenceObjects.get(indexes[i]));
            toRemoveManipulations.add(_manipulationSequencesRaw.get(indexes[i]));
        }
        if (log.isInfoEnabled()) {
            log.info("Removing " + toRemoveSequences.size() + " manipulation Sequences");
        }
        _manipulationSequenceObjects.removeAll(toRemoveSequences);
        _manipulationSequencesRaw.removeAll(toRemoveManipulations);


    }

    public ParameterManipulationSequence getManipulationSequence(int iRow) {
        return (ParameterManipulationSequence)_manipulationSequenceObjects.get(iRow);
    }

    private static final Logger log = Logger.getLogger(ParameterManipulations.class);
    private ParameterMap _parameterMap;
    private List _manipulationSequenceObjects;
    private List _manipulationSequencesRaw;
    private ExperimentPlan _plan;
}
