
/**
 * @author AKAPUSTA
 */

package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Random;
import java.lang.Math;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Minute;

import org.jfree.data.time.TimeSeries;

import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.links.CategoryItemLinkGenerator;

public class ProduceTimeSeriesDataExample implements DatasetProducer, Serializable {

	private final int NUM_DATA_POINTS = 20;

	private String date = null;
	private String[] points = {"Data1", "Data2"};

	public Object produceDataset(Map arg1) throws DatasetProduceException {

        TimeSeriesCollection ts = new TimeSeriesCollection();
        Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.AM_PM, Calendar.AM);
        Random ran = new Random();

        Date[] calDomain = new Date[NUM_DATA_POINTS];
        Float[] dset1 = new Float[NUM_DATA_POINTS];
        Float[] dset2 = new Float[NUM_DATA_POINTS];

        for(int i=0; i<NUM_DATA_POINTS; i++){
            calDomain[i] = cal.getTime();
            cal.add(Calendar.MINUTE, 15);
        }

        for (int j=0; j<NUM_DATA_POINTS; j++){
            float val1 = ran.nextFloat();
            if (val1 < 0.0){
                val1 = Math.abs(val1);
            }
            dset1[j] = new Float(val1);
        }

        for (int n=0; n<NUM_DATA_POINTS; n++){
            float val2 = ran.nextFloat();
            if (val2 < 0.0){
                val2 = Math.abs(val2);
            }
            dset2[n] = new Float(val2);
        }
        
        for(int w=0; w<points.length; w++){
            TimeSeries testts = new TimeSeries(points[w], Minute.class);
            for (int count=0; count<NUM_DATA_POINTS; count++){
                if (w==0) {
                    testts.add(new Minute(calDomain[count]), dset1[count]);
                } else if (w==1) {
                    testts.add(new Minute(calDomain[count]), dset2[count]);
                }
            }
            ts.addSeries(testts);
        }
        return ts;
    }

    public boolean hasExpired(Map map1, Date date1){
        return true;
    }

    public String getProducerId(){
        return "chart data";
    }

    public String getDate(){
        return date;
    }

    public void setDate(String newD){
        this.date = newD;
    }

    public String[] getPoints(){
        return points;
    }

    public void setPoints(String[] newPoints){
        this.points = newPoints;
    }

}
