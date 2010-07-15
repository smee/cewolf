package de.laures.cewolf.example;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.plot.CategoryPlot;

public class LegendItemPostProcessor implements ChartPostProcessor, Serializable
{
	public void processChart (Object chart, Map params) {
		CategoryPlot plot = (CategoryPlot) ((JFreeChart) chart).getPlot();
		LegendItemCollection liColl = plot.getLegendItems();

		for (int i = 0; i < liColl.getItemCount(); i++) {
			LegendItem li = liColl.get(i);
			String colorStr = (String) params.get(String.valueOf(i));
			li.setLabelPaint(Color.decode(colorStr));
		}
	}
}
