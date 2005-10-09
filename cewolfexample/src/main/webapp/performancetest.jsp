<%@page contentType="text/html"%>
<%@page import="java.util.*"%>
<%@page import="de.laures.cewolf.*"%>
<%@page import="org.jfree.data.general.*"%>;

<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>
<%

DatasetProducer pieData = new DatasetProducer() {
    public Object produceDataset(Map params) {
        final String[] categories = {"apples", "pies", "bananas", "oranges"};
        DefaultPieDataset ds = new DefaultPieDataset();
        for(int i = 0; i < categories.length; i++){
            ds.setValue(categories[i], new Integer(i + 3));
        }
        return ds;
    }
    	
   	public String getProducerId() {
		return "PieDataProducer";
	}
	
	public boolean hasExpired(Map params, Date since) {
		return false;
	}
};
session.setAttribute("pieData", pieData);
%>
<html>
<body>
<H1>Caching Test</H1>
<p>
<% 
int count = 50;
long start = System.currentTimeMillis();
for(int i = 0; i < count; i++) { %>
<cewolf:chart 
    id="pieChart<%= i %>" 
    title="PieChart"
    type="pie">
    <cewolf:gradientpaint>
        <cewolf:point x="0" y="0" color="#FFFFFF" />
        <cewolf:point x="300" y="0" color="#DDDDFF" />
    </cewolf:gradientpaint>
    <cewolf:data>
        <cewolf:producer id="pieData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img 
    chartid="pieChart<%= i %>" 
    renderer="/cewolf" 
    width="<%= 300 + i %>" 
    height="300"
    />
<% } 
long stop = System.currentTimeMillis();
long last = (stop - start) / count;
%>
<p>
Time for one chart: <%= last %>
</body>
</html>
