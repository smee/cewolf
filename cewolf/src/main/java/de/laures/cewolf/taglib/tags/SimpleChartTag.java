/*
 * Created on 13.04.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.laures.cewolf.taglib.tags;

import java.util.Map;

import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.taglib.AbstractChartDefinition;
import de.laures.cewolf.taglib.DataAware;
import de.laures.cewolf.taglib.SimpleChartDefinition;

/**
 * @author guido
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SimpleChartTag extends AbstractChartTag implements DataAware{
    
    protected AbstractChartDefinition createChartDefinition() {
        return new SimpleChartDefinition();
    }

     public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache) {
        ((SimpleChartDefinition)chartDefinition).setDataProductionConfig(dsp, params, useCache);
    }

}
