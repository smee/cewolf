package de.laures.cewolf.dp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.jfree.data.general.Dataset;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.XYDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * @author guido
 */
public class MovingAverageDatasetProducer implements DatasetProducer, Serializable {

	static final long serialVersionUID = -240013835826772031L;

	/**
	 * @see de.laures.cewolf.DatasetProducer#produceDataset(Map)
	 */
	public Object produceDataset (Map params) throws DatasetProduceException {
		DatasetProducer datasetProducer = (DatasetProducer) params.get("producer");
		Dataset dataset = (Dataset) datasetProducer.produceDataset(params);
		String suffix = (String) params.get("suffix");
		int period = Integer.parseInt((String) params.get("period"));
		int skip = Integer.parseInt((String) params.get("skip"));
		if (dataset instanceof XYDataset){
	        return MovingAverage.createMovingAverage((XYDataset)dataset, suffix, period, skip);
		} else {
			throw new DatasetProduceException("moving average only supported for XYDatasets");
		}
	}

	/**
	 * @see de.laures.cewolf.DatasetProducer#hasExpired(Map, Date)
	 */
	public boolean hasExpired (Map params, Date since) {
		return true;
	}

	/**
	 * @see de.laures.cewolf.DatasetProducer#getProducerId()
	 */
	public String getProducerId() {
		return getClass().getName();
	}
}
