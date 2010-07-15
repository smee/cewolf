package de.laures.cewolf.taglib;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;

/**
 * @author guido
 */
public class AxisFactory implements AxisConstants {

    private static final AxisFactory instance = new AxisFactory();
    
    protected AxisFactory(){ }
    
    public static final AxisFactory getInstance(){
    	return instance;
    }
    
	public Axis createAxis(int orientation, int type, String label) {
		switch (type) {
			case AXIS_TYPE_DATE :
				return new DateAxis(label);
			case AXIS_TYPE_NUMBER :
				return new NumberAxis(label);
			case AXIS_TYPE_CATEGORY://added by lrh 2005-07-11
	            return new CategoryAxis(label);
			default:
				throw new RuntimeException("unsupported axis type constant " + type);
		}
	}

}
