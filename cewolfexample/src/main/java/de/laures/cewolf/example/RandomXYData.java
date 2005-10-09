/* ================================================================
 * Cewolf : Chart enabling Web Objects Framework
 * ================================================================
 *
 * Project Info:  http://cewolf.sourceforge.net
 * Project Lead:  Guido Laures (guido@laures.de);
 *
 * (C) Copyright 2002, by Guido Laures
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package de.laures.cewolf.example;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * Sample DatasetProducer whioh creates random XYDatasets.
 * @author  Guido Laures
 */
public class RandomXYData implements DatasetProducer, Serializable {

    private static final Log log = LogFactory.getLog(RandomXYData.class);

    /**
     * Produces a random XYDataset.
     * @return a random XYDataset
     */
    public Object produceDataset(Map params) throws DatasetProduceException {
    	log.debug("prodcing data.");
        XYSeries xys = new XYSeries("Example XY Dataset");
        int maxVal = 100;
        if (params.containsKey("maxVal")) {
            maxVal = ((Integer)params.get("maxVal")).intValue();
        }
        int minVal = -100;
        if (params.containsKey("minVal")) {
            minVal = ((Integer)params.get("minVal")).intValue();
        }
        final int inset = (maxVal - minVal) / 2;
        double last = maxVal - inset;
        for (int i = -10; i <= 10; i++) {
            final double y = Math.max(Math.min(last + ((Math.random() * inset) - inset / 2), maxVal), minVal);
            xys.add(i, y);
            last = y;
        }
        return new XYSeriesCollection(xys);
    }

	/**
	 * @see de.laures.cewolf.DatasetProducer#hasExpired(Map, Date)
	 */
	public boolean hasExpired(Map params, Date since) {
		log.debug(this + ".hasExpired()");
		return false;
	}
	
	public String getProducerId(){
		return "RandomXYData DatsetProducer";
	}

}
