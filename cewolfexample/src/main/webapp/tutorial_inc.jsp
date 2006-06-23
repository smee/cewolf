<HTML>
<HEAD>
<TITLE>Cewolf Tutorial - Step 1</TITLE>
<link href="cewolf.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY bgcolor="#DDE8F2">
<H1>Cewolf Tutorial</H1>
<H2>A Simple Page View Statistics Chart</H2>
<HR>
This page shows the resulting chart when finishing the Cewolf tutorial on the <a href="http://cewolf.sourceforge.net">Cewolf homepage</a>.<p>
<jsp:useBean id="pageViews" class="de.laures.cewolf.example.PageViewCountData"/>
<cewolf:chart 
    id="line" 
    title="Page View Statistics" 
    type="line" 
    xaxislabel="Page" 
    yaxislabel="Views">
    <cewolf:data>
        <cewolf:producer id="pageViews"/>
    </cewolf:data>
</cewolf:chart>
<p>

<cewolf:img chartid="line" renderer="/cewolf" width="400" height="300">
    <cewolf:map linkgeneratorid="pageViews" tooltipgeneratorid="pageViews"
    	tooltipRendererClass="de.laures.cewolf.tooltips.ITooltipRenderer$Overlib"
    />
</cewolf:img>
<P>
</BODY>
</HTML>