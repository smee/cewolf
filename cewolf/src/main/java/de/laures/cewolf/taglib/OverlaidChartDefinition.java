/*
 * Created on 13.04.2003
 */
package de.laures.cewolf.taglib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.data.general.Dataset;

import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;

/**
 * @author guido
 */
public class OverlaidChartDefinition extends AbstractChartDefinition {

	static final long serialVersionUID = 2620349751116074174L;

	private int xAxisType = 0;
	private int yAxisType = 0;

    private List plotDefinitions = new ArrayList();
    private transient DrawingSupplier drawingSupplier;

    public Dataset getDataset() throws DatasetProduceException {
        return ((PlotDefinition)plotDefinitions.get(0)).getDataset();
    }

    public void addPlot(PlotDefinition pd) {
        pd.setDrawingSupplier(getDrawingSupplier());
        plotDefinitions.add(pd);
    }

    protected JFreeChart produceChart() throws DatasetProduceException, ChartValidationException {
        return CewolfChartFactory.getOverlaidChartInstance(type, title, xAxisLabel, yAxisLabel, xAxisType, yAxisType, plotDefinitions, showLegend);
    }

	/**
	 * Sets the xAxisType.
	 * @param xAxisType The xAxisType to set
	 */
	public void setXAxisType(int xAxisType) {
		this.xAxisType = xAxisType;
	}

	/**
	 * Sets the yAxisType.
	 * @param yAxisType The yAxisType to set
	 */
	public void setYAxisType(int yAxisType) {
		this.yAxisType = yAxisType;
	}

	private DrawingSupplier getDrawingSupplier() {
		if (drawingSupplier == null) {
			drawingSupplier = new DefaultDrawingSupplier();
		}
		return drawingSupplier;
	}
}
