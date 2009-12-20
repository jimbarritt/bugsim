/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.agent.motile;

import com.ixcode.bugsim.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.MotileAgentBase;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.MixedModelSignalAndDesireRandomWalk;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Description : Displays graphically the state of a signalbased agent.
 * This includes the input from all of its sensors and the probability matrix which is determining its movement behaviour
 */
public class SignalBasedAgentInspector extends JPanel implements PropertyChangeListener {

    public SignalBasedAgentInspector(LandscapeView view) {
        super.setLayout(new BorderLayout());

        JFreeChart chart = createEmptyChart("Predisposition");
        _randomWalkChart = new ChartPanel(chart);
        _randomWalkChart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JFreeChart chart2 = createEmptyChart("Olfactory Bias");
        _olfactionChart = new ChartPanel(chart2);
        _olfactionChart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JFreeChart chart3 = createEmptyChart("Visual Bias");
        _visualChart = new ChartPanel(chart3);
        _visualChart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JFreeChart chart4 = createEmptyChart("Resulting Action");
        _actionChart = new ChartPanel(chart4);
        _actionChart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel chartPanel = new JPanel();

        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.PAGE_AXIS));
        chartPanel.add(_randomWalkChart);
        chartPanel.add(_olfactionChart);
        chartPanel.add(_visualChart);
        chartPanel.add(_actionChart);
        super.add(chartPanel, BorderLayout.CENTER);
        _view = view;

    }

    public static JFreeChart createEmptyChart(String title) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Heading (h)",
                "P",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        return chart;
    }

    /**
     * title - the chart title (null permitted).
     * categoryAxisLabel - the label for the category axis (null permitted).
     * valueAxisLabel - the label for the value axis (null permitted).
     * dataset - the dataset for the chart (null permitted).
     * orientation - the plot orientation (horizontal or vertical) (null not permitted).
     * legend - a flag specifying whether or not a legend is required.
     * tooltips - configure chart to generate tool tips?
     * urls - configure chart to generate
     *
     * @param dataset
     * @return
     */
    public static JFreeChart createBarChart(String title, CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Heading (h)",
                "P",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        return chart;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MotileAgentBase.PROPERTY_LOCATION)) {
            createCharts();
            redraw();
        }
    }


    public void setAgent(IMotileAgent agent) {
        if (_agent != null) {
            _agent.removePropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        }
        _agent = agent;
        _agent.addPropertyChangeListener(MotileAgentBase.PROPERTY_LOCATION, this);
        IMovementStrategy movementStrategy = _agent.getMovementStrategy();

        if (!(movementStrategy instanceof MixedModelSignalAndDesireRandomWalk)) {
            throw new IllegalStateException("Tried to inspect something thats not a signal based random walk!");
        }

        _signalAndDesireCRW = (MixedModelSignalAndDesireRandomWalk)movementStrategy;
        createCharts();
        redraw();
    }

    private void createCharts() {
        List sensors = SignalSensorOlfactoryStrategy.getSensorsFromAgent(_agent);
        DiscreetValueMap baseDistribution = new DiscreetValueMap(-180, 180, 1, DoubleMath.DOUBLE_PRECISION_DELTA);
        CategoryDataset dataset = createCategoryDataset(baseDistribution);
        JFreeChart chart = createBarChart("Predisposition", dataset);
        _randomWalkChart.setChart(chart);

        DiscreetValueMap olfactoryDistribution = _signalAndDesireCRW.createOlfactoryPDistribution(sensors, _agent, _view.getLandscape());
        CategoryDataset dataset2 = createCategoryDataset(olfactoryDistribution);
        JFreeChart chart2 = createBarChart("Olfactory Bias",dataset2);
        _olfactionChart.setChart(chart2);

//        DiscreetValueMap visualDistribution = _signalAndDesireCRW.createVisualPDistribution(_agent, _view.getLandscape());
//        CategoryDataset dataset3 = createCategoryDataset(visualDistribution);
//        JFreeChart chart3 = createBarChart("Visual Bias",dataset3);
//        _visualChart.setChart(chart3);


        DiscreetValueMap actionDistribution = _signalAndDesireCRW.createActionDistribution(sensors, _agent, _view.getLandscape());
        CategoryDataset dataset4 = createCategoryDataset(actionDistribution);
        JFreeChart chart4 = createBarChart("Resulting Action",dataset4);
        _actionChart.setChart(chart4);
    }

    private CategoryDataset createCategoryDataset(DiscreetValueMap pdistribution) {
        KeyedValues keyedValues = new DiscreetValueMapFunctionWrapper(pdistribution);
        return DatasetUtilities.createCategoryDataset(new Integer(0), keyedValues);

    }

    private void redraw() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                invalidate();
                repaint();
            }
        });

    }


    private IMotileAgent _agent;
    private MixedModelSignalAndDesireRandomWalk _signalAndDesireCRW;
    private ChartPanel _randomWalkChart;
    private ChartPanel _olfactionChart;
    private ChartPanel _visualChart;
    private ChartPanel _actionChart;
    private LandscapeView _view;
}
