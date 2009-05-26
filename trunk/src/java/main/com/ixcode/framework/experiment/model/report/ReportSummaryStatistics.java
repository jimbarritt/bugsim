/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model.report;

import java.util.List;
import java.util.ArrayList;
import java.beans.beancontext.BeanContextMembershipEvent;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 3, 2007 @ 5:53:02 PM by jim
 */
public class ReportSummaryStatistics {

    public ReportSummaryStatistics() {
    }

    public void addRow(ReportSummaryStatisticsRow row) {
        _rows.add(row);
    }

    public List getRows() {
        return _rows;
    }

    public List getStatisticNames() {
        if (_rows.size() >0) {
            return ((ReportSummaryStatisticsRow)_rows.get(0)).getStatisticNames();
        } else {
            return new ArrayList();
        }
    }

    private List _rows = new ArrayList();
}
