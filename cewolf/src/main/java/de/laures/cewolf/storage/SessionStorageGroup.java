/*
 * Created on Aug 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.laures.cewolf.storage;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author brianf
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SessionStorageGroup implements Runnable
{
  private Map map = new HashMap();
  Thread      runner;

  private void start()
  {
    if ( runner == null || !runner.isAlive() )
    {
      runner = new Thread(this);
      runner.setDaemon(false);
      runner.setName("SessionCleanup");
      runner.setPriority(Thread.MIN_PRIORITY);
      runner.start();
    }
  }

  public synchronized Object get( Object a )
  {
    return map.get(a);
  }

  public synchronized Object put( Object a, Object b )
  {
    Object c = map.put(a, b);
    start();
    return c;
  }

  public synchronized Object remove( Object a )
  {
    return map.remove(a);
  }
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run()
  {
    while ( !map.isEmpty() )
    {
      Date date = new Date();
      synchronized (this)
      {
        Collection keys = map.keySet();

        Iterator iter = keys.iterator();

        while ( iter.hasNext() )
        {
          // System.out.println("Get Next");
          String cid = (String) iter.next();
          SessionStorageItem ssi = (SessionStorageItem) get(cid);
          if ( ssi.isExpired(date) )
          {
           // System.out.println("Removing " + ssi);
            iter.remove();
          }
        }
      }
      try
      {
        //System.gc();
        Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
   // System.out.println("Exiting Thread");   
  }
  public static void main( String[] args )
  {
    int start = 0;
   while(true)
    {
      System.out.println("Adding Objects");
    testThread(start);
    
    try
    {
      Thread.sleep(10000);
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }
  }

  public static void testThread(int start)
  {
    SessionStorageGroup ssg = new SessionStorageGroup();
    Calendar cal = new GregorianCalendar();
    cal.setTime(new Date());
    //   some huge garbage string
  
    for (int i = start; i < start+10000; i++)
    {
      cal.add(Calendar.MILLISECOND, 1);
      SessionStorageItem ssi = new SessionStorageItem(null, Integer.toString(i), cal.getTime());
      ssg.put(Integer.toString(i), ssi);
    }


  }
  /**
   * Test that memory is freed up when low on it...
   */
  public static void testSoftreferenceMemoryFreeup()
  {
    Date neverexpire = new Date(10000, 1, 1);
    SessionStorageGroup ssg = new SessionStorageGroup();

    //   some huge garbage string
    StringBuffer longString = new StringBuffer();
    for (int i = 0; i < 10000; i++)
    {
      longString.append(Math.random());
    }

    int i = 0;
    long minmem = Long.MAX_VALUE;
    while ( true )
    {
      //   let's use the id string to waste memory
      String key = Long.toString(System.currentTimeMillis());
      String id = key + longString;
      SessionStorageItem ssi = new SessionStorageItem(null, id, neverexpire);
      ssg.put(key, ssi);

      i++;
      long freemem = Runtime.getRuntime().freeMemory();
      long usedmem = Runtime.getRuntime().totalMemory();
      long maxmem = Runtime.getRuntime().maxMemory();
      if ( freemem < minmem )
      {
        minmem = freemem;
      }
      if ( i % 100 == 0 )
      {
        System.out.println("#" + i + ", minimum memory:" + minmem + ", freemem:" + freemem
            + ", usedmem:" + usedmem + ", maxmem:" + maxmem);
      }
    }

  }
}

