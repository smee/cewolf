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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import de.laures.cewolf.CewolfException;
import de.laures.cewolf.ChartImage;
import de.laures.cewolf.Storage;
import de.laures.cewolf.taglib.util.KeyGenerator;

/**
 * Storage stores images in session, but expires them after a certain time. 
 * This expiration time defaults to 300 seconds, and can be changed by adding 
 * the timeout="xxx" parameter to <cewolf:img> and <cewolf:legend> tags.
 * 
 * @author brianf
 */
public class LongTermSessionStorage implements Storage
{
 
  public final String getKey( ChartImage cid )
  {
    return String.valueOf(KeyGenerator.generateKey((Serializable) cid));
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.laures.cewolf.Storage#storeChartImage(de.laures.cewolf.ChartImage,
   *      javax.servlet.jsp.PageContext)
   */
  public String storeChartImage( ChartImage chartImage, PageContext pageContext ) throws CewolfException
  {
    HttpSession session = pageContext.getSession();
    SessionStorageGroup ssg = (SessionStorageGroup) session.getAttribute("CewolfCharts");
    if ( ssg == null )
    {
      ssg = new SessionStorageGroup();
      session.setAttribute("CewolfCharts", ssg);
    }
    String cid = getKey(chartImage);
    SessionStorageItem ssi = new SessionStorageItem(chartImage, cid, chartImage.getTimeoutTime());
    ssg.put(cid, ssi);

    return cid;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.laures.cewolf.Storage#getChartImage(java.lang.String,
   *      javax.servlet.http.HttpServletRequest)
   */
  public ChartImage getChartImage( String id, HttpServletRequest request )
  {
    HttpSession session = request.getSession();
    ChartImage chart = null;
    SessionStorageGroup ssg = (SessionStorageGroup) session.getAttribute("CewolfCharts");
    if ( ssg != null )
    {
      SessionStorageItem ssi = (SessionStorageItem) ssg.get(id);
      if ( ssi != null )
      {
        chart = ssi.getChart();
      }
    }

    return chart;
  }
  /*
   * (non-Javadoc)
   * 
   * @see de.laures.cewolf.Storage#init(javax.servlet.ServletContext)
   */
  public void init( ServletContext servletContext ) throws CewolfException {
  }

}