/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.math.function.FunctionRegistry;
import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.swing.JFrameExtension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class FunctionDisplayPanel extends JPanel implements PropertyChangeListener {


    public FunctionDisplayPanel() {
        super.setLayout(new BorderLayout());

        _chart = createLineChart(_dataset);
        _chartPanel = new ChartPanel(_chart);
        _chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        super.add(_chartPanel, BorderLayout.CENTER);

        _samplingParameters = new ChartSamplingParameters(0.5, 1, 100);

        _samplingParameters.addPropertyChangeListener(this);
    }

    public ChartSamplingParameters getSamplingParameters() {
        return _samplingParameters;
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        redisplayChart();
    }

    public JComponent createChartComponent(ISignalFunction signalFunction) {
        XYDataset dataset = createXYDataset(signalFunction, _samplingParameters);
//        XYSeries series = new XYSeries("Data");
//        for (int i=0;i<100;++i) {
//            series.add(i, Math.pow(i, 2));
//        }

//        XYDataset dataset = new XYSeriesCollection(series);
        return new ChartPanel(createLineChart(dataset));
    }

    private void redisplayChart() {
        JFreeChart chart;

        if (_chartType == ChartType.XY_PLOT) {
            XYDataset dataset = createXYDataset(_signalFunction, _samplingParameters);
            chart = createLineChart(dataset);
        } else if (_chartType == ChartType.BAR_PLOT) {
            CategoryDataset dataset = createCategoryDataset(_signalFunction, _samplingParameters);
            chart = createBarChart(dataset);
        } else {
            throw new IllegalStateException("Unrecognised chart type: " + _chartType);
        }
        _chartPanel.setChart(chart);
    }

    private CategoryDataset createCategoryDataset(ISignalFunction signalFunction, ChartSamplingParameters samplingParameters) {

        KeyedValues keyedValues = new KeyedValuesFunctionWrapper(signalFunction, _samplingParameters);
        return DatasetUtilities.createCategoryDataset(new Integer(0), keyedValues);

    }

    public static XYDataset createXYDataset(ISignalFunction signalFunction, ChartSamplingParameters params) {
        Function2D function = new Function2DFunctionWrapper(signalFunction, 1);
        XYDataset dataset = DatasetUtilities.sampleFunction2D(function, params.getSampleRangeStart(),
                params.getSampleRangeEnd(), params.getSampleFrequency(), "Function");
//        System.out.println("Created Dataset with " + params);
        return dataset;
    }

    public static JFreeChart createLineChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Function Plot",
                "input (x)",
                "output (y)",
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
        categoryAxisLabel - the label for the category axis (null permitted).
        valueAxisLabel - the label for the value axis (null permitted).
        dataset - the dataset for the chart (null permitted).
        orientation - the plot orientation (horizontal or vertical) (null not permitted).
        legend - a flag specifying whether or not a legend is required.
        tooltips - configure chart to generate tool tips?
        urls - configure chart to generate
     * @param dataset
     * @return
     */
    public static JFreeChart createBarChart(CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart(
                "Function Plot",
                "input (x)",
                "output (y)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        return chart;
    }

    public void setForceFunction(ISignalFunction function) {
        if (_signalFunction != null) {
            ((ModelBase)_signalFunction).removePropertyChangeListener(this);

        }
        Object old = _signalFunction;
        _signalFunction = function;
        redisplayChart();
        super.firePropertyChange("forceFunction", old, _signalFunction);
        ((ModelBase)_signalFunction).addPropertyChangeListener(this);
    }


    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FunctionRegistry functionRegistry = new FunctionRegistry();
                createAndShowGUI(functionRegistry);
            }

        });


    }

    private static void createAndShowGUI(FunctionRegistry functionRegistry) {
        FunctionDisplayPanel functionDisplay = new FunctionDisplayPanel();
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));


        west.add(Box.createHorizontalStrut(260));

        JPanel content = new JPanel(new BorderLayout());
        JFrameExtension testFrame = new JFrameExtension("Function Display Panel", content, false);
        testFrame.setStatusBar(false);

        JPanel functionParametersContainerPanel = createFunctionParametersPanel(functionDisplay, testFrame, functionRegistry);
        FunctionChoicePanel functionChoicePanel = new FunctionChoicePanel(functionDisplay, functionRegistry);
        SamplingParametersPanel samplingParametersPanel = new SamplingParametersPanel(functionDisplay.getSamplingParameters());


        west.add(functionChoicePanel);
        west.add(functionParametersContainerPanel);
        west.add(samplingParametersPanel);
        content.add(functionDisplay, BorderLayout.CENTER);

        JPanel westContainer = new JPanel(new BorderLayout());
        westContainer.add(west, BorderLayout.NORTH);
        content.add(westContainer, BorderLayout.WEST);


        testFrame.pack();
        testFrame.show();
    }

    private static JPanel createFunctionParametersPanel(FunctionDisplayPanel functionDisplay, JFrameExtension testFrame, FunctionRegistry functionRegistry) {
        JPanel containerPanel = new JPanel(new CardLayout());
        for (Iterator itr = functionRegistry.getFunctionNames().iterator(); itr.hasNext();) {
            String functionName = (String)itr.next();
            ISignalFunction function = (ISignalFunction)functionRegistry.getFunction(functionName);
            JComponent parameterPanel = ForceFunctionParameterPanelFactory.instance().createParameterPanel(function);
            containerPanel.add(functionName, parameterPanel);
        }

        functionDisplay.addPropertyChangeListener(new FunctionParameterController(testFrame, containerPanel));
        return containerPanel;
    }

    private static class FunctionParameterController implements PropertyChangeListener {

        public FunctionParameterController(JFrame container, JPanel functionParametersContainerPanel) {
            _containerPanel = functionParametersContainerPanel;
            _frame = container;
        }

        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            if (propertyChangeEvent.getPropertyName() == "forceFunction") {
                setupParameterPanel((FunctionDisplayPanel)propertyChangeEvent.getSource());
            }
        }

        private void setupParameterPanel(FunctionDisplayPanel functionDisplayPanel) {
            ISignalFunction function = functionDisplayPanel.getForceFunction();


            CardLayout cardLayout = (CardLayout)_containerPanel.getLayout();

            cardLayout.show(_containerPanel, function.getName());
        }

        private JPanel _containerPanel;
        private JFrame _frame;
    }

    private ISignalFunction getForceFunction() {
        return _signalFunction;
    }

    public ChartType getChartType() {
        return _chartType;
    }

    public void setChartType(ChartType chartType) {
        _chartType = chartType;
    }

    private XYSeriesCollection _dataset = new XYSeriesCollection();
    private ChartPanel _chartPanel;

    private static final XYDataset EMPTY_DATASET;

    static {
        XYSeries series = new XYSeries("No function selected");
        EMPTY_DATASET = new XYSeriesCollection(series);
    }

    private JFreeChart _chart;

    private ChartSamplingParameters _samplingParameters;
    private ISignalFunction _signalFunction;
    private ChartType _chartType = ChartType.XY_PLOT;
}
