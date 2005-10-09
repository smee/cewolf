package de.laures.cewolf.dp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.XYDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * @author guido
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MovingAverageDatasetProducer implements DatasetProducer, Serializable {
	
	private static final Log log = LogFactory.getLog(MovingAverageDatasetProducer.class);

	/**
	 * @see de.laures.cewolf.DatasetProducer#produceDataset(Map)
	 */
	public Object produceDataset(Map params) throws DatasetProduceException {
		log.debug(params);
		DatasetProducer datasetProducer = (DatasetProducer)params.get("producer");
		log.debug(datasetProducer);
		Dataset dataset = (Dataset)datasetProducer.produceDataset(params);
		String suffix = (String)params.get("suffix");
		int period = ((Integer)params.get("period")).intValue();
		int skip = ((Integer)params.get("skip")).intValue();
		if(dataset instanceof XYDataset){
	        return MovingAverage.createMovingAverage((XYDataset)dataset, suffix, period, skip);
		} else {
			throw new DatasetProduceException("moving average only supported for XYDatasets");
		}
	}

	/**
	 * @see de.laures.cewolf.DatasetProducer#hasExpired(Map, Date)
	 */
	public boolean hasExpired(Map params, Date since) {
		return true;
	}

	/**
	 * @see de.laures.cewolf.DatasetProducer#getProducerId()
	 */
	public String getProducerId() {
		return getClass().getName();
	}

}
