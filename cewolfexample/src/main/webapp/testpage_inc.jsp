<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css">
</head>
<BODY bgcolor="#DDE8F2">
<H1>Cewolf test page</H1>
<p>
<jsp:useBean id="testXYData" class="de.laures.cewolf.example.RandomXYData" scope="page"  />
<jsp:useBean id="myEnhancer" class="de.laures.cewolf.example.ExtraTitleEnhancer" scope="page" />
<jsp:useBean id="params" class="de.laures.cewolf.example.ParamBean" scope="request"/>
<jsp:setProperty name="params" property="*"/>
<form method="GET">
<!-- outermost table -->
<TABLE><TR><TD>

<!-- control panel table -->
<TABLE border=1 cellspacing=3> 
<TR>
    <TD><%@include file='chartparamspanel.jsp'%></TD>
</TR>
<TR>
    <TD><%@include file='paintparamspanel.jsp'%></TD>
</TR>
<TR>
    <TD><%@include file='dataparamspanel.jsp'%></TD>
</TR>
<TR>
    <TD><%@include file='enhancerparamspanel.jsp'%></TD>
</TR>
<TR>
    <TD colspan='3' align="center"><INPUT type="submit" value='Render'></TD>
</TR>
</TABLE>
<!-- end control panel table -->

</TD>
<TD valign="top">

<!-- image panel -->
<cewolf:chart 
    id="XYChart" 
    type="xy"
    title='<%= params.getTitle() %>'
    antialias='<%= params.getAntialias() %>'
    showlegend='<%= params.isLegend() %>'
    legendanchor='<%= params.getLegendAnchor() %>'>
    <% if(params.isSelectedPaint("color")) { %>
        <cewolf:colorpaint 
            color='<%= params.getColorColor() %>'/>
    <% } else if(params.isSelectedPaint("gradient")) { %>
        <cewolf:gradientpaint 
            cyclic="<%= params.getCyclic() %>">
            <cewolf:point 
                x="<%= params.getGradientX1() %>" 
                y="<%= params.getGradientY1() %>" 
                color="<%= params.getGradientColor1() %>" />
            <cewolf:point 
                x="<%= params.getGradientX2() %>" 
                y="<%= params.getGradientY2() %>" 
                color="<%= params.getGradientColor2() %>" />
        </cewolf:gradientpaint>
    <% } else { %>
        <cewolf:texturepaint 
            image="<%= params.getTextureImage() %>" 
            width="<%= params.getTextureWidth() %>" 
            height="<%= params.getTextureHeight() %>" />
    <% } %>
    <cewolf:data>
        <cewolf:producer 
            id="testXYData">
            <cewolf:param 
                name="maxVal" 
                value="<%= new Integer(params.getMaxVal()) %>"/>
            <cewolf:param 
                name="minVal" 
                value="<%= new Integer(params.getMinVal())  %>"/>
        </cewolf:producer>
    </cewolf:data>
    <cewolf:chartpostprocessor 
        id="myEnhancer">
        <cewolf:param
            name="title"
            value="<%= params.getExtraTitle() %>"/>
    </cewolf:chartpostprocessor>
</cewolf:chart>
<cewolf:img 
    chartid="XYChart" 
    renderer="/cewolf"
    width="<%= params.getWidth() %>" 
    height="<%= params.getHeight() %>"
    mime="<%= params.getMimeType() %>"/>
<!-- end image panel -->
<p>
<cewolf:imgurl 
	chartid="XYChart"    
	renderer="/cewolf"
    width="<%= params.getWidth() %>" 
    height="<%= params.getHeight() %>"
    mime="<%= params.getMimeType() %>"
    var="imgURL"/>
image URL: <%= imgURL %>
</TD>

<!-- end outermost table -->
</TR>
</TABLE>

</FORM>
</body>
</html>
