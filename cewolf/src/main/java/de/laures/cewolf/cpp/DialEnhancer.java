package de.laures.cewolf.cpp;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.dial.*;

/**
* A postprocessor for changing details of a Dial plot.
* <BR><b>pointerType</b> "pin" or "pointer"; default pointer
* <BR><b>dialText</b> text to display on the dial; optional
* <BR><b>lowerBound</b> optional; default 0; starting value for the scale
* <BR><b>upperBound</b> optional; default 100; end value for the scale
* <BR><b>majorTickIncrement</b> optional; default 10; value increment between major tick marks
* <BR><b>minorTickCount</b> optional; default 4; minor tick marks to put between major tick marks
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="dialEnhancer"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="pointerType" value="pin"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="dialText" value="(km/h)"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="lowerBound" value="0.1"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="upperBound" value="0.1"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="majorTickIncrement" value="20"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="minorTickCount" value="9"/&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

// TODO: capFill, capRadius and capOutline don't work yet
/*
* <BR><b>capFill</b> optional; default #000000 (i.e., black)
* <BR><b>capOutline</b> optional; default #FFFFFF (i.e., white)
* <BR><b>capRadius</b> optional; 0.0 &lt; radius &lt; 1.0; default 0.05

* &nbsp;&nbsp;&lt;chart:param name="capFill" value="#FF8800"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="capOutline" value="#0088FF"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="capRadius" value="0.1"/&gt;<BR>
*/

public class DialEnhancer implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = 6708371054518325470L;

    public void processChart (Object chart, Map params) {
		String pointerType = "pointer";
		String dialText = null;

		Color capFillPaint = new Color(0, 0, 0);
		Color capOutlinePaint = new Color(255, 255, 255);
		double capRadius = 0.05;
		boolean setCap = false;

		double lowerBound = 0.0;
		double upperBound = 100.0;
		double majorTickIncrement = 10.0;
		int minorTickCount = 4;
		boolean setScale = false;

		String str = (String) params.get("pointerType");
		if (str != null && str.trim().length() > 0)
			pointerType = str.trim();

		str = (String) params.get("dialText");
		if (str != null && str.trim().length() > 0)
			dialText = str.trim();

		str = (String) params.get("capRadius");
		if (str != null) {
			try {
				capRadius = Double.parseDouble(str);
				setCap = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("capFill");
		if (str != null && str.trim().length() > 0) {
			try {
				capFillPaint = Color.decode(str);
				setCap = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("capOutline");
		if (str != null && str.trim().length() > 0) {
			try {
				capOutlinePaint = Color.decode(str);
				setCap = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("lowerBound");
		if (str != null) {
			try {
				lowerBound = Double.parseDouble(str);
				setScale = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("upperBound");
		if (str != null) {
			try {
				upperBound = Double.parseDouble(str);
				setScale = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("majorTickIncrement");
		if (str != null) {
			try {
				majorTickIncrement = Double.parseDouble(str);
				setScale = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("minorTickCount");
		if (str != null) {
			try {
				minorTickCount = Integer.parseInt(str);
				setScale = true;
			} catch (NumberFormatException nfex) { }
		}

		Plot plot = ((JFreeChart) chart).getPlot();
		if (plot instanceof DialPlot) {
			DialPlot dplot = (DialPlot) plot;

			if ("pin".equals(pointerType)) {
				dplot.removePointer(0);
				dplot.addPointer(new DialPointer.Pin());
			} else if ("pointer".equals(pointerType)) {
				dplot.removePointer(0);
				dplot.addPointer(new DialPointer.Pointer());
			}

			if (setCap) {
				DialCap cap = new DialCap();
				cap.setRadius(capRadius);
				cap.setFillPaint(capFillPaint);
				cap.setOutlinePaint(capOutlinePaint);
				dplot.setCap(cap);
			}

			if (setScale) {
				StandardDialScale scale = (StandardDialScale) dplot.getScale(0);
				scale.setLowerBound(lowerBound);
				scale.setUpperBound(upperBound);
				scale.setMajorTickIncrement(majorTickIncrement);
				scale.setMinorTickCount(minorTickCount);
			}

			if (dialText != null) {
				DialTextAnnotation annotation = new DialTextAnnotation(dialText);
				annotation.setFont(new Font("Dialog", Font.BOLD, 10));
				annotation.setRadius(0.35);
				annotation.setAngle(90.0);
				dplot.addLayer(annotation);
			}
		}
	}
}
