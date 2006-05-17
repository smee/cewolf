package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultHighLowDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * Creates some random data for high-low charts
 * @author glaures
 */
public class HighLowDatasetProducer implements DatasetProducer, Serializable {

    /**
     * @see de.laures.cewolf.DatasetProducer#produceDataset(Map)
     */
    public Object produceDataset(Map params) throws DatasetProduceException {
    	int numElements = 40;
    	Date dates[] = new Date[numElements];
    	double values1[] = new double[numElements];
    	double values2[] = new double[numElements];
    	double values3[] = new double[numElements];
    	double values4[] = new double[numElements];
    	double values5[] = new double[numElements];
    	Calendar cal = Calendar.getInstance();
    	// generate some random data
    	for (int i = 0; i < numElements; i++) {
    		dates[i] = cal.getTime();
    		cal.add(Calendar.DAY_OF_MONTH, 1); // advance
    		createRandomValue(values1, i);
    		createRandomValue(values2, i);
    		createRandomValue(values3, i);
    		createRandomValue(values4, i);
    		createRandomValue(values5, i);
		}
    	
    	Dataset ds = new DefaultHighLowDataset("Series 1", dates, values1, values2, values3, values4, values5);
    	return ds;
    }
    
    /**
     * Create a random value, and put it to the array
     * @param values The value array
     * @param index current index
     */
    private void createRandomValue(double values[], int index) {
    	double last = 0.0;
    	if (index > 0) {
    		last = values[index-1];
    	}
    	values[index] = last + (Math.sin(index* Math.PI/90) * 100.0d) + (Math.random()*5.0d);
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
