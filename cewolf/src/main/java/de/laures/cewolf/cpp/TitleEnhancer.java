package de.laures.cewolf.cpp;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.HorizontalAlignment;

/**
* A postprocessor for setting a (sub)title on a chart. It supports the following parameters:
* <BR><b>type</b> title/subtitle; default is title
* <BR><b>title</b> no default, title won't be set if empty
* <BR><b>fontname</b> optional; default SansSerif
* <BR><b>fontsize</b> optional; default 18 for titles, 12 for subtitles
* <BR><b>paint</b> optional; default #000000 (i.e., black)
* <BR><b>backgroundpaint</b> optional; default #FFFFFF (i.e., white)
* <BR><b>bold</b> true/false; optional; default true
* <BR><b>italic</b> true/false; optional; default false
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="subTitle"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="type" value="title" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="title" value="My Important Title" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="fontname" value="Serif" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="fontsize" value="24" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="paint" value="#FF8800" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="backgroundpaint" value="#0088FF" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="bold" value="false" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="italic" value="true" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
* <P>
* Based on the ExtraTitleEnhancer class from the cewolfexample web app.
*/

public class TitleEnhancer implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = 591686288142936677L;

    public void processChart (Object chart, Map params) {
		JFreeChart localChart = (JFreeChart) chart;
		String title = null;
		String type = "title";
		String fontName = "SansSerif";
		Color paint = null;
		Color backgroundPaint = null;
		int fontSize = 18;
		boolean isBold = true;
		boolean isItalic = false;
//		HorizontalAlignment align = HorizontalAlignment.CENTER;

		String typeParam = (String) params.get("type");
		if (typeParam != null && typeParam.trim().length() > 0)
			type = typeParam.trim();

		String titleParam = (String) params.get("title");
		if (titleParam != null && titleParam.trim().length() > 0)
			title = titleParam.trim();

		String fontNameParam = (String) params.get("fontname");
		if (fontNameParam != null && fontNameParam.trim().length() > 0)
			fontName = fontNameParam.trim();

		String fontSizeParam = (String) params.get("fontsize");
		if (fontSizeParam != null && fontSizeParam.trim().length() > 0) {
			try {
				fontSize = Integer.parseInt(fontSizeParam);
				if (fontSize < 1)
					fontSize = 18;
			} catch (NumberFormatException nfex) { }
		}

		String paintParam = (String) params.get("paint");
		if (paintParam != null && paintParam.trim().length() > 0) {
			try {
				paint = Color.decode(paintParam);
			} catch (NumberFormatException nfex) { }
		}

		String backgroundpaintParam = (String) params.get("backgroundpaint");
		if (backgroundpaintParam != null && backgroundpaintParam.trim().length() > 0) {
			try {
				backgroundPaint = Color.decode(backgroundpaintParam);
			} catch (NumberFormatException nfex) { }
		}

		String boldParam = (String) params.get("bold");
		if (boldParam != null)
			isBold = "true".equals(boldParam.toLowerCase());

		String italicParam = (String) params.get("italic");
		if (italicParam != null)
			isItalic = "true".equals(italicParam.toLowerCase());
/*
		String alignParam = (String) params.get("align");
		if (alignParam != null) {
			if ("left".equals(alignParam))
				align = HorizontalAlignment.LEFT;
			else if ("right".equals(alignParam))
				align = HorizontalAlignment.RIGHT;
		}
*/
		if (title != null || "title".equals(type)) {
			TextTitle tt = null;
			if ("subtitle".equals(type)) {
				// search for subtitle
				List subTitles = localChart.getSubtitles();
				Iterator iter = subTitles.iterator();
				while (iter.hasNext()) {
					Object o = iter.next();
					if (o instanceof TextTitle) {
						tt = (TextTitle) o;
						break;
					}
				}

				if (tt == null) {
					tt = new TextTitle(title);
					localChart.addSubtitle(tt);
				}
			} else {
				tt = localChart.getTitle();
			}

			Font font = new Font(fontName,
								(isBold ? Font.BOLD : 0) + (isItalic ? Font.ITALIC : 0),
								fontSize);
			tt.setFont(font);
			if (paint != null)
				tt.setPaint(paint);
			if (backgroundPaint != null)
				tt.setBackgroundPaint(backgroundPaint);
		}
	}
}
