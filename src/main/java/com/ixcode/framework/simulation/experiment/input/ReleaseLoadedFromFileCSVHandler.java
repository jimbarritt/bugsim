/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.input;

import com.ixcode.framework.io.csv.ICSVHandler;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseInitialisationParameters;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ReleaseLoadedFromFileCSVHandler implements ICSVHandler  {

    private static final int COL_X = 8;
    private static final int COL_Y = 9;
    private static final int COL_H = 10;
    private static final int COL_D = 11;
    private static final int COL_I = 13;

    public ReleaseLoadedFromFileCSVHandler() {
    }

    public void handleHeadings(String[] headings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handleRow(int rowId, String[] data) {
        double x = Double.parseDouble(data[COL_X]);
        double y = Double.parseDouble(data[COL_Y]);
        double h = Double.parseDouble(data[COL_H]);
        double d = Double.parseDouble(data[COL_D]);
        double i = Double.parseDouble(data[COL_I]);

        ReleaseInitialisationParameters p = new ReleaseInitialisationParameters(x, y, h, d, i);

        _parameters.add(p);

        if (log.isDebugEnabled()) {
            log.debug("Loaded birth parameter: " + p);
        }
    }


    public List getParameters() {
        return _parameters;
    }

    private List _parameters = new ArrayList();
    private static final Logger log = Logger.getLogger(ReleaseLoadedFromFileCSVHandler.class);
}


