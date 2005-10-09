<%@page contentType="text/html"%>
<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css">
</head>
<body class="menu">
<% 
String versionPostfix = "";
String specInfo = javax.servlet.jsp.JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion();
if(specInfo.indexOf("1.1") >= 0){
  versionPostfix = "-1.1";
}
//System.out.println(specInfo);
%>
<table border="0" cellpadding="5">
  <tr>
    <td nowrap><a href="cewolfset<%= versionPostfix %>.jsp" target="content">Cewolf Set</a></td>
    <td nowrap><a href="tutorial<%= versionPostfix %>.jsp" target="content">Tutorial</a></td>
    <td nowrap><a href="testpage<%= versionPostfix %>.jsp" target="content">Testpage</a></td>
    <td nowrap><a href="complex/index.html" target="content">Complex Charts</a></td>
    <td width="100%">&nbsp;</td>
    <td nowrap><a href="http://cewolf.sourceforge.net" target="new"><img src="img/cewolf_logo.gif" alt="logo" width="211" height="80" border="0"></a></td>
  </tr>
</table>
</body>
</html>