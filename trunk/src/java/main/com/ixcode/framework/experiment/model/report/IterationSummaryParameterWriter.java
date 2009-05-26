/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.report;

import com.ixcode.framework.experiment.model.report.ISummaryStatisticsReporter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.experiment.model.IExperiment;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class IterationSummaryParameterWriter extends ReportWriterBase {

    public IterationSummaryParameterWriter(File iterationOutputDir, boolean append) {
        super(append);
        _iterationOutputDir = iterationOutputDir;
    }

    protected void writeHeaders(CSVWriter out, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) {
        List parameters = currentParameters.getAllParameters();


        final int columnCount = SYSCOL_COUNT + parameters.size();

        CSVRow header = new CSVRow(columnCount);

        writeSysHeaders(header);

        int iCol = SYSCOL_COUNT;
        for (Iterator itr = parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();

            String name = parameter.getFullyQualifiedName();
            header.setString(iCol++, name);
        }


        out.writeRow(header);
    }



    private void writeSysHeaders(CSVRow header) {
        int iCol = 0;
        header.setString(iCol++, Simulation.PROPERTY_TITLE);
        header.setString(iCol++, "iteration");
        header.setString(iCol++, "replicateCount");




    }

    protected void writeResults(CSVWriter out, IExperiment experiment, String experimentId, ParameterMap currentParameters, ExperimentProgress progress) {
        List parameters = currentParameters.getAllParameters();


        final int columnCount = SYSCOL_COUNT + parameters.size() ;

        CSVRow data = new CSVRow(columnCount);

        writeSysData(data, experiment, progress);

        int iCol = SYSCOL_COUNT;
        for (Iterator itr = parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            Object val = (parameter.containsStrategy()) ? ((StrategyDefinitionParameter)parameter.getValue()).getName() : parameter.getValue();
            data.setObject(iCol++, val);
        }



        out.writeRow(data);
    }

    private void writeSysData(CSVRow data, IExperiment experiment, ExperimentProgress progress) {
        int iCol = 0;
        data.setString(iCol++, experiment.getName());
        data.setLong(iCol++, progress.getCurrentIteration());
        data.setLong(iCol++, progress.getReplicateCount());
        
    }


    private static int SYSCOL_COUNT = 3;
    private File _iterationOutputDir;
}
