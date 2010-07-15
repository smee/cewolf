package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Random;
import java.lang.Math;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Minute;

import org.jfree.data.time.TimeSeries;

import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.links.CategoryItemLinkGenerator;

/**
 * @author AKAPUSTA
 */
public class ProduceMathDataExample implements DatasetProducer, Serializable {

	private double minX = Double.POSITIVE_INFINITY;
	private double maxX = Double.NEGATIVE_INFINITY;
	private double minY = Double.POSITIVE_INFINITY;
	private double maxY = Double.NEGATIVE_INFINITY;

	private final int NUM_DATA_POINTS = 50;

	private String[] points = {"Cosine", "Sine"};

	private double[][] data1 = new double[2][NUM_DATA_POINTS];
	private double[][] data2 = new double[2][NUM_DATA_POINTS];

	public ProduceMathDataExample() {
        for (int i=0; i<NUM_DATA_POINTS; i++){
            data1[0][i] = -2.0 * Math.PI + i * 4.0 * Math.PI / NUM_DATA_POINTS;
			if (data1[0][i] < minX)
				minX = data1[0][i];
			if (data1[0][i] > maxX)
				maxX = data1[0][i];
            data1[1][i] = Math.cos(data1[0][i]);
			if (data1[1][i] < minY)
				minY = data1[1][i];
			if (data1[1][i] > maxY)
				maxY = data1[1][i];

            data2[0][i] = data1[0][i];
            data2[1][i] = Math.sin(data1[0][i]);
			if (data2[1][i] < minY)
				minY = data2[1][i];
			if (data2[1][i] > maxY)
				maxY = data2[1][i];
        }
	}

	public Object produceDataset(Map arg1) throws DatasetProduceException {
        DefaultXYDataset dset = new DefaultXYDataset();

        dset.addSeries(points[0], data1);
        dset.addSeries(points[1], data2);

        return dset;
    }

    public boolean hasExpired(Map map1, Date date1){
        return true;
    }

    public String getProducerId(){
        return "chart data";
    }

    public String[] getPoints(){
        return points;
    }

    public void setPoints(String[] newPoints){
        this.points = newPoints;
    }

	public double getMinX() { return minX; }
	public double getMaxX() { return maxX; }
	public double getMinY() { return minY; }
	public double getMaxY() { return maxY; }
}

