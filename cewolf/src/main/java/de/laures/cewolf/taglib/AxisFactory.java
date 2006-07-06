package de.laures.cewolf.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;

/**
 * @author guido
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AxisFactory implements AxisConstants {

    protected Log log = LogFactory.getLog(AxisFactory.class.getName());
    
    private static final AxisFactory instance = new AxisFactory();
    
    protected AxisFactory(){
    }
    
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
