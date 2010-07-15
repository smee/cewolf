package de.laures.cewolf.cpp;

import java.io.Serializable;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.CategoryDataset;

import de.laures.cewolf.ChartPostProcessor;

/**
* A postprocessor for rotating and/or removing the labels on the X-Axis. It supports the following parameters:
* <BR><b>rotate_at</b> make the labels vertical if this many categories are present; default 1
* <BR><b>remove_at</b> don't print any labels if this many categories are present; default 100
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="labelRotation"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="rotate_at" value="10"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="remove_at" value="100"/&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*
* @author Rich Unger
*/

public class RotatedAxisLabels implements ChartPostProcessor, Serializable {

	static final long serialVersionUID = 5242029033037971789L;

	public void processChart (Object chart, Map params) {
		int rotateThreshold=1, removeThreshold=100;

		String rotateParam = (String) params.get("rotate_at");
		if (rotateParam != null && rotateParam.trim().length() > 0) {
			try {
				rotateThreshold = Integer.parseInt(rotateParam);
			} catch (NumberFormatException nfex) { }
		}

		String removeParam = (String) params.get("remove_at");
		if (removeParam != null && removeParam.trim().length() > 0) {
			try {
				removeThreshold = Integer.parseInt(removeParam);
			} catch (NumberFormatException nfex) { }
		}

		Plot plot = ((JFreeChart) chart).getPlot();
		Axis axis = null;
		int numValues = 0;

		if (plot instanceof CategoryPlot) {
			axis = ((CategoryPlot) plot).getDomainAxis();
			numValues = ((CategoryPlot) plot).getDataset().getRowCount();
		} else if (plot instanceof XYPlot) {
			axis = ((XYPlot) plot).getDomainAxis();
			numValues = ((XYPlot) plot).getDataset().getItemCount(0);
		} else if (plot instanceof FastScatterPlot) {
			axis = ((FastScatterPlot) plot).getDomainAxis();
			numValues = ((FastScatterPlot) plot).getData()[0].length;
		}

		if (axis instanceof CategoryAxis) {
			CategoryAxis catAxis = (CategoryAxis) axis;

			if (rotateThreshold > 0) {
				if (numValues >= rotateThreshold) {
					catAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
				} else {
					catAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
				}
			}
		} else if (axis instanceof ValueAxis) {
			ValueAxis valueAxis = (ValueAxis) axis;

			if (rotateThreshold > 0) {
				valueAxis.setVerticalTickLabels(numValues >= rotateThreshold);
			}
		}

		if ((axis != null) && (removeThreshold > 0)) {
			axis.setTickLabelsVisible(numValues < removeThreshold);
		}
	}
}
