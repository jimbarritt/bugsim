/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.input;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.io.csv.ICSVHandler;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledCartesianCoordinate;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CabbageCSVHandler implements ICSVHandler {
    public CabbageCSVHandler(RectangularCoordinate origin,  IDistanceUnit units, Landscape landscape) {
        _origin = origin;
        _units = units;
        _landscape = landscape;
        System.out.println("CabbageHandler:init origin: " + _origin + ", _units: " + units + " landscape scale = " + landscape.getScale());
    }

    public void handleHeadings(String[] headings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handleRow(int rowId, String[] data) {
        long cabbageId = Long.parseLong(data[INDEX_ID]);
        double xpos = Double.parseDouble(data[INDEX_XPOS]);
        double ypos = Double.parseDouble(data[INDEX_YPOS]);

        ScaledCartesianCoordinate scaledCoord = new ScaledCartesianCoordinate(xpos, ypos, _units);
        RectangularCoordinate logicalCoord = _landscape.getScale().convertScaledToLogicalCoordinate(scaledCoord);


        double logicalX = _origin.getDoubleX() + logicalCoord.getDoubleX();
        double logicalY = _origin.getDoubleY() + logicalCoord.getDoubleY();

        CabbageAgent cabbage = new CabbageAgent(cabbageId, new Location(logicalX, logicalY), 20);
        System.out.println("Added cabbage (" + cabbage.getId() + ") at logical (" + logicalX + ", " + logicalY + ") original coords were : (" + xpos + ", " +ypos + ")");
        _landscape.getSimulation().addAgent(cabbage);
    }

    private static final int INDEX_ID = 0;
    private static final int INDEX_XPOS = 1;
    private static final int INDEX_YPOS = 2;


    //"id", "x", "y", "X1m_dens", "X6m_dens", "X36m_dens", "pred_treatment", "Teggs_240106", "field"

    private IDistanceUnit _units;
    private Landscape _landscape;
    private RectangularCoordinate _origin;
}
