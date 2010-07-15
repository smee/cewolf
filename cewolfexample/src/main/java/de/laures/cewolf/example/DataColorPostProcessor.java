package de.laures.cewolf.example;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;

public class DataColorPostProcessor implements ChartPostProcessor, Serializable
{
    public void processChart (Object chart, Map params) {
      CategoryPlot plot = (CategoryPlot) ((JFreeChart) chart).getPlot();
      for (int i = 0; i < params.size(); i++) {
        String colorStr = (String) params.get(String.valueOf(i));
        plot.getRenderer().setSeriesPaint(i, Color.decode(colorStr));
      }
    }
}
