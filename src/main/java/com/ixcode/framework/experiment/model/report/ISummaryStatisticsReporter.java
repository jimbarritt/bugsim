/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.experiment.model.report.IExperimentReporter;

public interface ISummaryStatisticsReporter extends IExperimentReporter {

    ReportSummaryStatistics getSummaryStats();

}
