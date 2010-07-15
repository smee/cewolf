package de.laures.cewolf.cpp;

import java.io.Serializable;
import java.util.*;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;

/**
* A postprocessor for changing details of a Pie plot.
* <BR><b>interiorGap</b> gap between plot and exterior boundary; default is 0.05, which means 5%
* <BR><b>labelGap</b> gap between the edge of the pie and the labels, expressed as a percentage of the plot width.
* <BR><b>startAngle</b> angle at which to start drawing; default is 0
* <BR><b>simpleLabels</b> whether or not to use simple labels on the pie sections; default is false, which means
* to move the labels away from the chart, and to draw a line to the associated pie section
* <BR><b>explode_</b> breaks a particular section of the pie out of it; the distance it is moved out is appended
* to the "explode_" as a percentage, e.g. "explode_0.25"; the value being passed is the name of the section.
* More than one section can be broken out, but since there can be no two parameters with the same name,
* slightly different percentages must be used; see the usage example below. Note that 3D pie charts do not support
* exploded sections.
* <P>
* <b>Usage:</b><P>
* &lt;chart:chartpostprocessor id="pieEnhancer"&gt;<BR>
* &nbsp;&nbsp;&nbsp;&nbsp;&lt;chart:param name="interiorGap" value="0.25" /&gt;<BR>
* &nbsp;&nbsp;&nbsp;&nbsp;&lt;chart:param name="labelGap" value="0.05" /&gt;<BR>
* &nbsp;&nbsp;&nbsp;&nbsp;&lt;chart:param name="startAngle" value="30" /&gt;<BR>
* &nbsp;&nbsp;&nbsp;&nbsp;&lt;chart:param name="simpleLabels" value="true" /&gt;<BR>
* &nbsp;&nbsp;&nbsp;&nbsp;&lt;chart:param name="explode_0.25" value="apples" /&gt;<BR>
* &nbsp;&nbsp;&nbsp;&nbsp;&lt;chart:param name="explode_0.26" value="bananas" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

public class PieEnhancer implements ChartPostProcessor, Serializable
{
	private static final long serialVersionUID = 2295977907208852725L;

    public void processChart (Object chart, Map params) {
		Plot plot = ((JFreeChart) chart).getPlot();

		if (plot instanceof PiePlot) {
			PiePlot piePlot = (PiePlot) plot;

			double interiorGap = piePlot.getInteriorGap();
			double labelGap = piePlot.getLabelGap();
			double startAngle = piePlot.getStartAngle();
			boolean simpleLabels = piePlot.getSimpleLabels();

			String str = (String) params.get("interiorGap");
			if (str != null) {
				try {
					interiorGap = Double.parseDouble(str);
				} catch (NumberFormatException nfex) { }
			}

			str = (String) params.get("labelGap");
			if (str != null) {
				try {
					labelGap = Double.parseDouble(str);
				} catch (NumberFormatException nfex) { }
			}

			str = (String) params.get("startAngle");
			if (str != null) {
				try {
					startAngle = Double.parseDouble(str);
				} catch (NumberFormatException nfex) { }
			}

			str = (String) params.get("simpleLabels");
			if (str != null)
				simpleLabels = "true".equals(str);

			piePlot.setSimpleLabels(simpleLabels);
			piePlot.setInteriorGap(interiorGap);
			piePlot.setLabelGap(labelGap);
			piePlot.setStartAngle(startAngle);

			// The following section deals with whether individual pie sections are exploded or not.
			// Although PiePlot3D extends PiePlot, it does not support exploded sections.
			if (! (plot instanceof PiePlot3D)) {
				PieDataset ds = piePlot.getDataset();

				for (Iterator paramIter=params.entrySet().iterator(); paramIter.hasNext(); ) {
					Map.Entry entry = (Map.Entry) paramIter.next();
					String paramKey = (String) entry.getKey();
					if (paramKey.startsWith("explode_")) {
						double explodePercent = Double.valueOf(paramKey.substring(8)).doubleValue();
						String paramValue = (String) entry.getValue();

						for (Iterator keyIter=ds.getKeys().iterator(); keyIter.hasNext(); ) {
							Comparable key = (Comparable) keyIter.next();
							if (key.equals(paramValue)) {
								piePlot.setExplodePercent(key, explodePercent);
							}
						}
					}
				}
			}
		}
	}
}
