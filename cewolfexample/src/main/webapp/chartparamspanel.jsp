<TABLE>
    <TR>
        <TH colspan='2'>Chart Params</TH>
    </TR>
    <TR>
    <TD>title</TD>
    <TD>
        <INPUT type="TEXT" name="title" value='<jsp:getProperty name="params" property="title"/>'>
    </TD>
    </TR>
    <TR>
    <TD>width</TD>
    <TD>
        <INPUT type="TEXT" name="width" value='<jsp:getProperty name="params" property="width"/>'>
    </TD>
    </TR>
    <TR>
    <TD>height</TD>
    <TD>
        <INPUT type="TEXT" name="height" value='<jsp:getProperty name="params" property="height"/>'>
    </TD>
    </TR>
    <TR>
    <TD>antialias</TD>
    <TD>
        on&nbsp;<INPUT type="RADIO" name="antialias" <% if(params.getAntialias())%>checked<%;%> value='true'>&nbsp;off&nbsp;<INPUT type="RADIO" name="antialias" <% if(!params.getAntialias())%>checked<%;%> value='false'>
    </TD>
    </TR>
    <TR>
    <TD>legend</TD>
    <TD>
        on&nbsp;<INPUT type="RADIO" name="legend" <% if(params.isLegend())%>checked<%;%> value='true'>&nbsp;off&nbsp;<INPUT type="RADIO" name="legend" <% if(!params.isLegend())%>checked<%;%> value='false'>
    </TD>
    </TR>
    <TR>
    <TD>legend anchor</TD>
    <TD>
        north&nbsp;<INPUT type="RADIO" name="legendAnchor" <% if(params.isLegendAnchorSelected("north"))%>checked<%;%> value='north'>
        &nbsp;south&nbsp;<INPUT type="RADIO" name="legendAnchor" <% if(params.isLegendAnchorSelected("south"))%>checked<%;%> value='south'>
        &nbsp;west&nbsp;<INPUT type="RADIO" name="legendAnchor" <% if(params.isLegendAnchorSelected("west"))%>checked<%;%> value='west'>
        &nbsp;east&nbsp;<INPUT type="RADIO" name="legendAnchor" <% if(params.isLegendAnchorSelected("east"))%>checked<%;%> value='east'>
    </TD>
    </TR>
</TABLE>
