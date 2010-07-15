package de.laures.cewolf.cpp;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.CompassPlot;

/**
* A postprocessor for changing details of a Compass plot.
* <BR><b>drawBorder</b> boolean; default false
* <BR><b>needleType</b> arrow,line,long,pin,plum,pointer,ship,wind,middlepin; default arrow
* <BR><b>needleFill</b> optional; default #000000 (i.e., black)
* <BR><b>needleOutline</b> optional; default #000000 (i.e., black)
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="compassEnhancer"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="needleType" value="ship" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="drawBorder" value="false" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="needleFill" value="#336699" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="needleOutline" value="#99AACC" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

// possible further settings: rose paint, rose center paint, rose highlight paint

public class CompassEnhancer implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = -1392284687232625608L;

    public void processChart (Object chart, Map params) {
		String needleType = "arrow";
		boolean drawBorder = false;
		Color needleFill = new Color(0, 0, 0);
		Color needleOutline = new Color(0, 0, 0);

		String str = (String) params.get("needleType");
		if (str != null && str.trim().length() > 0)
			needleType = str.trim();

		str = (String) params.get("needleFill");
		if (str != null && str.trim().length() > 0) {
			try {
				needleFill = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("needleOutline");
		if (str != null && str.trim().length() > 0) {
			try {
				needleOutline = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("drawBorder");
		if (str != null)
			drawBorder = "true".equals(str);

		Plot plot = ((JFreeChart) chart).getPlot();
		if (plot instanceof CompassPlot) {
			CompassPlot cplot = (CompassPlot) plot;

			cplot.setDrawBorder(drawBorder);

			if ("line".equals(needleType)) {
				cplot.setSeriesNeedle(1);
			} else if ("long".equals(needleType)) {
				cplot.setSeriesNeedle(2);
			} else if ("pin".equals(needleType)) {
				cplot.setSeriesNeedle(3);
			} else if ("plum".equals(needleType)) {
				cplot.setSeriesNeedle(4);
			} else if ("pointer".equals(needleType)) {
				cplot.setSeriesNeedle(5);
			} else if ("ship".equals(needleType)) {
				cplot.setSeriesNeedle(6);
			} else if ("wind".equals(needleType)) {
				cplot.setSeriesNeedle(7);
			} else if ("arrow".equals(needleType)) {
				cplot.setSeriesNeedle(8);
			} else if ("middlepin".equals(needleType)) {
				cplot.setSeriesNeedle(9);
			}

			cplot.setSeriesPaint(0, needleFill);
			cplot.setSeriesOutlinePaint(0, needleOutline);
		}
	}
}
