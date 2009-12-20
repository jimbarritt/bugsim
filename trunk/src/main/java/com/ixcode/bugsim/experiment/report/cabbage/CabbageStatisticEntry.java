/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.cabbage;

import com.ixcode.framework.datatype.analysis.AnalysisValue;

/**
 *  Description : THis keeps a track of everything for a given analysis code
 */
public class CabbageStatisticEntry {


    public CabbageStatisticEntry(AnalysisValue analysisValue) {
        _analysisValue = analysisValue;
    }

    public void addEggs(long numberOfEggs) {
        _totalEggs += numberOfEggs;
        _numberOfPlants++;
    }

    public long getTotalEggs() {
        return _totalEggs;
    }

    public long getNumberOfPlants() {
        return _numberOfPlants;
    }

    public double getEggsPerPlant() {
        return (double)_totalEggs / (double)_numberOfPlants;
    }

    public double getEggsPerPlantRatio() {
        return _eggsPerPlantRatio;
    }

    public void setEggsPerPlantRatio(double eggsPerPlantRatio) {
        _eggsPerPlantRatio = eggsPerPlantRatio;
    }

    public AnalysisValue getAnalysisValue() {
        return _analysisValue;
    }

    private long _totalEggs;
    private long _numberOfPlants;

    private double _eggsPerPlantRatio;
    private AnalysisValue _analysisValue;
}
