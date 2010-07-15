package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

import org.jfree.data.time.*;

public class DemoDataSetProducer1 implements DatasetProducer, Serializable {

	private int idx;

	private double offset;

	public DemoDataSetProducer1 (int idx, double offset) {
		this.idx = idx;
		this.offset = offset;
	}

	private Month[] months = new Month[] { new Month(2, 2001),
			new Month(3, 2001), new Month(4, 2001), new Month(5, 2001),
			new Month(6, 2001), new Month(7, 2001), new Month(8, 2001),
			new Month(9, 2001), new Month(10, 2001), new Month(11, 2001),
			new Month(12, 2001), new Month(1, 2002), new Month(2, 2002),
			new Month(3, 2002), new Month(4, 2002), new Month(5, 2002),
			new Month(6, 2002), new Month(7, 2002) };

	private double[] values = new double[] { 181.8, 167.3, 153.8, 167.6,
			158.8, 148.3, 153.9, 142.7, 123.2, 131.8, 139.6, 142.9, 138.7,
			137.3, 143.9, 139.8, 137.0, 132.8 };

	public Object produceDataset (Map params) throws DatasetProduceException {
		TimeSeries s1 = new TimeSeries("L&G European Index Trust " + idx, Month.class);
		for (int i = 0; i < months.length; i++) {
			s1.add(months[i], values[i] + offset);
		}
		return new TimeSeriesCollection(s1);
	}

	public boolean hasExpired (Map params, Date since) {
		return false;
	}

	public String getProducerId() {
		return "European Index Trust " + idx;
	}
}

