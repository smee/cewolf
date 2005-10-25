/*
 * Created on 13.04.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.laures.cewolf.taglib.tags;

import de.laures.cewolf.taglib.AbstractChartDefinition;
import de.laures.cewolf.taglib.AxisTypes;
import de.laures.cewolf.taglib.OverlaidChartDefinition;
import de.laures.cewolf.taglib.PlotContainer;
import de.laures.cewolf.taglib.PlotDefinition;


/**
 * @author guido
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OverlaidChartTag extends AbstractChartTag implements PlotContainer {
	
    protected AbstractChartDefinition createChartDefinition() {
        return new OverlaidChartDefinition();
    }

	public void addPlot(PlotDefinition pd){
		((OverlaidChartDefinition)chartDefinition).addPlot(pd);
	}
    
	/**
	 * Sets the xAxisType.
	 * @param xAxisType The xAxisType to set
	 */
	public void setxaxistype(String xAxisType) {
        final int xAxisTypeConst = AxisTypes.typeList.indexOf(xAxisType);
		((OverlaidChartDefinition)chartDefinition).setXAxisType(xAxisTypeConst);
	}

	/**
	 * Sets the yAxisType.
	 * @param yAxisType The yAxisType to set
	 */
	public void setyaxistype(String yAxisType) {
        final int yAxisTypeConst = AxisTypes.typeList.indexOf(yAxisType);
        ((OverlaidChartDefinition)chartDefinition).setYAxisType(yAxisTypeConst);
	}

}
