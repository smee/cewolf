package de.laures.cewolf.taglib.tags;

import de.laures.cewolf.taglib.AbstractChartDefinition;
import de.laures.cewolf.taglib.CombinedChartDefinition;
import de.laures.cewolf.taglib.PlotContainer;
import de.laures.cewolf.taglib.PlotDefinition;


/**
 * Chart tag subclass to handle combined charts
 *
 * @author guido
 * @author tbardzil
 *
 */
public class CombinedChartTag extends AbstractChartTag implements PlotContainer {

    protected AbstractChartDefinition createChartDefinition() {
        return new CombinedChartDefinition();
    }

	public void addPlot(PlotDefinition pd){
		((CombinedChartDefinition) chartDefinition).addPlot(pd);
	}

    /**
     * Setter for property layout [tb]
     * @param layout
     */
    public void setLayout(String layout) {
        ((CombinedChartDefinition) chartDefinition).setLayout(layout);
    }
}
