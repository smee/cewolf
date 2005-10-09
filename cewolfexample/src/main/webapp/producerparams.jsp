<%@page contentType="text/html"%>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>
<html>
<body>
<H1>Producer Param Test Page</H1>
<p>
<jsp:useBean id="xyData" class="de.laures.cewolf.example.RandomXYData" />
<cewolf:chart id="XYChart" title="XYChart" renderer="servlet/chart" width="300" height="300" type="xy" xaxislabel="x-values" yaxislabel="y-values">
    <cewolf:data>
        <cewolf:producer id="xyData">
            <cewolf:param name="maxVal" value="<%= new Integer(1000) %>"/>
            <cewolf:param name="minVal" value="<%= new Integer(650) %>"/>
        </cewolf:producer>
    </cewolf:data>
</cewolf:chart>
</body>
</html>
