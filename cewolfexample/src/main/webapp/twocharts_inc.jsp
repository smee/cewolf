<HTML>
<HEAD>
<TITLE>Cewolf Tutorial - Step 1</TITLE>
<link href="cewolf.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY bgcolor="#DDE8F2">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<script language="JavaScript" src="overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
<H1>Cewolf Test</H1>
<H2>Two charts using the same dataset rpoducer</H2>
<HR>
<jsp:useBean id="pageViews" class="de.laures.cewolf.example.PageViewCountData"/>
<cewolf:chart 
    id="line" 
    title="Page View Statistics" 
    type="line" 
    xaxislabel="Page" 
    yaxislabel="Views">
    <cewolf:data>
        <cewolf:producer id="pageViews" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="line" renderer="/cewolf" width="400" height="300" />
<p>
<cewolf:chart 
    id="line" 
    title="Page View Statistics" 
    type="line" 
    xaxislabel="Page" 
    yaxislabel="Views">
    <cewolf:data>
        <cewolf:producer id="pageViews" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="line" renderer="/cewolf" width="300" height="300" />
</BODY>
</HTML>