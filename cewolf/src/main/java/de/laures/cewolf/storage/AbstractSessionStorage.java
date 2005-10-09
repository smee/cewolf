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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.laures.cewolf.CewolfException;
import de.laures.cewolf.ChartImage;
import de.laures.cewolf.Storage;
import de.laures.cewolf.taglib.util.KeyGenerator;

/**
 * @author glaures
 */
public abstract class AbstractSessionStorage implements Storage
{

  private static final Log log = LogFactory.getLog(AbstractSessionStorage.class);

  /**
   * @see de.laures.cewolf.Storage#storeChartImage(ChartImage, ServletContext)
   */
  public String storeChartImage( ChartImage cid, PageContext pageContext ) throws CewolfException
  {
    if ( contains(cid, pageContext) )
    {
      return getKey(cid);
    }
    log.debug("storing chart " + cid);
    final HttpSession session = pageContext.getSession();
    //String key = getKey(cid);
    return storeChartImage(cid, session);
  }


  /**
   * @see de.laures.cewolf.Storage#getChartImage(String)
   */
  public ChartImage getChartImage( String id, HttpServletRequest request )
  {
    HttpSession session = request.getSession();
    return (ChartImage) session.getAttribute(id);
  }

  public boolean contains( ChartImage cid, PageContext pageContext )
  {
    return pageContext.getSession().getAttribute(getKey(cid)) != null;
  }

  public final String getKey( ChartImage cid )
  {
    return String.valueOf(KeyGenerator.generateKey((Serializable) cid));
  }

  protected String storeChartImage( ChartImage cid, HttpSession session ) throws CewolfException
  {
    final String sessionKey = getKey(cid);
    synchronized (session)
    {
      session.setAttribute(sessionKey, getCacheObject(cid));
    }
    return sessionKey;
  }
  
  protected abstract Object getCacheObject( ChartImage cid ) throws CewolfException;

  /**
   * @see de.laures.cewolf.Storage#init(ServletContext)
   */
  public void init( ServletContext servletContext ) throws CewolfException
  {
  }
  


}