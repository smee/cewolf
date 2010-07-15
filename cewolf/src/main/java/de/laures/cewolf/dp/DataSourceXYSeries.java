package de.laures.cewolf.dp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jfree.data.xy.XYSeries;

import de.laures.cewolf.DatasetProduceException;

public class DataSourceXYSeries implements Serializable {

	static final long serialVersionUID = -925366561462055785L;

	private String dataSourceName;
	private String query;
	private String xCol = "x";
	private String yCol = "y";
	private String seriesName = "name";

	/**
	 * Constructor for DataSourceXYSeries.
	 */
	public DataSourceXYSeries (String dataSourceName, String query) {
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
		Connection con = null;
		Statement stmt = null;
		try {
			DataSource ds = getDataSource();
			con = ds.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int xColIndex = rs.findColumn(xCol);
			int yColIndex = rs.findColumn(yCol);
			while (rs.next()) {
				series.add((Number)rs.getObject(xColIndex), (Number)rs.getObject(yColIndex));
			}
		} catch (Exception namingEx) {
			namingEx.printStackTrace();
			throw new DatasetProduceException(namingEx.getMessage());
		} finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException sqlex) { }
			try { if (con != null) con.close(); } catch (SQLException sqlex) { }
		}
		return series;
	}
}
