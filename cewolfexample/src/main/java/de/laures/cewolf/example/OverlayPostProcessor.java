package de.laures.cewolf.example;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;

public class OverlayPostProcessor implements ChartPostProcessor, Serializable
{
	public void processChart (Object jfc, Map params) {
		XYPlot plot = (XYPlot) ((JFreeChart) jfc).getPlot();
		// set different colors for each:
		Color[] colors = new Color[] { Color.BLACK, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY };
		for (int i = 0; i < 4; i++) {
			XYItemRenderer renderer = plot.getRenderer(i);
			if (renderer != null) {
				renderer.setSeriesPaint(i, colors[i]);
			}
		}
	}
}
