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
  <tr><td nowrap><a href="overlay1<%= versionPostfix %>.jsp" target="subcontent">Overlaid 1</a></td></tr>
  <tr><td nowrap><a href="overlay2<%= versionPostfix %>.jsp" target="subcontent">Overlaid 2</a></td></tr>
  <tr><td nowrap><a href="combined1<%= versionPostfix %>.jsp" target="subcontent">Combined<br>horizontal</a></td></tr>
  <tr><td nowrap><a href="combined2<%= versionPostfix %>.jsp" target="subcontent">Combined<br>vertical</a></td></tr>
</table>
</body>
</html>