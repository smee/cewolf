/* ================================================================
 * Cewolf : Chart enabling Web Objects Framework
 * ================================================================
 *
 * Project Info:  http://cewolf.sourceforge.net
 * Project Lead:  Guido Laures (guido@laures.de);
 *
 * (C) Copyright 2002, by Guido Laures
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
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

import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.HighLowRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;

/**
 * Contains the list of all possible plot type string values which can be used
 * in the <code>type</code> attribute of a &lt;chart&gt; tag.
 *
 * It also contains all the renders that correspond with the plot types
 * @author  Chris McCann
 */
public abstract class PlotTypes {

	// map contains (String) plot-type->PlotTypes instance mapping 
	private static Map plotTypes = new HashMap();
	
	// register all types
	static {
		registerRenderer("xyarea",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new XYAreaRenderer();
			}}
		);
		registerRenderer("xyline",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new StandardXYItemRenderer();
			}}
		);
		registerRenderer("xyshapesandlines",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES);
			}}
		);
		registerRenderer("scatter",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES);
			}});
		registerRenderer("xyverticalbar",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new XYBarRenderer();
			}});
		registerRenderer("step",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new XYStepRenderer();
			}});
		registerRenderer("candlestick",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new CandlestickRenderer();
			}});
		registerRenderer("highlow", new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new HighLowRenderer();
			}});
		/*
		registerRenderer("signal", new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new SignalRenderer();
			}});
		*/
		registerRenderer("verticalbar",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new BarRenderer();
			}});
		registerRenderer("area",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new AreaRenderer();
			}});
		registerRenderer("line",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new LineAndShapeRenderer(true,false);
			}});
		registerRenderer("shapesandlines",new PlotTypes() {
			public AbstractRenderer getRenderer() {
				return new LineAndShapeRenderer(true,true);
			}}); 				
		
	}
	
	protected PlotTypes() {
	}
	
	/**
	 * Get the renderer for the plot type.
	 * @return The renderer instance
	 */
	public abstract AbstractRenderer getRenderer();

	/**
	 * Register a new renderer for a plot type.
	 * @param typeName The name of the type.
	 * @param plotType The plot-type implementation class
	 */
	public static void registerRenderer(String typeName, PlotTypes plotType) {
		plotTypes.put(typeName.toLowerCase(), plotType);
	}
	
	/**
	 * Get a Plot-type factory
	 * @param typeName The name of the type
	 * @return The plot type, or null if not found
	 */
	public static PlotTypes getPlotType (String typeName) {
		return (PlotTypes) plotTypes.get(typeName.toLowerCase());
	}

	/**
	 * Get the renderer for a plot type.
	 * @param typeName The name of the type
	 * @return The renderer
	 * @throws UnsupportedChartTypeException if the type not found 
	 */
	public static AbstractRenderer getRenderer(String typeName) throws UnsupportedChartTypeException {
		PlotTypes type = getPlotType(typeName);
		if (type != null) {
			return type.getRenderer();
		} else {
			throw new UnsupportedChartTypeException(typeName);
		}
	}
		    
}
