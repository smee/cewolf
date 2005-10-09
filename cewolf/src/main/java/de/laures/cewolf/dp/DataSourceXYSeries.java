package de.laures.cewolf.dp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jfree.data.xy.XYSeries;

import de.laures.cewolf.DatasetProduceException;

/**
 * @author glaures
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DataSourceXYSeries implements Serializable {

	private String dataSourceName;
	private String query;
	private String xCol = "x";
	private String yCol = "y";
	private String seriesName = "name";
	
	/**
	 * Constructor for DataSourceXYSeries.
	 */
	public DataSourceXYSeries(String dataSourceName, String query) {
		this.dataSourceName = dataSourceName;
		this.query = query;
	}

	protected DataSource getDataSource() throws NamingException {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		return (DataSource) envCtx.lookup(dataSourceName);
	}

	/**
	 * @see de.laures.cewolf.DatasetProducer#produceDataset(Map)
	 */
	public XYSeries produceXYSeries() throws DatasetProduceException {
		XYSeries series = new XYSeries(seriesName);
		try {
			DataSource ds = getDataSource();
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement().executeQuery(query);
			int xColIndex = rs.findColumn(xCol);
			int yColIndex = rs.findColumn(yCol);
			while(rs.next()){
				series.add((Number)rs.getObject(xColIndex), (Number)rs.getObject(yColIndex));
			}
		} catch (Exception namingEx) {
			namingEx.printStackTrace();
			throw new DatasetProduceException(namingEx.getMessage());
		}
		return series;
	}
}
