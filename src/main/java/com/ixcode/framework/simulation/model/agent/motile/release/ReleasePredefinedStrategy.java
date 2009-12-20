/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 * @todo dependant on the location of the nescessary information in the parameters - need to split this up.
 */
public class ReleasePredefinedStrategy extends ReleaseRandomAroundBorderStrategy {


    /**
     * @param algorithmParameter
     * @param params
     * @param simulation
     */
    public void initialise(StrategyDefinitionParameter algorithmParameter, ParameterMap params, Map initialisationObjects) {
        super.initialise(algorithmParameter, params, initialisationObjects);


        Parameter predefinedParameters = params.findParameter(ButterflyParameters.BUTTERFLY_AGENT_LIFECYCLE_BIRTH_PREDEFINED_PARAMETERS);
        _parameters = (List)predefinedParameters.getValue();


    }


    public ReleasePredefinedStrategy() {

    }

    public RectangularCoordinate generateInitialLocation() {
        if (_locationIndex >= _parameters.size()) {
            throw new RuntimeException("Run Out of parameters to create new butterflies!!");
        }
        ReleaseInitialisationParameters p = (ReleaseInitialisationParameters)_parameters.get(_locationIndex);
        _locationIndex++;
//        BigDecimal x = p.getDoubleX();
//        BigDecimal y = p.getDoubleY();
//        return new RectangularCoordinate(x, y);
        return Geometry.findCoordAroundPerimeterOfCircle(super.getZeroBoundaryBounds(), p.getI());

    }

    public double generateInitialAzimuth() {
        if (_headingIndex >= _parameters.size()) {
            throw new RuntimeException("Run Out of parameters to create new butterflies!!");
        }
        ReleaseInitialisationParameters p = (ReleaseInitialisationParameters)_parameters.get(_headingIndex);
        _headingIndex++;
        return p.getH();

    }

    public double generateReleaseI() {
        if (_releaseIIndex >= _parameters.size()) {
            throw new RuntimeException("Run Out of parameters to create new butterflies!! " + _releaseIIndex + " : " + _parameters.size());
        }
        ReleaseInitialisationParameters p = (ReleaseInitialisationParameters)_parameters.get(_releaseIIndex);
        _releaseIIndex++;
        return p.getI();

    }

    public double generateBirthMoveLength() {
        if (_moveLengthIndex >= _parameters.size()) {
            throw new RuntimeException("Run Out of parameters to create new butterflies!!");
        }
        ReleaseInitialisationParameters p = (ReleaseInitialisationParameters)_parameters.get(_moveLengthIndex);
        _moveLengthIndex++;
        return p.getD();

    }


    private static final Logger log = Logger.getLogger(ReleasePredefinedStrategy.class);

    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");


    private int _headingIndex = 0;
    private int _locationIndex = 0;
    private List _parameters;
    public static final String ZERO_BOUNDARY_GRID = "Zero Boundary";
    private int _moveLengthIndex = 0;
    private int _releaseIIndex = 0;
}
