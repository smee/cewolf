<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="de.laures.cewolf.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.time.*"%>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.plot.Plot" %>
<%@page import="org.jfree.chart.plot.XYPlot" %>
<%@page import="org.jfree.chart.renderer.xy.XYItemRenderer" %>
<%@page import="java.awt.Color" %>
<%@page import="org.jfree.data.category.CategoryDataset" %>
<%@page import="de.laures.cewolf.tooltips.XYToolTipGenerator" %>
<%@page import="de.laures.cewolf.links.XYItemLinkGenerator" %>
<%@ page import="org.jfree.data.xy.XYDataset" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%!class DemoDataSetProducer1 implements DatasetProducer {

		private int idx;

		private double offset;

		public DemoDataSetProducer1(int idx, double offset) {
			this.idx = idx;
			this.offset = offset;
		}

		private Month[] months = new Month[] { new Month(2, 2001),
				new Month(3, 2001), new Month(4, 2001), new Month(5, 2001),
				new Month(6, 2001), new Month(7, 2001), new Month(8, 2001),
				new Month(9, 2001), new Month(10, 2001), new Month(11, 2001),
				new Month(12, 2001), new Month(1, 2002), new Month(2, 2002),
				new Month(3, 2002), new Month(4, 2002), new Month(5, 2002),
				new Month(6, 2002), new Month(7, 2002) };

		private double[] values = new double[] { 181.8, 167.3, 153.8, 167.6,
				158.8, 148.3, 153.9, 142.7, 123.2, 131.8, 139.6, 142.9, 138.7,
				137.3, 143.9, 139.8, 137.0, 132.8 };

		public Object produceDataset(Map params) throws DatasetProduceException {
			TimeSeries s1 = new TimeSeries("L&G European Index Trust " + idx, Month.class);
			for (int i = 0; i < months.length; i++) {
				s1.add(months[i], values[i] + offset);
			}
			return new TimeSeriesCollection(s1);
		}

		public boolean hasExpired(Map params, Date since) {
			return false;
		}

		public String getProducerId() {
			return "European Index Trust " + idx;
		}

	}

	%>

<%
		final DatasetProducer dp1 = new DemoDataSetProducer1(0,-50);

		DatasetProducer dp2 = new DatasetProducer() {

			public Object produceDataset(Map params) throws DatasetProduceException {
				TimeSeries data = ((TimeSeriesCollection) dp1.produceDataset(new HashMap())).getSeries(0);
				return new TimeSeriesCollection(MovingAverage.createMovingAverage((TimeSeries) data,
								"Six Month Moving Average", 6, 6));
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}

			public String getProducerId() {
				return "European Index Trust Average";
			}

		};

		// example how to create a post processor for each charts in the overlaid
		ChartPostProcessor postprocessor = new ChartPostProcessor() {

			public void processChart(Object chart, Map params) {
				JFreeChart jfc = (JFreeChart) chart;
				XYPlot plot = (XYPlot) jfc.getPlot();
				// set different colors for each:
				Color[] colors = new Color[] { Color.BLACK, Color.BLUE,
						Color.YELLOW, Color.GREEN, Color.GRAY };
				for (int i = 0; i < 4; i++) {
					XYItemRenderer renderer = plot.getRenderer(i);
					if (renderer!=null) {
						renderer.setPaint(colors[i]);
					}
				}
			}

		};

		pageContext.setAttribute("xy1", dp1);
		pageContext.setAttribute("xy2", dp2);

		// create parallel data lines for these charts:
		pageContext.setAttribute("dp3", new DemoDataSetProducer1(3, 40));
		pageContext.setAttribute("dp4", new DemoDataSetProducer1(4, 50));
		pageContext.setAttribute("dp5", new DemoDataSetProducer1(5, 60));

		pageContext.setAttribute("postprocessor", postprocessor);
		
		
		// create example link generator for the overlaid chart
		final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
		
		XYToolTipGenerator tooltipGenerator = new XYToolTipGenerator() {
			public String generateToolTip(XYDataset data, int series, int item) {
				Date d = new Date(((Long)data.getX(series,item)).longValue());
				return "tooltip x=" + dateformat.format(d) + ",y="+ data.getY(series,item);
			}
		};
		
		XYItemLinkGenerator linkGenerator = new XYItemLinkGenerator() {
			 public String generateLink(Object data, int series, int item) {
			 	XYDataset dataset = (XYDataset) data;
			 	Date d = new Date(((Long)dataset.getX(series,item)).longValue());
			 	return "link x=" + dateformat.format(d) + ",y="+ dataset.getY(series,item);
			 }
		};	
		
		pageContext.setAttribute("tooltipGenerator", tooltipGenerator);
		pageContext.setAttribute("linkGenerator", linkGenerator);	
		
%>
<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css"></head>
<BODY bgcolor="#DDE8F2">
<H1>Complex Chart Set</H1>
<p>
<table border=0>

<TR>
<TD>
<cewolf:overlaidchart 
	id="test2" 
	title="LG European Index Trust (with moving average)" 
	type="overlaidxy" 
	xaxistype="date"
	yaxistype="number"
	xaxislabel="x-values" 
	yaxislabel="y-values">
    <cewolf:colorpaint color="#AAAAFFEE"/>
		
		<cewolf:plot type="xyline">
			<cewolf:data>
      			<cewolf:producer id="xy2" />
	    	</cewolf:data>
		</cewolf:plot>
		
		<cewolf:plot type="xyverticalbar" >
			<cewolf:data>
      			<cewolf:producer id="xy1" />
    		</cewolf:data>
		</cewolf:plot>
		
		<cewolf:plot type="xyline">
			<cewolf:data>
      			<cewolf:producer id="dp3" />
	    	</cewolf:data>
		</cewolf:plot>		
		
		<cewolf:plot type="scatter">
			<cewolf:data>
      			<cewolf:producer id="dp4" />
    		</cewolf:data>
		</cewolf:plot>		
		
		<cewolf:plot type="scatter">
			<cewolf:data>
      			<cewolf:producer id="dp5" />
    		</cewolf:data>
		</cewolf:plot>	
		
		<cewolf:chartpostprocessor id="postprocessor" />	
		
</cewolf:overlaidchart>

<!-- this demonstrates that links work with overlaid chart too -->
<cewolf:img chartid="test2" renderer="/cewolf" width="500" height="300">
	<cewolf:map linkgeneratorid="linkGenerator"  tooltipgeneratorid="tooltipGenerator" />
</cewolf:img>

</TD>
</TR>
</TABLE>
</body>
</html>
