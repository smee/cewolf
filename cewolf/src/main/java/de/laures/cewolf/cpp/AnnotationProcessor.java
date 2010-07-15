package de.laures.cewolf.cpp;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.*;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.ui.TextAnchor;

/**
* A postprocessor for adding annotation to an X/Y or Category plot.
* If either arrowPaint or arrowAngle is set then a pointer will be drawn; otherwise, just the text.
* <BR><b>text</b> the text to display; mandatory
* <BR><b>x</b> (for X/Y plots only) the X value at which to show the text; mandatory
* <BR><b>y</b> (for X/Y plots only) the Y value at which to show the text; mandatory
* <BR><b>category</b> (for category plots only) the category for which to show the text; mandatory
* <BR><b>value</b> (for category plots only) the value at which to show the text; mandatory
* <BR><b>fontname</b> optional; default SansSerif
* <BR><b>fontsize</b> optional; default 14
* <BR><b>bold</b> true/false; optional; default false
* <BR><b>italic</b> true/false; optional; default false
* <BR><b>textPaint</b> the color to use for the text; optional; default #000000 (i.e., black)
* <BR><b>arrowPaint</b> the color to use for the text; optional; default #000000 (i.e., black)
* <BR><b>arrowAngle</b> the angle at which to display the arrow; optional; default 0
* <BR><b>textAnchor</b> the position of text relative to its origin point; optional; possible values are:
BASELINE_CENTER, BASELINE_LEFT, BASELINE_RIGHT, BOTTOM_CENTER, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, CENTER_LEFT,
CENTER_RIGHT, HALF_ASCENT_CENTER, HALF_ASCENT_LEFT, HALF_ASCENT_RIGHT, TOP_CENTER, TOP_LEFT, TOP_RIGHT 
* <P>
* See the annotations.jsp page of the sample web app for usage examples.
*/

public class AnnotationProcessor implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = 6321794363389448612L;

    public void processChart (Object chart, Map params) {
		String text = "text goes here";
		String fontName = "SansSerif";
		int fontSize = 14;
		boolean isBold = false;
		boolean isItalic = false;
		double x = 0.0;
		double y = 0.0;
		String category = null;
		double value = 0.0;
		TextAnchor textAnchor = TextAnnotation.DEFAULT_TEXT_ANCHOR;
		Color textPaint = new Color(0, 0, 0);
		Color arrowPaint = new Color(0, 0, 0);
		double arrowAngle = 0;
		boolean drawArrow = false;

		String str = (String) params.get("text");
		if (str != null && str.trim().length() > 0)
			text = str.trim();

		String fontNameParam = (String) params.get("fontname");
		if (fontNameParam != null && fontNameParam.trim().length() > 0)
			fontName = fontNameParam.trim();

		String fontSizeParam = (String) params.get("fontsize");
		if (fontSizeParam != null && fontSizeParam.trim().length() > 0) {
			try {
				fontSize = Integer.parseInt(fontSizeParam);
				if (fontSize < 4)
					fontSize = 14;
			} catch (NumberFormatException nfex) { }
		}

		String boldParam = (String) params.get("bold");
		if (boldParam != null)
			isBold = "true".equals(boldParam.toLowerCase());

		String italicParam = (String) params.get("italic");
		if (italicParam != null)
			isItalic = "true".equals(italicParam.toLowerCase());

		Font font = new Font(fontName,
							(isBold ? Font.BOLD : 0) + (isItalic ? Font.ITALIC : 0),
							fontSize);

		str = (String) params.get("x");
		if (str != null) {
			try {
				x = Double.parseDouble(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("y");
		if (str != null) {
			try {
				y = Double.parseDouble(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("category");
		if (str != null && str.trim().length() > 0)
			category = str.trim();

		str = (String) params.get("value");
		if (str != null) {
			try {
				value = Double.parseDouble(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("textPaint");
		if (str != null && str.trim().length() > 0) {
			try {
				textPaint = Color.decode(str);
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("arrowPaint");
		if (str != null && str.trim().length() > 0) {
			try {
				arrowPaint = Color.decode(str);
				drawArrow = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("arrowAngle");
		if (str != null) {
			try {
				arrowAngle = Double.parseDouble(str);
				drawArrow = true;
			} catch (NumberFormatException nfex) { }
		}

		str = (String) params.get("textAnchor");
		if (str != null) {
			if ("BASELINE_CENTER".equals(str))
				textAnchor = TextAnchor.BASELINE_CENTER;
			else if ("BASELINE_LEFT".equals(str))
				textAnchor = TextAnchor.BASELINE_LEFT;
			else if ("BASELINE_RIGHT".equals(str))
				textAnchor = TextAnchor.BASELINE_RIGHT;
			else if ("BOTTOM_CENTER".equals(str))
				textAnchor = TextAnchor.BOTTOM_CENTER;
			else if ("BOTTOM_LEFT".equals(str))
				textAnchor = TextAnchor.BOTTOM_LEFT;
			else if ("BOTTOM_RIGHT".equals(str))
				textAnchor = TextAnchor.BOTTOM_RIGHT;
			else if ("CENTER".equals(str))
				textAnchor = TextAnchor.CENTER;
			else if ("CENTER_LEFT".equals(str))
				textAnchor = TextAnchor.CENTER_LEFT;
			else if ("CENTER_RIGHT".equals(str))
				textAnchor = TextAnchor.CENTER_RIGHT;
			else if ("HALF_ASCENT_CENTER".equals(str))
				textAnchor = TextAnchor.HALF_ASCENT_CENTER;
			else if ("HALF_ASCENT_LEFT".equals(str))
				textAnchor = TextAnchor.HALF_ASCENT_LEFT;
			else if ("HALF_ASCENT_RIGHT".equals(str))
				textAnchor = TextAnchor.HALF_ASCENT_RIGHT;
			else if ("TOP_CENTER".equals(str))
				textAnchor = TextAnchor.TOP_CENTER;
			else if ("TOP_LEFT".equals(str))
				textAnchor = TextAnchor.TOP_LEFT;
			else if ("TOP_RIGHT".equals(str))
				textAnchor = TextAnchor.TOP_RIGHT; 
		}

		Plot plot = ((JFreeChart) chart).getPlot();
        if (plot instanceof XYPlot) {
			XYTextAnnotation anno = drawArrow
									? new XYPointerAnnotation(text, x, y, arrowAngle)
									: new XYTextAnnotation(text, x, y);
			anno.setPaint(textPaint);
			anno.setFont(font);
			anno.setTextAnchor(textAnchor);
			if (drawArrow) {
				((XYPointerAnnotation) anno).setArrowPaint(arrowPaint);
			}
			((XYPlot) plot).addAnnotation(anno);
        } else if (plot instanceof CategoryPlot) {
			CategoryTextAnnotation anno = drawArrow
									? new CategoryPointerAnnotation(text, category, value, arrowAngle)
									: new CategoryTextAnnotation(text, category, value);
			anno.setPaint(textPaint);
			anno.setFont(font);
			anno.setTextAnchor(textAnchor);
			if (drawArrow) {
				((CategoryPointerAnnotation) anno).setArrowPaint(arrowPaint);
			}
			((CategoryPlot) plot).addAnnotation(anno);
		}
	}
}
