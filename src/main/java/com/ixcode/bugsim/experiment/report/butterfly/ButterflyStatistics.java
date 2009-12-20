/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.butterfly;

import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;
import com.ixcode.framework.math.stats.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyStatistics {

    public ButterflyStatistics(int populationSize) {
        _ddSQ = new double[populationSize];
        _iButterfly = 0;

    }
    public void addButterfly(ButterflyAgent butterflyAgent) {

        _ddSQ[_iButterfly] = Math.pow(butterflyAgent.calculateDisplacementDistance(), 2);
        _iButterfly++;
    }

    public void calculate() {
        _stats = new ArrayList();

        for (int i=0;i<_ddSQ.length; ++i) {
            DescriptiveStatistics s = new DescriptiveStatistics(_ddSQ);
            _stats.add(s);
        }

        if (_stats.size() >0) { // If you have turned off recording historical agents there wont be any to report
            _lastStats = (DescriptiveStatistics)_stats.get(_stats.size()-1);
        }
    }

    public DescriptiveStatistics getDescriptiveStats() {
        return _lastStats;
    }

    public List getStatsByAge() {
        return _stats;
    }


    private double[] _ddSQ; // Dispersal distance squared - (dispersalDistance)^2
    private int _iButterfly;

    private DescriptiveStatistics _lastStats;
    public static final String DISPSQ_N = "dispsq.N";
    public static final String DISPSQ_MEAN = "dispsq.MEAN";
    public static final String DISPSQ_VARIANCE = "dispsq.VARIANCE";
    public static final String DISPSQ_STD_ERROR = "dispq.STD_ERR";
    public static final String DISPSQ_MEAN_MINUS_ERR = "dispsq.MEAN_MINUS_ERR";
    public static final String DISPSQ_MEAN_PLUS_ERR = "dispsq.MEAN_PLUS_ERR";
    private int[] _recordAges;
    private List _stats;
}
