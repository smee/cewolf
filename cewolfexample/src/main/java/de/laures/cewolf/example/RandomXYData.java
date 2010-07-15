package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RandomXYData implements DatasetProducer, Serializable
{
    public Object produceDataset (Map params) throws DatasetProduceException {
		XYSeries xys = new XYSeries("Example XY Dataset");
		int maxVal = 100;
		if (params.containsKey("maxVal"))
			maxVal = Integer.parseInt((String) params.get("maxVal"));
		int minVal = -100;
		if (params.containsKey("minVal"))
			minVal = Integer.parseInt((String) params.get("minVal"));
		int inset = (maxVal - minVal) / 2;
		double last = (double) (maxVal - inset);
		for (int i = -10; i <= 10; i++) {
			double y = Math.max(Math.min(last + (Math.random() * (double) inset - (double) (inset / 2)),
					 (double) maxVal),
					(double) minVal);
			xys.add((double) i, y);
			last = y;
		}
		return new XYSeriesCollection(xys);
    }
    
    public boolean hasExpired(Map params, Date since) {
		return false;
    }
    
    public String getProducerId() {
		return "RandomXYData DatsetProducer";
    }
}
