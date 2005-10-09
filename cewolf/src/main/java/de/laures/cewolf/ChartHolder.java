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

/**
 * An object of this type represents a full descritption of a chart.
 * Therefore it is able to produce the chart and dataset object
 * resulting out of this definition.
 * @author  Guido Laures
 */
public interface ChartHolder {

	/**
	 * Returns a chart. The type of this object is dependant on the underlying
	 * chart implementation. As of this version of Cewolf it is of type
	 * org.jfree.chart.JFreeChart.
	 * @return the chart object for this definition
	 * @throws DatasetProduceException if there could be no data produced for
	 * the cahrt
	 * @throws ConfigurationException if there is something wrong in the
	 * Cewolf configuration
	 * @throws PostProcessingException if a post processor failed to 
	 * process the chart
	 */
    public Object getChart() throws DatasetProduceException, PostProcessingException, ChartValidationException;
    
	/**
	 * Returns the dataset produced when using this definition.The type of this object is dependant on the underlying
	 * chart implementation. As of this version of Cewolf it is of type
	 * org.jfree.data.Dataset.
	 * @return Object the dataset for this definition
	 * @throws DatasetProduceException if the dataset could not be produced
	 */
    public Object getDataset() throws DatasetProduceException;
    
}
