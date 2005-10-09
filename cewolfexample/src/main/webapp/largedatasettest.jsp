<%@page contentType="text/html"%>
<%@page import="java.util.*"%>
<%@page import="de.laures.cewolf.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.xy.*"%>;

<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>
<%
	final java.io.PrintWriter fOut = new java.io.PrintWriter(out);
	DatasetProducer xyData = new DatasetProducer() {
	    public Object produceDataset(Map params) {
	    	long start = System.currentTimeMillis();
	        XYSeries xys = new XYSeries("Example XY Dataset");
	        double last = 0.0;
	        for(int i = 0; i <= 50000; i++){
	            double y = last + 0.45;
	            xys.add((double)i, y);
	            last = y;
	        }
	        try {
	        	fOut.write("dataset creation: " + (System.currentTimeMillis() - start) + "ms.\n");
	        } catch(Exception ex){
	        	ex.printStackTrace(fOut);
	        }
	        return new XYSeriesCollection(xys);
	    }
	    
	    public String getProducerId() {
			return "xyDataProducer";
		}
	
		public boolean hasExpired(Map params, Date since) {
			return false;
		}
	};
	pageContext.setAttribute("xyData", xyData);
%>
<html>
<body>
<H1>Large Dataset Test</H1>
<p>
<cewolf:chart 
    id="XYChart" 
    title="XYChart"
    type="xy">
    <cewolf:gradientpaint>
        <cewolf:point x="0" y="0" color="#FFFFFF" />
        <cewolf:point x="300" y="0" color="#DDDDFF" />
    </cewolf:gradientpaint>
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img 
    chartid="XYChart" 
    renderer="/cewolf" 
    width="500" 
    height="500"/>
<p>
</body>
</html>
