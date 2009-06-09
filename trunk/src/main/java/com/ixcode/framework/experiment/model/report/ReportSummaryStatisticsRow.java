/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ReportSummaryStatisticsRow {

    public ReportSummaryStatisticsRow() {
    }

    public List getStatisticNames() {
        return _names;
    }

    public  Object getStatisticValue(String name) {
        return _stats.get(name);
    }

    public void addStatistic(String name, long value) {
        addStatistic(name, new Long(value));

    }
    public void addStatistic(String name, double value) {
        addStatistic(name, new Double(value));

    }
    public void addStatistic(String name, Object value) {
        _stats.put(name, value);
        _names.add(name);
    }

    


    private Map _stats = new HashMap();
    private List _names = new ArrayList();
}
