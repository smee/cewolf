package de.laures.cewolf.dp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * @author glaures
 */
public class DataSourceXYDatasetProducer implements DatasetProducer, Serializable {

	static final long serialVersionUID = 4624928252168845205L;

	public static final String PARAM_SERIES_LIST = "series";

	/**
	 * @see de.laures.cewolf.DatasetProducer#produceDataset(Map)
	 */
	public Object produceDataset(Map params) throws DatasetProduceException {
		/*
		DataSourceXYSeries series = new DataSourceXYSeries("select * from xy;");
		XYSeriesCollection dataset = new XYSeriesCollection();
		try {
			DataSource ds = getDataSource((String)params.get(PARAM_DATASOURCE));
			dataset.addSeries(series.produceXYSeries(ds));
		} catch (NamingException nEx) {
			nEx.printStackTrace();
			throw new DatasetProduceException(nEx.getMessage());
		}
		*/
		return null;
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
		return toString();
	}

}
