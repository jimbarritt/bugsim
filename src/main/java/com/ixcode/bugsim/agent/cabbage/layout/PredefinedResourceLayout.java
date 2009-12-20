/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage.layout;

import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.CabbageInitialisationParameters;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.stats.SummaryStatistics;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class PredefinedResourceLayout extends ResourceLayoutBase {

    public void initialise(StrategyDefinitionParameter strategyParameter, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyParameter, params, initialisationObjects);

        PredefinedResourceLayoutStrategy pds = new PredefinedResourceLayoutStrategy(strategyParameter, params, false);
        _predefinedInitialisations = pds.getResources();
        _patchBounds = pds.getLayoutBoundary().getBounds();
        _searchGridResolution = pds.getSearchGridResolution();
        _applyFixedRadius = pds.getApplyFixedResourceRadius();
        _fixedRadius = pds.getFixedResourceRadius();
    }


    public void createCabbages(Simulation simulation) {

        _cabbages = createCabbagePatch(simulation, getResourceLayoutGridName(), _patchBounds, _predefinedInitialisations, _searchGridResolution);


        double meanRadius = (_predefinedInitialisations.size() >0) ? calculateMeanRadius(_predefinedInitialisations) : 0;

        _S = "N/A";
        _P = "ArrayList[size=" + _predefinedInitialisations.size() + "]";
        _R = FORMAT2D.format(meanRadius);

        super.createCabbageInformationSurfaces(simulation.getLandscape());
    }

    private List createCabbagePatch(Simulation simulation, String gridName, CartesianBounds logicalPatchBounds, List predefinedInitialisations, int searchGridResolution) {
        Landscape landscape = simulation.getLandscape();
        CartesianBounds centredB = logicalPatchBounds.centre(landscape.getLogicalBounds());

        if (log.isInfoEnabled()) {
                log.info("Creating cabbages: useFixedRadius=" + _applyFixedRadius + ", radius=" + _fixedRadius);
            }
        for (Iterator itr = predefinedInitialisations.iterator(); itr.hasNext();) {
            CabbageInitialisationParameters params = (CabbageInitialisationParameters)itr.next();
            RectangularCoordinate adjustedLocation = centredB.convertLocalToGlobalCoord(new RectangularCoordinate(params.getX(), params.getY()));

            double radius = (_applyFixedRadius) ? _fixedRadius : params.getRadius();


            CabbageAgent cabbage = new CabbageAgent(params.getCabbageId(), new Location(adjustedLocation), radius);
            simulation.addAgent(cabbage);
        }


        Grid analysisGrid = GridFactory.createGrid(landscape,  gridName, centredB, 1, 1);
        landscape.addGrid(analysisGrid);

        Grid searchGrid = GridFactory.createGrid(landscape, SEARCH_GRID_NAME, centredB, searchGridResolution, searchGridResolution);
        landscape.addGrid(searchGrid);
        return new ArrayList();
    }


    private double calculateMeanRadius(List predefinedInitialisations) {
        double[] radii = new double[predefinedInitialisations.size()];
        for (int i = 0; i < predefinedInitialisations.size(); ++i) {
            CabbageInitialisationParameters params = (CabbageInitialisationParameters)predefinedInitialisations.get(i);
            radii[i] = params.getRadius();
        }
        return SummaryStatistics.calculateMeanDouble(radii);
    }

    public String getParameterSummary() {
        return super.getParameterSummary() + "S=" + _S + ", R=" + _R + ", P=(predefined: " + _P + ")";
    }

    public List getCabbages() {
        return _cabbages;
    }

    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _P;
    private String _R;
    private String _S;
    private List _cabbages;
    private CartesianBounds _patchBounds;

    private BigDecimal _centreX;
    private BigDecimal _centreY;


    private List _predefinedInitialisations;

    public static final String SEARCH_GRID_NAME = "searchGrid";
    private int _searchGridResolution;
    private boolean _applyFixedRadius;
    private double _fixedRadius;
    private static final Logger log = Logger.getLogger(PredefinedResourceLayout.class);
}
