/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.bugsim.experiment.report.butterfly.ButterflyDispersalRow;
import com.ixcode.bugsim.experiment.report.butterfly.ButterflyStatistics;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.math.stats.DescriptiveStatistics;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.dispersal.DispersalDistanceRecorder;
import com.ixcode.framework.simulation.model.agent.dispersal.DispersalRecorderAgentFilter;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class DispersalDistanceWriter extends SimulationWriterBase {




    public DispersalDistanceWriter() {
        super(true);
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(ButterflyDispersalRow.HEADER_ROW);
    }


    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        List agents = simulation.getLiveAgents(DispersalRecorderAgentFilter.INSTANCE);
        if (agents.size() > 0) {
            DispersalDistanceRecorder recorder = (DispersalDistanceRecorder)agents.get(0);
            recorder.calculateStatistics();
            Map stats = recorder.getStatistics();
            long[] ages = recorder.getAges();
            if (DispersalDistanceWriter.log.isInfoEnabled()) {
                DispersalDistanceWriter.log.info("Reporting " + ages.length + " Ages.");
            }

            for(int i=0;i<ages.length;++i) {
                Long age = new Long(ages[i]);                                                       
                DescriptiveStatistics s = (DescriptiveStatistics)stats.get(age);
                writeDescriptiveStatistics(age, s,out,  experimentId, currentIteration, currentReplicate);
            }
        }

    }


    private void writeDescriptiveStatistics(Long age, DescriptiveStatistics stats,CSVWriter out,  String experimentId, long currentIteration, long currentReplicant) {
        ButterflyDispersalRow row = new ButterflyDispersalRow();

        row.setExperimentId(experimentId);
        row.setIterationId(FORMAT.format(new Long(currentIteration)));
        row.setReplicateId(FORMAT.format(new Long(currentReplicant)));

        row.setAge(age);
        row.setDescriptiveStatistics(stats);



        out.writeRow(row);

    }

    public ButterflyStatistics getStats() {
        return _stats;
    }


    private Format FORMAT = new DecimalFormat("000");
    private String _experimentalGridName;
    private ButterflyStatistics _stats;
    private static final Logger log = Logger.getLogger(DispersalDistanceWriter.class);
}
