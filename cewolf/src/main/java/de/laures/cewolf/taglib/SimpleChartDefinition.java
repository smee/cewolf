/*
 * Created on 13.04.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.laures.cewolf.taglib;

import java.io.Serializable;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * @author guido
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SimpleChartDefinition extends AbstractChartDefinition implements DataAware, Serializable {

	private DataContainer dataAware = new DataContainer();
	
    protected JFreeChart produceChart() throws DatasetProduceException, ChartValidationException {
    	return CewolfChartFactory.getChartInstance(type, title, xAxisLabel, yAxisLabel, (Dataset)getDataset());
    }

    public Object getDataset() throws DatasetProduceException {
        return dataAware.getDataset();
    }

    public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache) {
    	dataAware.setDataProductionConfig(dsp, params, useCache);
    }

    

}
