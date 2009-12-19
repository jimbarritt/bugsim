package com.ixcode.bugsim;

import static com.ixcode.bugsim.BugsimVersion.*;
import com.ixcode.bugsim.view.landscape.*;
import static com.ixcode.framework.logging.Log4JConfiguration.*;
import static com.ixcode.framework.math.scale.DistanceUnitRegistry.*;
import static com.ixcode.framework.math.scale.ScaledDistance.*;
import com.ixcode.framework.simulation.model.*;
import com.ixcode.framework.simulation.model.landscape.*;
import org.apache.log4j.*;

import java.io.*;

/**
 * Re-work of BugsimMain to simplify.
 */
public class BugsimApplication {

    private static final Logger log = Logger.getLogger(BugsimApplication.class);

    public static void main(String[] args) {
        loadLog4JConfig();
        log.info("Welcome to BugSim : Version [" + getVersion() + "]");
        log.info("BugSim is running in [" + new File(".").getAbsolutePath() + "]");
        new BugsimApplication().start();
    }

    private void start() {
        Simulation simulation = new Simulation();
        Landscape landscape = new Landscape(simulation);
        landscape.setScale(scaledDistanceOf(1, centimetres()));
        landscape.setScaledSize(scaledDistanceOf(10, metres()));

        landscape.setCircular(true);

        LandscapeFrame landscapeFrame = new LandscapeFrame(landscape);
        landscapeFrame.open();
    }     
}
