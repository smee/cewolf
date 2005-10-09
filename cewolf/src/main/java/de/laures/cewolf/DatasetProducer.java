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

package de.laures.cewolf;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Produces a {@link org.jfree.data.Dataset} which will be rendered 
 * as a chart 	afterwards.
 * @see org.jfree.data.Dataset
 * @author  Guido Laures
 * @since 0.1
 */
public interface DatasetProducer extends Serializable {
    
    /**
     * By default the the name of the JSP attribute 
     * holding the producer instance is passed to the 
     * produceDataset method as a prameter.
     */
    public static final String PRODUCER_ATTRIBUTE_NAME = "de.laures.cewolf.DatasetProducer.id";
	
    /**
     * Produces a {@link org.jfree.data.Dataset} object.
     * @param params additional params for the dataset production. All elements
     * of this HashMap are of type <code>java.io.Serializable</code>. This is
     * necessary to ensure the the serialization of the dataset producer into
     * the http session. To provide a producer with additional production
     * parameters the &lt;param&gt; tag is used (see tag library documentation).
     * It is recommended to synchronize implementations of this method to avoid
     * concurrency problems.
     * @return an object of type <code>org.jfree.data.Dataset</code>.
     * @throws DatasetProduceException if an error occured during production
     * @since 0.2
     */
    Object produceDataset(Map params) throws DatasetProduceException;
	
	/**
	 * This method is called by the Cewolf framework to check if a formerly
	 * produced data can be reused. If the data which had already been used
	 * for chart rendering is still valid this method should return <code>true</code>.
	 * If possible the Cewolf framework will try to reuse the rendered chart
	 * image. If this is not possible because of some circumstances (e.g. the chart
	 * had been removed from the image cache) the produceDataset method is called afterwards.
	 * Therefore there is no guarantee that the dataset production is always 
	 * avoided if this method returns <code>true</true>.
	 * @param params the production parameters of the already produced data
	 * @param since the point in time when the already produced data had been produced
	 * @return <code>true</code> if the data which had been produced with the 
	 * passed in parameters has expired since its creation, <code>false</code> 
	 * otherwise
	 * @since 0.9
	 */
	boolean hasExpired(Map params, Date since);
	
	/**
	 * Tis method returns a unique ID for a DatasetProducer from this class.
	 * Producers with the same ID are supposed to produce the same data when
	 * called with the same paramters.
	 * @return the unique ID for instances of this poducer class
	 * @since 0.9
	 */
	String getProducerId();

}
