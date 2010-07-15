
package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class xyData implements DatasetProducer, Serializable
{
    public xyData() { }

    public Object produceDataset(Map params) throws DatasetProduceException {
        DefaultXYDataset dataset = new DefaultXYDataset();
		double[][] data = new double[2][10];
		int lastY = (int)(Math.random() * 1000D + 1000D);
		for(int i = 0; i < 10; i++) {
			int y = lastY + (int)(Math.random() * 200D - 100D);
			data[0][i] = i;
			data[1][i] = y;
			lastY = y;
			dataset.addSeries("xyData for Spline", data);
		}

        return dataset;
    }

    public boolean hasExpired(Map params, Date since)
    {
        return System.currentTimeMillis() - since.getTime() > 0L;
    }

    public String getProducerId()
    {
        return "xyData DatasetProducer";
    }
}
