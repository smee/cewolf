package de.laures.cewolf.cpp;

import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;

import de.laures.cewolf.ChartPostProcessor;

/**
* A cewolf post-processor for rotating and/or removing the labels on the X-Axis
* parameters:
* rotate_at: make the labels vertical
* skip_at: print only some of the labels (so they don't overlap)
* remove_at: don't print any labels
*
* Usage:
* <chart:chartpostprocessor id="labelRotation">
* <chart:param name="rotate_at" value='<%= new Integer(10) %>'/>
* <chart:param name="skip_at" value='<%= new Integer(50) %>'/>
* <chart:param name="remove_at" value='<%= new Integer(100) %>'/>
* </chart:chartpostprocessor>
*
*
* @author Rich Unger
*/

public class RotatedAxisLabels implements ChartPostProcessor {
	
public void processChart(Object chart, Map params) {
		CategoryPlot plot = (CategoryPlot) ((JFreeChart) chart).getPlot();

		CategoryAxis axis = plot.getDomainAxis();

		Number rotateThreshold = (Number) params.get("rotate_at");
		Number skipThreshold = (Number) params.get("skip_at");
		Number removeThreshold = (Number) params.get("remove_at");

		CategoryDataset dataset = plot.getDataset();
		int iCategoryCount = dataset.getRowCount();

		if (rotateThreshold != null) 
    {
      if (iCategoryCount >= rotateThreshold.intValue()) 
      {
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
      }
      else 
      {
        axis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
      }

    }
    
		if (skipThreshold != null) 
    {
      //this method does nothing in jfreechart .9.18
			//axis.setSkipCategoryLabelsToFit(iCategoryCount >= skipThreshold.intValue());
		}
		
    if (removeThreshold != null) 
    {
			axis.setTickLabelsVisible(iCategoryCount < removeThreshold.intValue());
		}
	}
	
}
