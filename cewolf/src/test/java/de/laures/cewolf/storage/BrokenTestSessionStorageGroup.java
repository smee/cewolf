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
public class BrokenTestSessionStorageGroup extends TestCase {

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
  // public void testStorageGroupCleanup() throws Exception {
  // // put the two charts into the storage groups
  // group1.put(ssi_chart1.getCid(), ssi_chart1);
  // group2.put(ssi_chart2.getCid(), ssi_chart2);
//
//
  // // assertTrue("the cleaner thread must run now", StorageCleaner.getInstance().isRunning());
//
  // // the groups must still contain these
  // assertNotNull(group1.get(ssi_chart1.getCid()));
  // assertNotNull(group2.get(ssi_chart2.getCid()));
  // // wait a bit so the 1 second chart goes away
  // Thread.sleep(2000);
  // assertNull("One second image must go away", group1.get(ssi_chart1.getCid()));
  // assertNotNull("Ten second image must stay", group2.get(ssi_chart2.getCid()));
  // // wait more for the other to go away
  // Thread.sleep(9000);
  // assertTrue("All images must go away", group1.isEmpty());
  // assertTrue("All images must go away", group2.isEmpty());
//
  // }


}
