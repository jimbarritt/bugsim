/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageStatsWriter;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageWriter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageStatistics;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated
 */
public class EdgeEffectReporter  extends ExperimentListenerBase implements IExperimentReporter {

    private static final Logger log = Logger.getLogger(EdgeEffectReporter.class);


    public EdgeEffectReporter(String baseDirectoryName, String experimentalGridName) {
        _baseDirectoryName = baseDirectoryName;
        _experimentalGridName = experimentalGridName;
    }

     public void nextIteration(long loopIndex, IExperimentLoop loop, IExperiment experiment) {
        try {

            String loopId = createLoopId(loopIndex);
            if (loopIndex ==0) {
                _experimentId = nextExperimentId();
                _outputRoot = new File(makeExperimentPathName(_outputPath, _experimentId));
                _outputRoot.mkdirs();
            }
            File outputLoopDir = makeLoopDirName(loopId);
            outputLoopDir.mkdirs();
            outputExperimentParameters(_experimentId, loopId, loopIndex, experiment.getSimulation(), _outputRoot, outputLoopDir);


            outputResults(experiment.getSimulation(), outputLoopDir, _experimentId, loopId);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public boolean isOutputResults() {
        return _outputResults;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOutputResults(boolean outputResults) {
        _outputResults = outputResults;
    }

    public void setOutputRootPath(String path) {
        _outputPath = path;
    }

    public void experimentComplete(IExperiment experiment)  {


    }


    private void outputResults(Simulation simulation, File outputRoot, String experimentId, String loopId) throws IOException {
//        outputExperimentParameters(experiment, outputRoot);
        CabbageStatistics stats = outputCabbages(simulation,outputRoot, experimentId, loopId);
        outputCabbageStats(simulation, stats, outputRoot, experimentId, loopId);
        outputButterflyPaths(simulation, outputRoot);

    }

    private void outputButterflyPaths(Simulation simulation, File outputRoot) throws IOException {

    }

    private void outputCabbageStats(Simulation simulation, CabbageStatistics stats, File outputRoot, String experimentId, String loopId) throws IOException {
        CabbageStatsWriter writer = new CabbageStatsWriter(stats, "Isolation Level");  //@todo make analysis part of model, not experiment
        writer.write(createOutputFile(outputRoot,"cabbages-statistics", experimentId, loopId), simulation, experimentId, 0, 0);

    }



    private CabbageStatistics outputCabbages(Simulation simulation, File outputRoot, String experimentId, String loopId) throws IOException {
//        CabbageWriter writer = new CabbageWriter(_experimentalGridName, _outputS);
//        writer.write(createOutputFile(outputRoot, "cabbages", experimentId, loopId), simulation, experimentId, 0, 0);
//        return writer.getStats();
        return null;
    }


    private void outputExperimentParameters(String experimentId, String loopId, long loopIndex, Simulation simulation, File outputRoot, File loopDir) throws IOException {
        File out =  new File(outputRoot, makeExperimentRootOuputFilename("parameters", experimentId));
        SimulationParameterWriter writer = new SimulationParameterWriter(true, loopIndex, loopDir);
        writer.write(out, simulation, experimentId, 0, 0);
    }

    private String makeExperimentRootOuputFilename(String baseName, String experimentId) {
        return experimentId + "-" + baseName + ".csv";
    }

    private String  nextExperimentId() throws IOException {
        String id = nextId();
        File candidate = new File(makeExperimentPathName(_outputPath, id));
        int tries = 0;
        while(candidate.exists() && tries++ < 20) {
            id = nextId();
            candidate = new File(makeExperimentPathName(_outputPath, id));
        }
        if (tries >=20) {
            resetId();
            throw new IOException("Exceeded 20 directories for this experiment, please delete some!");
        }

        if (log.isInfoEnabled()) {
            log.info("Going to create results  in : " + candidate);
        }

        return id;
    }

    private void resetId() {
        EXPERIMENT_ID = 0;
    }

    /**
     * @todo refactor name creation to a seperate class.
     * @param rootPath
     * @param id
     * @return
     */
    private String makeExperimentPathName(String rootPath, String id) {
         return rootPath + File.separator + _baseDirectoryName + "-" + id + File.separator;
    }
    private File makeLoopDirName(String loopId) {
        File outputLoopDir = new File(_outputRoot, "Iteration-" + loopId);
        return outputLoopDir;
    }

     private File createOutputFile(File outputRoot, String filename, String experimentId, String loopId) {
        return new File(outputRoot, createOutputFilename(filename, experimentId, loopId));
    }

    private String createOutputFilename(String baseName, String experimentId, String loopId) {
        return  experimentId + "-" + loopId + "-" + baseName + ".csv";
    }

    private String nextId() {
        return FORMAT.format(EXPERIMENT_ID++);
    }
    private String createLoopId(long loopIndex) {
        return FORMAT.format(loopIndex + 1);
    }






    private NumberFormat FORMAT = new DecimalFormat("000");
    private File _outputRoot;
    private String _outputPath;
    private boolean _outputResults = true;
    private static long  EXPERIMENT_ID = 0;
    private String _baseDirectoryName;
    private String _experimentalGridName;
    private String _experimentId;
}
