/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.input;

import com.ixcode.framework.io.csv.CSVReader;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 *  Description : Loads Cabbages !
 */
public class CabbageLoader {


    public void loadCabbages(Landscape landscape, RectangularCoordinate origin , File importFile) throws IOException {
        CabbageCSVHandler handler = new CabbageCSVHandler(origin, DistanceUnitRegistry.metres(), landscape);

        CSVReader reader = new CSVReader(2, true);

        FileInputStream in = new FileInputStream(importFile);
        try {
            reader.readCSVFile(in, handler);
        } finally {
            in.close();
        }

    }


    public List loadCabbageInitialisationParameters(String filename, double radius, IDistanceUnit inputUnits, ScaledDistance logicalScale) throws IOException {
        CabbageInitialisationParametersCSVHandler handler = new CabbageInitialisationParametersCSVHandler(radius, inputUnits, logicalScale);

        if (log.isInfoEnabled()) {
            log.info("Loading cabbage information from '" + filename +  "' ...");
        }
        CSVReader reader = new CSVReader(1, true);

        FileInputStream in = new FileInputStream(filename);
        try {
            reader.readCSVFile(in, handler);
            return handler.getInitialisationParameters();
        } finally {
            in.close();
        }
    }



    private static final Logger log = Logger.getLogger(CabbageLoader.class);
}
