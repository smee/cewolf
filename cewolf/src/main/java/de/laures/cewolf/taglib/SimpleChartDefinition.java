/*
 * Created on 13.04.2003
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
 */
public class SimpleChartDefinition extends AbstractChartDefinition implements DataAware {

	static final long serialVersionUID = -1330286307731143710L;

	private DataContainer dataAware = new DataContainer();

    protected JFreeChart produceChart() throws DatasetProduceException, ChartValidationException {
    	return CewolfChartFactory.getChartInstance(type, title, xAxisLabel, yAxisLabel, getDataset(), showLegend);
    }

    public Dataset getDataset() throws DatasetProduceException {
        return dataAware.getDataset();
    }

    public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache) {
    	dataAware.setDataProductionConfig(dsp, params, useCache);
    }
}
