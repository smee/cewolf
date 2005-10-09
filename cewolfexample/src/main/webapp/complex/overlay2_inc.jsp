


<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css"></head>
<BODY bgcolor="#DDE8F2">
<H1>Complex Chart Set</H1>
<p>
<table border=0>

<TR>
<TD>
<jsp:useBean id="hilo" class="de.laures.cewolf.example.HighLowDatasetProducer" scope="page"  />
<jsp:useBean id="movav" class="de.laures.cewolf.dp.MovingAverageDatasetProducer" scope="page"  />
<cewolf:overlaidchart 
	id="test2" 
	title="Overlaid Diagram" 
	type="overlaidxy" 
	xaxistype="date"
	yaxistype="number"
	xaxislabel="Date" 
	yaxislabel="Price">
    <cewolf:gradientpaint>
        <cewolf:point x="0" y="0" color="#FFFFFFEE" />
        <cewolf:point x="0" y="300" color="#AAAAFFEE" />
    </cewolf:gradientpaint>
	<cewolf:plot type="highlow">
		<cewolf:data>
  			<cewolf:producer id="hilo" />
		</cewolf:data>
	</cewolf:plot>
	
	<cewolf:plot type="xyline">
		<cewolf:data>
  			<cewolf:producer id="movav">
  				<cewolf:param name="producer" value="<%= hilo %>"/>
  				<cewolf:param name="suffix" value='<%= " (3 days average)" %>'/>
  				<cewolf:param name="skip" value="<%= new Integer(1000 * 60 * 60 * 24 * 3) %>"/>
  				<cewolf:param name="period" value="<%= new Integer(1000 * 60 * 60 * 24 * 3) %>"/>
  			</cewolf:producer>
    	</cewolf:data>
	</cewolf:plot>
</cewolf:overlaidchart>

<cewolf:img chartid="test2" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>
</TABLE>
</body>
</html>
