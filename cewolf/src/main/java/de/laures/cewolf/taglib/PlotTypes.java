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

import java.util.Arrays;
import java.util.List;

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
public class PlotTypes {

	/** All type strings in an array */
	public static final String[] typeNames =
		{
			"xyarea",
			"xyline",
			"xyshapesandlines",
			"scatter",
			"xyverticalbar",
			"step",
			"candlestick",
			"highlow",
			"signal",
			"verticalbar",
			"area",
			"line",
			"shapesandlines" };

	/**
	 * The whole typeNames array inside of a list.
	 * @see #typeNames
	 */
	private static final List typeList = Arrays.asList(typeNames);
	
	/**
	 * Create a renderer for the given type index.
	 * We create a new renderer instance for each chart, because they may want to customize
	 * it in a post-processor.
	 * 
	 * @param idx The index of the type
	 * @return A new renderer instance
	 */
	public static AbstractRenderer getRenderer(int idx) {
		switch (idx) {
			case 0: return new XYAreaRenderer();
			case 1: return new StandardXYItemRenderer();
			case 2: return new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES);
			case 3: return new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES);
			case 4: return new XYBarRenderer();
			case 5: return new XYStepRenderer();
			case 6: return new CandlestickRenderer();
			case 7: return new HighLowRenderer();
			//case 8: return new SignalRenderer();
			case 9: return new BarRenderer();
			case 10: return new AreaRenderer();
			case 11: return new LineAndShapeRenderer(true,false);
			case 12: return new LineAndShapeRenderer(true,true);
			default:
				throw new RuntimeException("Invalid renderer index:" + idx);
		}		
	}

	private PlotTypes() {
	}

    /**
     * Get the renderer index for the given plot type.
     * @param plotType The type string of the plot
     * @return The index The index of renderer
     * @throws AttributeValidationException if unknown type
     */
    public static int getRendererIndex(String plotType) throws AttributeValidationException  {
        int rendererIndex = PlotTypes.typeList.indexOf(plotType.toLowerCase());
        if (rendererIndex < 0) {
          throw new AttributeValidationException("plot.type", plotType);
        }
        return rendererIndex;
    }

}
