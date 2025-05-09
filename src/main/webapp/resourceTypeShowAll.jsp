<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.ResourceType" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>Resource Type</title>
</head>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='resourceTypeServlet.html';">BACK</button>
	</div>
		<h2>
		Resource Type Management
		</h2>
		<div class="add-button-container">
			<input type="button" class="buttonadd" onclick="location.href='ResourceTypeServlet?action=add';" value="ADD NEW RESOURCE TYPE" />
		</div>	
		<h4>
		Click Id to edit a particular row
		</h4>
		<table class="table1">
			<tr><th>Id</th><th>Resource Type</th></tr>
				<%
				List<ResourceType> list = (List<ResourceType>) request.getAttribute("resTypesSent");
				for (ResourceType e : list) {
				%>
				<tr><td><a href="ResourceTypeServlet?action=edit&idInput=<%=e.getId() %>"> <%= e.getId() %>
				</td><td><%= e.getName()%></td></tr>
				<% 
				}
				%>
		</table>
		<br>
		<div class="back-button-container bottom">
			<button class="button-back-minimal" onclick="location.href='resourceTypeServlet.html';">BACK</button>
		</div>
</body>
</html>