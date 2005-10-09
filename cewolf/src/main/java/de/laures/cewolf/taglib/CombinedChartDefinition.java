package de.laures.cewolf.taglib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;

import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;

/**
 * Chart definition subclass to handle combined charts
 *
 * @author guido
 * @author tbardzil
 */
public class CombinedChartDefinition extends AbstractChartDefinition implements Serializable {
    private String layout;

    private List plotDefinitions = new ArrayList();
    private transient DrawingSupplier drawingSupplier = new DefaultDrawingSupplier();

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Object getDataset() throws DatasetProduceException {
        return ((PlotDefinition)plotDefinitions.get(0)).getDataset();
    }

    public void addPlot(PlotDefinition pd) {
        pd.setDrawingSupplier(drawingSupplier);
        plotDefinitions.add(pd);
    }

    protected JFreeChart produceChart() throws DatasetProduceException, ChartValidationException {
        return CewolfChartFactory.getCombinedChartInstance(type, title, xAxisLabel, yAxisLabel, plotDefinitions, layout);
    }

}
