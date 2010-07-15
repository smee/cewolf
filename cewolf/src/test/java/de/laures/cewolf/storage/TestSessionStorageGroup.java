/*
 * $Id$
 */
package de.laures.cewolf.storage;

import java.util.Calendar;

import junit.framework.TestCase;

/**
 * Test for the SessionStorageGroup
 * @author zluspai
 */
public class TestSessionStorageGroup extends TestCase {

	private SessionStorageGroup group1;
	private SessionStorageGroup group2;
	
	private SessionStorageItem ssi_chart1;
	private SessionStorageItem ssi_chart2;
	
	/**
	 * Set up the test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		// prepare independent groups
		group1 = new SessionStorageGroup();
		group2 = new SessionStorageGroup();
		
		// prepare expiration dates
		Calendar expInOneSec = Calendar.getInstance();
		expInOneSec.add(Calendar.SECOND, 1);
		Calendar expInTenSec = Calendar.getInstance();
		expInTenSec.add(Calendar.SECOND, 10);
		
		ssi_chart1 = new SessionStorageItem(null, "chart1", expInOneSec.getTime());
		ssi_chart2 = new SessionStorageItem(null, "chart2", expInTenSec.getTime());
	}
	
	/**
	 * Clean up 
	 * @throws Exception
	 */
	protected void tearDown() throws Exception {
		// clean up references to the pending storage groups, so 
		// the gc() test will work correctly
		group1 = null;
		group2 = null;
		
		super.tearDown();
	}
	
	/**
	 * Test that the storage group will be cleaned up properly
	 */
	public void testStorageGroupCleanup() throws Exception {
		// put the two charts into the storage groups
		group1.put(ssi_chart1.getCid(), ssi_chart1);
		group2.put(ssi_chart2.getCid(), ssi_chart2);
		
		assertTrue("the cleaner thread must run now", StorageCleaner.getInstance().isRunning());
		
		// the groups must still contain these
		assertNotNull(group1.get(ssi_chart1.getCid()));
		assertNotNull(group2.get(ssi_chart2.getCid()));
		// wait a bit so the 1 second chart goes away
		Thread.sleep(2000);
		assertNull("One second image must go away", group1.get(ssi_chart1.getCid()));
		assertNotNull("Ten second image must stay", group2.get(ssi_chart2.getCid()));
		// wait more for the other to go away
		Thread.sleep(9000);
		assertTrue("All images must go away", group1.isEmpty());
		assertTrue("All images must go away", group2.isEmpty());
		
	}
	
	/**
	 * Test that cleaner thread will stop after the groups are cleaned up...
	 */
	public void testCleanerThreadStop() throws Exception {
		// put the two charts into the storage groups
		group1.put(ssi_chart1.getCid(), ssi_chart1);
		group2.put(ssi_chart2.getCid(), ssi_chart2);
		
		assertTrue("the cleaner thread must run now", StorageCleaner.getInstance().isRunning());
		
		// trying to make the groups garbage collected:
		group1 = null;
		group2 = null;
		// wait and gc for 2 seconds...
		for (int i=0; i<20;i++) {
			System.gc();
			Thread.sleep(100);
		}
		
		// possibly the thread is stopped by now
		assertFalse("the cleaner thread should be stopped now", StorageCleaner.getInstance().isRunning());
	}
	
//	  public static void main( String[] args )
//	  {
//	    int start = 0;
//	   while(true)
//	    {
//	      System.out.println("Adding Objects");
//	    testThread(start);
//	    
//	    try
//	    {
//	      Thread.sleep(10000);
//	    }
//	    catch (InterruptedException e)
//	    {
//	      // TODO Auto-generated catch block
//	      e.printStackTrace();
//	    }
//	    }
//	  }
//
//	  public static void testThread(int start)
//	  {
//	    SessionStorageGroup ssg = new SessionStorageGroup();
//	    Calendar cal = new GregorianCalendar();
//	    cal.setTime(new Date());
//	    //   some huge garbage string
//	  
//	    for (int i = start; i < start+10000; i++)
//	    {
//	      cal.add(Calendar.MILLISECOND, 1);
//	      SessionStorageItem ssi = new SessionStorageItem(null, Integer.toString(i), cal.getTime());
//	      ssg.put(Integer.toString(i), ssi);
//	    }
//
//
//	  }
//	  /**
//	   * Test that memory is freed up when low on it...
//	   */
//	  public static void testSoftreferenceMemoryFreeup()
//	  {
//	    Date neverexpire = new Date(10000, 1, 1);
//	    SessionStorageGroup ssg = new SessionStorageGroup();
//
//	    //   some huge garbage string
//	    StringBuffer longString = new StringBuffer();
//	    for (int i = 0; i < 10000; i++)
//	    {
//	      longString.append(Math.random());
//	    }
//
//	    int i = 0;
//	    long minmem = Long.MAX_VALUE;
//	    while ( true )
//	    {
//	      //   let's use the id string to waste memory
//	      String key = Long.toString(System.currentTimeMillis());
//	      String id = key + longString;
//	      SessionStorageItem ssi = new SessionStorageItem(null, id, neverexpire);
//	      ssg.put(key, ssi);
//
//	      i++;
//	      long freemem = Runtime.getRuntime().freeMemory();
//	      long usedmem = Runtime.getRuntime().totalMemory();
//	      long maxmem = Runtime.getRuntime().maxMemory();
//	      if ( freemem < minmem )
//	      {
//	        minmem = freemem;
//	      }
//	      if ( i % 100 == 0 )
//	      {
//	        System.out.println("#" + i + ", minimum memory:" + minmem + ", freemem:" + freemem
//	            + ", usedmem:" + usedmem + ", maxmem:" + maxmem);
//	      }
//	    }
//
//	  }
//	
	

}
