/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.Simulation;

import java.io.File;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class IterationSummaryStatisticsWriter extends ReportWriterBase {

    public IterationSummaryStatisticsWriter(File iterationOutputDir, boolean append) {
        super(append);
        _iterationOutputDir = iterationOutputDir;
    }

    protected void writeHeaders(CSVWriter out, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) {

        ReportSummaryStatistics stats = getStats(experiment);

        final int columnCount = IterationSummaryStatisticsWriter.SYSCOL_COUNT + stats.getStatisticNames().size();
        CSVRow header = new CSVRow(columnCount);
        writeSysHeaders(header);

        int iCol = IterationSummaryStatisticsWriter.SYSCOL_COUNT;
        for (Iterator itr = stats.getStatisticNames().iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            header.setString(iCol++, name);
        }
        out.writeRow(header);
    }

    private ReportSummaryStatistics getStats(IExperiment experiment) {
        ReportSummaryStatistics stats = new ReportSummaryStatistics();
        if (experiment.getIterationReporter() instanceof ISummaryStatisticsReporter) {
            stats = ((ISummaryStatisticsReporter)experiment.getIterationReporter()).getSummaryStats();
        }
        return stats;
    }

    private void writeSysHeaders(CSVRow header) {
        int iCol = 0;
        header.setString(iCol++, Simulation.PROPERTY_TITLE);
        header.setString(iCol++, "iteration");
        header.setString(iCol++, "replicate");
        header.setString(iCol++, Simulation.PROPERTY_EXECUTED_TIMESTEPS);
        header.setString(iCol++, ExperimentProgress.PROPERTY_ELAPSED_TIME);
        header.setString(iCol++, "elapsedTimeMS");
        header.setString(iCol++, "outputDir");


    }

    protected void writeResults(CSVWriter out, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) {

        ReportSummaryStatistics stats = getStats(experiment);
        final int columnCount = IterationSummaryStatisticsWriter.SYSCOL_COUNT + stats.getStatisticNames().size();

        for (Iterator itrRow = stats.getRows().iterator(); itrRow.hasNext();) {
            ReportSummaryStatisticsRow row = (ReportSummaryStatisticsRow)itrRow.next();

            CSVRow data = new CSVRow(columnCount);

            writeSysData(data, experiment, progress);

            int iCol = IterationSummaryStatisticsWriter.SYSCOL_COUNT;

            for (Iterator itr = stats.getStatisticNames().iterator(); itr.hasNext();) {
                String name = (String)itr.next();
                data.setObject(iCol++, row.getStatisticValue(name));
            }

            out.writeRow(data);
        }

    }

    private void writeSysData(CSVRow data, IExperiment experiment, ExperimentProgress progress) {
        int iCol = 0;
        data.setString(iCol++, experiment.getName());
        data.setLong(iCol++, progress.getCurrentIteration());
        data.setLong(iCol++, progress.getCurrentReplicate());
        data.setLong(iCol++, progress.getIterationTimestepsExecuted());
        data.setString(iCol++, progress.getElapsedTimeFormatted());
        data.setLong(iCol++, progress.getReplicateTimeMS());
        data.setString(iCol++, _iterationOutputDir.getName());
    }


    private static int SYSCOL_COUNT = 7;
    private File _iterationOutputDir;
}
