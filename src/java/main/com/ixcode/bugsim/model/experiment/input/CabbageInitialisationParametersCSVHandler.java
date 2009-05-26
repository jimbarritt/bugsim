/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.input;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.CabbageInitialisationParameters;
import com.ixcode.framework.io.csv.ICSVHandler;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledCartesianCoordinate;
import com.ixcode.framework.math.scale.ScaledDistance;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class CabbageInitialisationParametersCSVHandler implements ICSVHandler {


    public CabbageInitialisationParametersCSVHandler(double radius, IDistanceUnit inputUnits, ScaledDistance logicalScale) {

        _radius = radius;
        _inputUnits = inputUnits;
        _logicalScale = logicalScale;
    }

    public void handleHeadings(String[] headings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handleRow(int rowId, String[] data) {
        long cabbageId = Long.parseLong(data[INDEX_ID]);
        double xpos = Double.parseDouble(data[INDEX_XPOS]);
        double ypos = Double.parseDouble(data[INDEX_YPOS]);

        ScaledCartesianCoordinate inputLocation= new ScaledCartesianCoordinate(xpos, ypos, _inputUnits);

        RectangularCoordinate logicalLocation = inputLocation.scaleCoordinate(_logicalScale);
        CabbageInitialisationParameters params = new CabbageInitialisationParameters(cabbageId, logicalLocation, _radius);
        _initialisationParameters.add(params);
    }

    public List getInitialisationParameters() {
        return _initialisationParameters;
    }

    private static final int INDEX_ID = 0;
    private static final int INDEX_XPOS = 1;
    private static final int INDEX_YPOS = 2;


    //"id", "x", "y", "X1m_dens", "X6m_dens", "X36m_dens", "pred_treatment", "Teggs_240106", "field"

    private double _radius;
    private List _initialisationParameters = new ArrayList();
    private IDistanceUnit _inputUnits;

    private ScaledDistance _logicalScale;
}
