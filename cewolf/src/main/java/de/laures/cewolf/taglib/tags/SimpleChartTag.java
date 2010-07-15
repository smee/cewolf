/*
 * Created on 13.04.2003
 */
package de.laures.cewolf.taglib.tags;

import java.util.Map;

import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.taglib.AbstractChartDefinition;
import de.laures.cewolf.taglib.DataAware;
import de.laures.cewolf.taglib.SimpleChartDefinition;

/**
 * @author guido
 */
public class SimpleChartTag extends AbstractChartTag implements DataAware {
    
	static final long serialVersionUID = -3313178284141986292L;

    protected AbstractChartDefinition createChartDefinition() {
        return new SimpleChartDefinition();
    }

     public void setDataProductionConfig (DatasetProducer dsp, Map params, boolean useCache) {
        ((SimpleChartDefinition) chartDefinition).setDataProductionConfig(dsp, params, useCache);
    }

}
