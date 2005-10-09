<%@page import="java.util.*"%>
<%@page import="de.laures.cewolf.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.time.*"%>

<%
final DatasetProducer dp1 = new DatasetProducer(){
    
	public Object produceDataset(Map params) throws DatasetProduceException {
		TimeSeries s1 = new TimeSeries("L&G European Index Trust", Month.class);
        s1.add(new Month(2, 2001), 181.8);
        s1.add(new Month(3, 2001), 167.3);
        s1.add(new Month(4, 2001), 153.8);
        s1.add(new Month(5, 2001), 167.6);
        s1.add(new Month(6, 2001), 158.8);
        s1.add(new Month(7, 2001), 148.3);
        s1.add(new Month(8, 2001), 153.9);
        s1.add(new Month(9, 2001), 142.7);
        s1.add(new Month(10, 2001), 123.2);
        s1.add(new Month(11, 2001), 131.8);
        s1.add(new Month(12, 2001), 139.6);
        s1.add(new Month(1, 2002), 142.9);
        s1.add(new Month(2, 2002), 138.7);
        s1.add(new Month(3, 2002), 137.3);
        s1.add(new Month(4, 2002), 143.9);
        s1.add(new Month(5, 2002), 139.8);
        s1.add(new Month(6, 2002), 137.0);
        s1.add(new Month(7, 2002), 132.8);
		return new TimeSeriesCollection(s1);
	}

	public boolean hasExpired(Map params, Date since) {
		return false;
	}
	
	public String getProducerId(){
		return "European Index Trust";
	}

};

DatasetProducer dp2 = new DatasetProducer(){
    
	public Object produceDataset(Map params) throws DatasetProduceException {
		TimeSeries data = ((TimeSeriesCollection)dp1.produceDataset(new HashMap())).getSeries(0);
        return new TimeSeriesCollection(MovingAverage.createMovingAverage((TimeSeries)data, "Six Month Moving Average", 6, 6));
	}

	public boolean hasExpired(Map params, Date since) {
		return false;
	}
	
	public String getProducerId(){
		return "European Index Trust Average";
	}

};

pageContext.setAttribute("xy1", dp1);
pageContext.setAttribute("xy2", dp2);
%>
<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css" />
</head>
<BODY bgcolor="#DDE8F2">
<H1>Cewolf Chart Set</H1>
<p>
<table border=0>
<TR>
<TD>
<cewolf:combinedchart id="test2" layout="vertical" title="LG European Index Trust (with moving average)" type="combinedxy" xaxislabel="x-values">
    <cewolf:colorpaint color="#AAAAFFEE"/>

		<cewolf:plot type="xyverticalbar" yaxislabel="y-values">
			<cewolf:data>
      			<cewolf:producer id="xy1" />
    		</cewolf:data>
		</cewolf:plot>

		<cewolf:plot type="xyline" yaxislabel="y-values">
			<cewolf:data>
      			<cewolf:producer id="xy2" />
	    	</cewolf:data>
		</cewolf:plot>
</cewolf:combinedchart>
<cewolf:img chartid="test2" renderer="/cewolf" width="500" height="500"/>
</TD>
</TR>
</TABLE>
</body>
</html>
