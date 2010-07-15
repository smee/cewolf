package de.laures.cewolf.cpp;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.SpiderWebPlot;

import org.jfree.util.Rotation;
import org.jfree.util.TableOrder;

/**
* A postprocessor for changing details of a Meter plot.
* <BR><b>interiorGap</b> gap between plot and exterior boundary; default is 0.25, which means 25%
* <BR><b>headPercent</b> size of the actual dat points; default is 0.01, which means 1% of the plot size
* <BR><b>startAngle</b> angle at which to start drawing; default is 0
* <BR><b>webFilled</b> whether to fill the web with color, or just paint the outline; default is true
* <BR><b>clockWise</b> in which direction to rotate the data
* <BR><b>orderByRow</b> whether to interpret the data by rows or by columns
* <BR><b>labelPaint</b> the color of the labels; optional; default #000000 (i.e., black)
* <BR><b>labelFontName</b> font type of the labels; optional; default SansSerif
* <BR><b>labelFontSize</b> font size of the labels; optional; default 10
* <BR><b>labelBold</b> for the font type of the labels; true/false; optional; default false
* <BR><b>labelItalic</b> for the font type of the labels; true/false; optional; default false
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="thermometerEnhancer"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="interiorGap" value="0.25" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="headPercent" value="0.01" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="startAngle" value="30" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="webFilled" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="clockWise" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="orderByRow" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="labelPaint" value="#FFFFAA" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="labelFontName" value="Serif" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="labelFontSize" value="14" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="labelBold" value="false" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="labelItalic" value="true" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

public class SpiderWebEnhancer implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = 3693171934091003109L;

    public void processChart (Object chart, Map params) {

		double interiorGap = 0.25;
		double headPercent = 0.01;
		double startAngle = 0.0;
		boolean webFilled = true;
		boolean clockWise = false;
		boolean orderByRow = true;
		Color labelPaint = new Color(0, 0, 0);
		String labelFontName = "SansSerif";
		int labelFontSize = 10;
		boolean isBold = false;
		boolean isItalic = false;

		String str = (String) params.get("interiorGap");
		if (str != null) {
			try {
				interiorGap = Double.parseDouble(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("headPercent");
		if (str != null) {
			try {
				headPercent = Double.parseDouble(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("startAngle");
		if (str != null) {
			try {
				startAngle = Double.parseDouble(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("webFilled");
		if (str != null)
			webFilled = "true".equals(str);

		str = (String) params.get("clockWise");
		if (str != null)
			clockWise = "true".equals(str);

		str = (String) params.get("orderByRow");
		if (str != null)
			orderByRow = "true".equals(str);

		str = (String) params.get("labelPaint");
		if (str != null && str.trim().length() > 0) {
			try {
				labelPaint = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("labelFontName");
		if (str != null && str.trim().length() > 0)
			labelFontName = str.trim();

		str = (String) params.get("labelFontSize");
		if (str != null && str.trim().length() > 0) {
			try {
				labelFontSize = Integer.parseInt(str);
				if (labelFontSize < 1)
					labelFontSize = 10;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("labelBold");
		if (str != null)
			isBold = "true".equals(str.toLowerCase());

		str = (String) params.get("labelItalic");
		if (str != null)
			isItalic = "true".equals(str.toLowerCase());

		Plot plot = ((JFreeChart) chart).getPlot();
		if (plot instanceof SpiderWebPlot) {
			SpiderWebPlot swplot = (SpiderWebPlot) plot;
			swplot.setStartAngle(startAngle);
			swplot.setInteriorGap(interiorGap);
			swplot.setHeadPercent(headPercent);
			swplot.setWebFilled(webFilled);
			swplot.setLabelPaint(labelPaint);

			Font font = new Font(labelFontName,
								(isBold ? Font.BOLD : 0) + (isItalic ? Font.ITALIC : 0),
								labelFontSize);
			swplot.setLabelFont(font);

			if (clockWise)
				swplot.setDirection(Rotation.CLOCKWISE);
			else
				swplot.setDirection(Rotation.ANTICLOCKWISE);

			if (orderByRow)
				swplot.setDataExtractOrder(TableOrder.BY_ROW);
			else
				swplot.setDataExtractOrder(TableOrder.BY_COLUMN);
		}
	}
}
