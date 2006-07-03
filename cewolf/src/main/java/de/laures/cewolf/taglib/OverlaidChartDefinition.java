/*
 * Created on 13.04.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
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
 * Definiton object for the overlaid charts.
 * @author guido
 *
 */
public class OverlaidChartDefinition extends AbstractChartDefinition implements Serializable {

	private int xAxisType = 0;
	private int yAxisType = 0;
	
    private List plotDefinitions = new ArrayList();
    private transient DrawingSupplier drawingSupplier = new DefaultDrawingSupplier();

    public Object getDataset() throws DatasetProduceException {
        return ((PlotDefinition)plotDefinitions.get(0)).getDataset();
    }

    /**
     * Add a new sub-plot will be displayed as part of the overlaid chart.
     * @param pd The definition
     */
    public void addPlot(PlotDefinition pd) {
        pd.setDrawingSupplier(drawingSupplier);
        plotDefinitions.add(pd);
    }

    protected JFreeChart produceChart() throws DatasetProduceException, ChartValidationException {
        log.debug("xAxisType = " + xAxisType);
        return CewolfChartFactory.getOverlaidChartInstance(type, title, xAxisLabel, yAxisLabel, xAxisType, yAxisType, plotDefinitions);
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

}
