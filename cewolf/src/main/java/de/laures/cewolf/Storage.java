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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * Pluggable storage. Implement this class to change the
 * storage concept which Cewolf uses to store chart imanges and
 * provide the implementation class name as init parameter <em>storage</em> for Cewolf servlet.
 * @author glaures
 */
public interface Storage extends Serializable {

	/**
	 * Stores a chart image.
	 * @param chartImage the image to be stored
	 * @param pageContext servletContext which might be needed
	 * @return String the storage id which is used to find the image in storage
	 */
	public String storeChartImage(ChartImage chartImage, PageContext pageContext) throws CewolfException;
  
	/**
	 * Retrieves a chart image.
	 * @param id the id of the image
	 * @param request the request
	 * @return ChartImage the stored image instance
	 */
	public ChartImage getChartImage(String id, HttpServletRequest request);
    
	/**
	 * Tests if a chart image is already available in thsi store..
	 * @param chartImage the image to test
	 * @param pageContext the pageContext
	 * @return <code>true</code> if a stored instance of this image is availbale
	 */
	// public boolean contains(ChartImage chartImage, PageContext pageContext);

	/**
	 * Returns the key for this 
	 * @param chartImage
	 * @return String
	 */
	// public String getKey(ChartImage chartImage);

	/**
	 * Method init.
	 * @param servletContext
	 * @throws CewolfException
	 */
	public void init(ServletContext servletContext) throws CewolfException;


	/**
	 * Removes the image from the storage
	 * @param imgId Image id
	 * @param request Servlet request
	 * @return Image id
	 * @throws CewolfException
	 */
	public String removeChartImage(String imgId, HttpServletRequest request) throws CewolfException;
}
