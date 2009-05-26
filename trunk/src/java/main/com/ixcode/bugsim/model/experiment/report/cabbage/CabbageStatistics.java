/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.cabbage;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageStatisticEntry;
import com.ixcode.framework.datatype.analysis.AnalysisCategory;
import com.ixcode.framework.datatype.analysis.AnalysisValue;

import java.util.*;

/**
 * Description : Use it to generate statistics about cabbages.
 * <p/>
 * first add some cabbages then call "calculate" this will return a CabbageStatistics object with all your stats.
 */
public class CabbageStatistics {


    public void addCabbage(CabbageAgent cabbage, List analysisValues) {
        for (Iterator itr = analysisValues.iterator(); itr.hasNext();) {
            AnalysisValue analysisValue = (AnalysisValue)itr.next();

            AnalysisCategory cat = analysisValue.getCategory();
            if (!_analysisCategories.contains(cat)) {
                _analysisCategories.add(cat);
            }
            if (!_analysisCategoryStatisticMap.containsKey(cat.getCode())) {
                _analysisCategoryStatisticMap.put(cat.getCode(), new HashMap());
            }
            HashMap statistics = (HashMap)_analysisCategoryStatisticMap.get(cat.getCode());
            if (!statistics.containsKey(analysisValue.getCode())) {
                statistics.put(analysisValue.getCode(), new CabbageStatisticEntry(analysisValue));
            }

            CabbageStatisticEntry current = (CabbageStatisticEntry)statistics.get(analysisValue.getCode());
            current.addEggs(cabbage.getEggCount());

        }
    }


    public List getAnalysisCategories() {
        return _analysisCategories;
    }

    public CabbageStatisticEntry getStatisticEntry(AnalysisValue value) {
        HashMap entries = (HashMap)_analysisCategoryStatisticMap.get(value.getCategory().getCode());

        CabbageStatisticEntry entry = NULL_ENTRY;
        if (entries.containsKey(value.getCode())) {
            entry = (CabbageStatisticEntry)entries.get(value.getCode());
        } else {
            entry = new CabbageStatisticEntry(value);
        }
        return entry;
    }

    public AnalysisCategory getAnalysisCategory(String categoryName) {
        AnalysisCategory found = null;
        for (Iterator itr = _analysisCategories.iterator(); itr.hasNext();) {
            AnalysisCategory category = (AnalysisCategory)itr.next();
            if (category.getName().equals(categoryName)) {
                found = category;
                break;
            }
        }
        return found;
    }

    /**
     * calculates any summary statistics
     */
    public void calculate() {

        for (Iterator itr = _analysisCategoryStatisticMap.values().iterator(); itr.hasNext();) {
            HashMap categoryMap = (HashMap)itr.next();

            for (Iterator itrValue = categoryMap.values().iterator(); itrValue.hasNext();) {
                CabbageStatisticEntry entry = (CabbageStatisticEntry)itrValue.next();
                _totalEggsPerPlant += entry.getEggsPerPlant();
                _totalCabbages += entry.getNumberOfPlants();
                _totalEggs += entry.getTotalEggs();
            }
        }

        for (Iterator itr = _analysisCategoryStatisticMap.values().iterator(); itr.hasNext();) {
            HashMap categoryMap = (HashMap)itr.next();

            for (Iterator itrValue = categoryMap.values().iterator(); itrValue.hasNext();) {
                CabbageStatisticEntry entry = (CabbageStatisticEntry)itrValue.next();
                double ratioEggsPerPlant = entry.getEggsPerPlant() / _totalEggsPerPlant;
                entry.setEggsPerPlantRatio(ratioEggsPerPlant);
                _totalEggsPerPlantRatio += ratioEggsPerPlant;
            }
        }

    }

    public double getTotalEggsPerPlant() {
        return _totalEggsPerPlant;
    }

    public long getTotalEggs() {
        return _totalEggs;
    }

    public long getTotalPlants() {
        return _totalCabbages;
    }

    public double getTotalEggsPerPlantRatio() {
        return _totalEggsPerPlantRatio;
    }

    public boolean hasAnalysisCategory(String analysisCategoryName) {
        return getAnalysisCategory(analysisCategoryName) != null;
    }

    public boolean hasAnalysisCategory(AnalysisCategory category) {
        return hasAnalysisCategory(category.getName());
    }

    private Map _analysisCategoryStatisticMap = new HashMap();
    private static final CabbageStatisticEntry NULL_ENTRY = new CabbageStatisticEntry(null);
    private List _analysisCategories = new ArrayList();

    double _totalEggsPerPlant;
    long _totalEggs;
    long _totalCabbages;
    private double _totalEggsPerPlantRatio;
}
