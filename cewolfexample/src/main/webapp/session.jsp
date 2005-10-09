<%@page import="java.util.*"%>
<%
if(request.getParameter("purge_session") != null){
	session.invalidate();
	request.getSession(true);
}
%>
<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css"></head>
<BODY bgcolor="#DDE8F2">
<H1>Session content</H1>
<p>
<table border=1>
<%
System.gc();
Enumeration names = session.getAttributeNames();
while(names.hasMoreElements()){
  String name = (String)names.nextElement();
%>
<TR>
<TD>
<% out.write(name); %>
</TD>
<TD>
<% out.println(name +"\t" + session.getAttribute(name)); %>
</TD>
</TR>
<%
}
%>
</TABLE>
<FORM>
<INPUT TYPE="submit" VALUE="Reload">
<INPUT TYPE="submit" VALUE="Purge Session" NAME="purge_session">
</FORM> 
</body>
</html>
