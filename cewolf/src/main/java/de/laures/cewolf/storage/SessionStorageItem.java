/*
 * Created on Aug 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.laures.cewolf.storage;

import java.util.Date;

import de.laures.cewolf.ChartImage;

/**
 * @author brianf
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SessionStorageItem
{
  String     cid     = null;
  ChartImage chart   = null;
  Date       timeout = null;

  public SessionStorageItem( ChartImage theChart, String theCid, Date theTimeout )
  {
    chart   = theChart;
    cid     = theCid;
    timeout = theTimeout;
  }
  
  public String toString()
  {
    return ("SSI: id:"+cid+" expires:"+timeout);
  }
  public final boolean isExpired(Date currentTime)
  {
    return currentTime.after(timeout);
  }
  /**
   * @return Returns the chart.
   */
  public ChartImage getChart()
  {
    return chart;
  }
  /**
   * @param chart
   *          The chart to set.
   */
  public void setChart( ChartImage chart )
  {
    this.chart = chart;
  }
  /**
   * @return Returns the cid.
   */
  public String getCid()
  {
    return cid;
  }
  /**
   * @param cid
   *          The cid to set.
   */
  public void setCid( String cid )
  {
    this.cid = cid;
  }
  /**
   *  
   */
  public SessionStorageItem()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @return Returns the timeout.
   */
  public Date getTimeout()
  {
    return timeout;
  }
  /**
   * @param timeout
   *          The timeout to set.
   */
  public void setTimeout( Date timeout )
  {
    this.timeout = timeout;
  }
}