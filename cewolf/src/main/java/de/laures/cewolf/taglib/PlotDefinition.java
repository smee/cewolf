/* ================================================================
 * Cewolf : Chart enabling Web Objects Framework
 * ================================================================
 *
 * Project Info:  http://cewolf.sourceforge.net
 * Project Lead:  Guido Laures (guido@laures.de);
 *
 * (C) Copyright 2002, by Guido Laures and contributers
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package de.laures.cewolf.taglib;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;

import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * (sub) Plot definition for combined/overlaid charts.
 * 
 * @author Chris McCann
 * @author Guido Laures
 */
public class PlotDefinition implements DataAware, Serializable, TaglibConstants {

	private transient Log log = LogFactory.getLog(getClass());

	// the type of the plot
	private String type;
	// x label
	private String xAxisLabel; // [tb]
	// y label
	private String yAxisLabel; // [tb]

	private DataContainer dataAware = new DataContainer();
	private transient Plot plot;
	private transient DrawingSupplier drawingSupplier = null;

	public Plot getPlot(String chartType) throws DatasetProduceException, ChartValidationException {
    log.debug("Plot.getPlot: chartType: " + chartType);
		if (plot == null) {
			
			Dataset data = (Dataset) getDataset();
			log.debug("Plot.getPlot: data name: " +data.getClass().getName());
			
			// expected plot type for custom charts
			Class expectedPlotType = null;

			try {
				if (chartType.equals(ChartConstants.CHARTTYPE_OVERLAID_XY) || chartType.equals(ChartConstants.CHARTTYPE_COMBINED_XY)) {
					expectedPlotType = XYPlot.class;
					
					AbstractRenderer renderer = PlotTypes.getRenderer(type);
					checkType(renderer, XYItemRenderer.class, "On plot:" + type);
					check(data, XYDataset.class, type);
					plot = new XYPlot((XYDataset) data, null, null, (XYItemRenderer) renderer);
				} else if (chartType.equals(ChartConstants.CHARTTYPE_OVERLAID_CATEGORY)) {
					expectedPlotType = CategoryPlot.class;
					
					AbstractRenderer renderer = PlotTypes.getRenderer(type);
					checkType(renderer, CategoryItemRenderer.class, "On plot:" + type);
					check(data, CategoryDataset.class, type);
					plot = new CategoryPlot((CategoryDataset) data, null, null, (CategoryItemRenderer) renderer);
				}
			} catch (UnsupportedChartTypeException ex) {
				log.warn("Can not find plot type:" , ex);
				// try to get the plot from the registered chart factories, so any chart would work
				// create the plot by creating a chart with the standard factory and get the plot of that...
				JFreeChart chart2;
				try {
					chart2 = CewolfChartFactory.getChartInstance(type, null, xAxisLabel, yAxisLabel, data);
				} catch (UnsupportedChartTypeException exChart) {
					// re-throw the original exception, because the chart type is not found
					throw ex;
				}
				plot = chart2.getPlot();
				
				// verify the expected plot is the appropriate type
				if (!expectedPlotType.isInstance(plot)) {
					throw new RuntimeException("Invalid plot class created, expected type:" + expectedPlotType.getClass().getName() + ", but the class of was created:" + plot.getClass().getName());
				}
			}
		}
		
		plot.setDrawingSupplier(drawingSupplier);
		return plot;
	}

	public Object getDataset() throws DatasetProduceException {
		return dataAware.getDataset();
	}

	/**
	 * Gets the y-axis label. [tb]
	 *
	 * @return the y-axis label.
	 */
	public String getXaxislabel() {
		return xAxisLabel;
	}

	/**
	 * Sets the x-axis label [tb]
	 *
	 * @return the x-axis label
	 */
	public String getYaxislabel() {
		return yAxisLabel;
	}

	/**
	 * Sets the x-axis label [tb]
	 *
	 * @param xAxisLabel New value of property xAxisLabel.
	 */
	public void setXaxislabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	/**
	 * Sets the y-axis label [tb]
	 *
	 * @param yAxisLabel New value of property yAxisLabel.
	 */
	public void setYaxislabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}
	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Gets the type.
	 * @return type of plot as a String
	 */
	public String getType() {
		return this.type;
	}

	public static void check(Dataset data, Class clazz, String plotTypeName) throws IncompatibleDatasetException {
		if (!clazz.isInstance(data)) {
			throw new IncompatibleDatasetException(
				"Plots of type " + plotTypeName + " need a dataset of type " + clazz.getName());
		}
	}
	
	/**
	 * Check if an object is a certain type. Throws a Runtime exception if not so.
	 * @param obj The object checked
	 * @param clazz The class to check against
	 * @param message The message displayed
	 */
	public static void checkType(Object obj, Class clazz, String message) {
		if (!clazz.isInstance(obj)) {
			throw new RuntimeException(message + ", error in type check, expected type:" + clazz.getName() +", but it was:" + obj.getClass().getName());
		}
	}

	public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache) {
		log.debug("setDataProductionConfig(" + dsp + ", " + params);
		dataAware.setDataProductionConfig(dsp, params, useCache);
	}

	/**
	 * Sets the drawingSupplier.
	 * @param drawingSupplier The drawingSupplier to set
	 */
	public void setDrawingSupplier(DrawingSupplier drawingSupplier) {
		this.drawingSupplier = drawingSupplier;
	}

}
