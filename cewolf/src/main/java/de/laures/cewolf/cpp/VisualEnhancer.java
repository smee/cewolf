package de.laures.cewolf.cpp;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;
import org.jfree.ui.RectangleInsets;

/**
* A postprocessor for altering the border and padding of a chart. It supports the following parameters:
* <BR><b>border</b> true/false; optional, default true; whether or not a border is drawn around the chart
* <BR><b>borderpaint</b> optional; default #000000 (i.e., black); the color of the border
* <BR><b>top</b> optional; default 1; sets the top padding between the chart border and the chart drawing area
* <BR><b>left</b> optional; default 1; sets the left padding between the chart border and the chart drawing area
* <BR><b>right</b> optional; default 1; sets the right padding between the chart border and the chart drawing area
* <BR><b>bottom</b> optional; default 1; sets the bottom padding between the chart border and the chart drawing area
* <BR><b>plotTop</b> optional; default 4; sets the top padding of the plot
* <BR><b>plotLeft</b> optional; default 8; sets the left padding of the plot
* <BR><b>plotRight</b> optional; default 8; sets the right padding of the plot
* <BR><b>plotBottom</b> optional; default 4; sets the bottom padding of the plot
* <BR><b>rangeIncludesZero</b> true/false; optional; default true; whether the range (Y) axis always includes zero 
* <BR><b>showDomainAxes</b> true/false; optional; default true; whether or not to show any domain (X) axes
* <BR><b>showRangeAxes</b> true/false; optional; default true; whether or not to show any range (Y) axes
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="visualEnhance"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="border" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="borderpaint" value="#4488BB" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="top" value="5" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="left" value="5" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="right" value="5" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="bottom" value="5" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="plotTop" value="0" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="plotLeft" value="0" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="plotRight" value="0" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="plotBottom" value="0" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="rangeIncludesZero" value="false" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="showDomainAxes" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="showRangeAxes" value="true" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

public class VisualEnhancer implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = -4434675932582386052L;

    public void processChart (Object chart, Map params) {
		JFreeChart localChart = (JFreeChart) chart;
		Color borderPaint = new Color(0, 0, 0);
		boolean hasBorder = true;
		boolean rangeIncludesZero = true;
		boolean showDomainAxes = true;
		boolean showRangeAxes = true;
		double top = 1.0;
		double left = 1.0;
		double right = 1.0;
		double bottom = 1.0;
		double plotTop = 4.0;
		double plotLeft = 8.0;
		double plotRight = 8.0;
		double plotBottom = 4.0;

		String str = (String) params.get("top");
		if (str != null && str.trim().length() > 0) {
			try {
				top = Double.parseDouble(str);
				if (top < 0)
					top = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("left");
		if (str != null && str.trim().length() > 0) {
			try {
				left = Double.parseDouble(str);
				if (left < 0)
					left = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("right");
		if (str != null && str.trim().length() > 0) {
			try {
				right = Double.parseDouble(str);
				if (right < 0)
					right = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("bottom");
		if (str != null && str.trim().length() > 0) {
			try {
				bottom = Double.parseDouble(str);
				if (bottom < 0)
					bottom = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("plotTop");
		if (str != null && str.trim().length() > 0) {
			try {
				plotTop = Double.parseDouble(str);
				if (plotTop < 0)
					plotTop = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("plotLeft");
		if (str != null && str.trim().length() > 0) {
			try {
				plotLeft = Double.parseDouble(str);
				if (plotLeft < 0)
					plotLeft = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("plotRight");
		if (str != null && str.trim().length() > 0) {
			try {
				plotRight = Double.parseDouble(str);
				if (plotRight < 0)
					plotRight = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("plotBottom");
		if (str != null && str.trim().length() > 0) {
			try {
				plotBottom = Double.parseDouble(str);
				if (plotBottom < 0)
					plotBottom = 0.0;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("borderpaint");
		if (str != null && str.trim().length() > 0) {
			try {
				borderPaint = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("border");
		if (str != null)
			hasBorder = "true".equals(str.toLowerCase());

		str = (String) params.get("rangeIncludesZero");
		if (str != null)
			rangeIncludesZero = "true".equals(str.toLowerCase());

		str = (String) params.get("showDomainAxes");
		if (str != null)
			showDomainAxes = "true".equals(str.toLowerCase());

		str = (String) params.get("showRangeAxes");
		if (str != null)
			showRangeAxes = "true".equals(str.toLowerCase());

		localChart.setBorderVisible(hasBorder);
		localChart.setBorderPaint(borderPaint);
		localChart.setPadding(new RectangleInsets(top, left, bottom, right));

		Plot plot = localChart.getPlot();
		plot.setInsets(new RectangleInsets(plotTop, plotLeft, plotBottom, plotRight));

		if (plot instanceof XYPlot) {
			XYPlot xyPlot = (XYPlot) plot;
			ValueAxis axis = xyPlot.getRangeAxis();
			if (axis instanceof NumberAxis)
				((NumberAxis) axis).setAutoRangeIncludesZero(rangeIncludesZero);
			if (! showDomainAxes)
				xyPlot.clearDomainAxes();
			if (! showRangeAxes)
				xyPlot.clearRangeAxes();
		} else if (plot instanceof CategoryPlot) {
			CategoryPlot catPlot = (CategoryPlot) plot;
			ValueAxis axis = catPlot.getRangeAxis();
			if (axis instanceof NumberAxis)
				((NumberAxis) axis).setAutoRangeIncludesZero(rangeIncludesZero);
			if (! showDomainAxes)
				catPlot.clearDomainAxes();
			if (! showRangeAxes)
				catPlot.clearRangeAxes();
		}
	}
}

