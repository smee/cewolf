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

package de.laures.cewolf.storage;

import java.io.Serializable;
import java.util.Date;

import de.laures.cewolf.CewolfException;
import de.laures.cewolf.ChartImage;

/**
 * @author guido
 *
 */
public class SerializableChartImage implements ChartImage, Serializable {
	
	private final int width;
	private final int height;
	private final int type;
	private final Date timeoutTime;
	private final String mimeType;
	private final byte[] data;
	
	public SerializableChartImage(ChartImage img) throws CewolfException{
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.type = img.getType();
		this.mimeType = img.getMimeType();
		this.data = img.getBytes();
		this.timeoutTime = img.getTimeoutTime();
	}

	/**
	 * @see de.laures.cewolf.ChartImage#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @see de.laures.cewolf.ChartImage#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @see de.laures.cewolf.ChartImage#getType()
	 */
	public int getType() {
		return type;
	}

	/**
	 * @see de.laures.cewolf.ChartImage#getBytes()
	 */
	public byte[] getBytes() throws CewolfException {
		return data;
	}

	/**
	 * @see de.laures.cewolf.ChartImage#getMimeType()
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @see de.laures.cewolf.ChartImage#getSize()
	 */
	public int getSize() throws CewolfException {
		return data.length;
	}

  /* (non-Javadoc)
   * @see de.laures.cewolf.ChartImage#getTimeoutTime()
   */
  public Date getTimeoutTime() {      
    return timeoutTime;
  }

}
