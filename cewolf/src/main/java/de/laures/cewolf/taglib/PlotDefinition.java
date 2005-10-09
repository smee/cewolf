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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.SignalsDataset;
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
public class PlotDefinition implements DataAware, Serializable, TaglibConstants, PlotConstants {

	private transient Log log = LogFactory.getLog(getClass());

	private String type;
	private String xAxisLabel; // [tb]
	private String yAxisLabel; // [tb]

	private DataContainer dataAware = new DataContainer();
	private transient Plot plot;
	private transient DrawingSupplier drawingSupplier = null;

	public Plot getPlot(int chartType) throws DatasetProduceException, ChartValidationException {
    log.debug("Plot.getPlot: chartType: " + chartType);
		if (plot == null) {
			int rendererIndex = PlotTypes.getRendererIndex(type);
			
			Dataset data = (Dataset) getDataset();
			log.debug("Plot.getPlot: data name: " +data.getClass().getName());
			AbstractRenderer rend = PlotTypes.getRenderer(rendererIndex);
			log.debug("Plot.getPlot: rendererIndex: " + rendererIndex);
			if (chartType == ChartConstants.OVERLAY_XY || chartType == ChartConstants.COMBINED_XY) {
				switch (rendererIndex) {
					case XY_AREA :
					case XY_LINE :
					case XY_SHAPES_AND_LINES :
					case SCATTER :
					case STEP :
						check(data, XYDataset.class, rendererIndex);
						plot = new XYPlot((XYDataset) data, null, null, (XYItemRenderer) rend);
						break;
					case XY_VERTICAL_BAR :
						check(data, IntervalXYDataset.class, rendererIndex);
						plot = new XYPlot((IntervalXYDataset) data, null, null, (XYItemRenderer) rend);
						break;
					case CANDLESTICK :
					case HIGH_LOW :
						check(data, OHLCDataset.class, rendererIndex);
						plot = new XYPlot((OHLCDataset) data, null, null, (XYItemRenderer) rend);
						break;
					case SIGNAL :
						check(data, SignalsDataset.class, rendererIndex);
						plot = new XYPlot((SignalsDataset) data, null, null, (XYItemRenderer) rend);
					default :
						throw new AttributeValidationException(chartType + ".type", type);
				}
			} else if (chartType == ChartConstants.OVERLAY_CATEGORY) {
				switch (rendererIndex) {
					case AREA :
					case VERTICAL_BAR :
					case LINE :
					case SHAPES_AND_LINES :
						check(data, CategoryDataset.class, rendererIndex);
						plot =
							new CategoryPlot(
								(CategoryDataset) data,
								null,
								null,
								(CategoryItemRenderer) rend);
						break;
					default :
						throw new AttributeValidationException(chartType + ".type", type);
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

	public void check(Dataset data, Class clazz, int plotType) throws IncompatibleDatasetException {
		if (!clazz.isInstance(data)) {
			throw new IncompatibleDatasetException(
				"Plots of type " + PlotTypes.typeNames[plotType] + " need a dataset of type " + clazz.getName());
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
