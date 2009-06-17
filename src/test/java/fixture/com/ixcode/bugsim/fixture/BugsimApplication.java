package com.ixcode.bugsim.fixture;

import org.apache.log4j.*;

import java.io.*;
import java.net.*;

/**
 * Provides an api to running the bugsim application.
 */
public class BugsimApplication {
    private static final Logger log = Logger.getLogger(BugsimApplication.class);

    private static String getApplicationPath() {
        return "open -a ./target/application/bugsim.app";
    }

    private static String getOutputPath() {
        return "./target/acceptance-test-output";
    }


    public void executeExperimentPlan(URL experimentPlanFilename) {
        executeProcess(getApplicationPath() + " -plan=" + experimentPlanFilename.toExternalForm());
    }

    private static void executeProcess(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader applicationOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            logOutput(applicationOutput);

            applicationOutput.close();
        } catch (Exception t) {
            throw new RuntimeException("Problem executing process [" + command + "]");
        }
    }

    private static void logOutput(BufferedReader input) throws IOException {
        String line;
        while ((line = input.readLine()) != null) {
            log.info(line);
        }
    }

    public File[] listOutputFiles() {
        return new File(getOutputPath()).listFiles();
    }
}
