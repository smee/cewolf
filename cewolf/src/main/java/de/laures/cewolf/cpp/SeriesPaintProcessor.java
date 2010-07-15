package de.laures.cewolf.cpp;

import java.awt.Color;
import java.io.Serializable;
import java.util.*;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
* A postprocessor for setting alternative colors for pie charts, category plots, XY plots and spider web plots.
* It takes numbered parameters containing the hex color values.
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="seriesPaint"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="0" value="#FFFFAA" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="1" value="#AAFFAA" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="2" value="#FFAAFF" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="3" value="#FFAAAA" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

public class SeriesPaintProcessor implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = -2290498142826058256L;

    public void processChart (Object chart, Map params) {
		JFreeChart localChart = (JFreeChart) chart;
        Plot plot = (Plot) localChart.getPlot();

		// pie charts
		if (plot instanceof PiePlot) {
			PiePlot piePlot = (PiePlot) plot;

			List keys = piePlot.getDataset().getKeys();
			for (int i=0; i<params.size(); i++) {
				String colorStr = (String) params.get(String.valueOf(i));
				piePlot.setSectionPaint((Comparable) keys.get(i), Color.decode(colorStr));
			}

		// category plots
		} else if (plot instanceof CategoryPlot) {
			CategoryItemRenderer render = ((CategoryPlot) plot).getRenderer();

			for (int i=0; i<params.size(); i++) {
				String colorStr = (String) params.get(String.valueOf(i));
				render.setSeriesPaint(i, Color.decode(colorStr));
			}

		// spider web plots
		} else if (plot instanceof SpiderWebPlot) {
			SpiderWebPlot swPlot = (SpiderWebPlot) plot;

			for (int i=0; i<params.size(); i++) {
				String colorStr = (String) params.get(String.valueOf(i));
				swPlot.setSeriesPaint(i, Color.decode(colorStr));
			}

		// XY plots
		} else if (plot instanceof XYPlot) {
			XYItemRenderer render = ((XYPlot) plot).getRenderer();

			for (int i=0; i<params.size(); i++) {
				String colorStr = (String) params.get(String.valueOf(i));
				render.setSeriesPaint(i, Color.decode(colorStr));
			}
		}
	}
}

