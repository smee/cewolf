package de.laures.cewolf.cpp;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.MeterPlot;

/**
* A postprocessor for changing details of a Meter plot.
* <BR><b>units</b> an arbitrary string to be displayed next to the numeric value; optional; default "Units"
* <BR><b>needleColor</b> optional; default #FFFFFF (i.e., white)
* <BR><b>backgroundColor</b> optional; default #000000 (i.e., black)
* <BR><b>valueColor</b> optional; default #FFFFFF (i.e., white)
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="thermometerEnhancer"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="units" value="km/h" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="needleColor" value="#336699" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="valueColor" value="#99AACC" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="backgroundColor" value="#CCCCCC" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

public class MeterEnhancer implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = 2850014117301156369L;

    public void processChart (Object chart, Map params) {
		Color valueColor = new Color(255, 255, 255);
		Color needleColor = new Color(255, 255, 255);
		Color backgroundColor = new Color(0, 0, 0);
		String units = "Units";

		String str = (String) params.get("needleColor");
		if (str != null && str.trim().length() > 0) {
			try {
				needleColor = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("valueColor");
		if (str != null && str.trim().length() > 0) {
			try {
				valueColor = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("backgroundColor");
		if (str != null && str.trim().length() > 0) {
			try {
				backgroundColor = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("units");
		if (str != null) {
			units = str;
		}

		Plot plot = ((JFreeChart) chart).getPlot();
		if (plot instanceof MeterPlot) {
			MeterPlot mplot = (MeterPlot) plot;
			mplot.setNeedlePaint(needleColor);
			mplot.setValuePaint(valueColor);
			mplot.setDialBackgroundPaint(backgroundColor);
			mplot.setUnits(units);
		}
	}
}
