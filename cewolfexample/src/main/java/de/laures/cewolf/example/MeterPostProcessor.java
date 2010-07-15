package de.laures.cewolf.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.data.Range;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.MeterInterval;

public class MeterPostProcessor implements ChartPostProcessor, Serializable
{
    public void processChart (Object chart, Map params) {
      MeterPlot plot = (MeterPlot) ((JFreeChart) chart).getPlot();

      double min = 0;
      double max = 260;
      double val = 86;
      double minCrit = 187;
      double maxCrit = max;
      double minWarn = 164;
      double maxWarn = minCrit;
      double maxNorm = minCrit;
      double minNorm = min;

      plot.setRange(new Range(min, max));
      plot.addInterval(new MeterInterval("Normal", new Range(minNorm, maxNorm),
										Color.green, new BasicStroke(2.0f), null));
      plot.addInterval(new MeterInterval("Warning", new Range(minWarn, maxWarn),
										Color.yellow, new BasicStroke(2.0f), null));
      plot.addInterval(new MeterInterval("Critical", new Range(minCrit, maxCrit),
										Color.red, new BasicStroke(2.0f), null));
    }
}
