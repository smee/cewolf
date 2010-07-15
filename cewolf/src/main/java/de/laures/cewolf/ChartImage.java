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

/**
 * A special ChartHolder which also holds the image presentation of the chart.
 * @author  Guido Laures
 */
public interface ChartImage extends Serializable {

	public static final int IMG_TYPE_CHART = 0;
	public static final int IMG_TYPE_LEGEND = 1;

	/**
	 * Returns the width of the chart image in pixel.
	 * @return the width of the chart image in pixel
	 */
    public int getWidth();

	/**
	 * Returns the height of the chart image in pixel.
	 * @return the height of the chart image in pixel
	 */
    public int getHeight();

	/**
	 * Returns the type of the chart image.
	 * @return the type of the chart image
	 * @see #IMG_TYPE_CHART
	 * @see #IMG_TYPE_LEGEND
	 */
    public int getType();

    /**
     * Writes out a cached image to an outputstream. This method only marks the object
     * as accessed and therfore frees it for cache cleanup.
     * @throws CewolfException 
     */
    public byte[] getBytes() throws CewolfException;

    /**
     * Returns the MIME type of this image.
     * @return the MIME type of the image
     */
    public String getMimeType();

	/**
	 * Returns the size of the image in bytes.
	 * @return size of the image
	 * @throws CewolfException if the size could not be determined
	 */
    public int getSize() throws CewolfException;
    
    public Date getTimeoutTime();
}
