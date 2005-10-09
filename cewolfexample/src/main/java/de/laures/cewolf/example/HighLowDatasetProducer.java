package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import demo.HighLowChartDemo1;

/**
 * @author glaures
 */
public class HighLowDatasetProducer implements DatasetProducer, Serializable {

    /**
     * @see de.laures.cewolf.DatasetProducer#produceDataset(Map)
     */
    public Object produceDataset(Map params) throws DatasetProduceException {
        //return DemoDatasetFactory.createHighLowDataset();
    	return HighLowChartDemo1.createDataset();
    }

    /**
     * @see de.laures.cewolf.DatasetProducer#hasExpired(Map, Date)
     */
    public boolean hasExpired(Map params, Date since) {
        return false;
    }

    /**
     * @see de.laures.cewolf.DatasetProducer#getProducerId()
     */
    public String getProducerId() {
        return getClass().getName();
    }

}
