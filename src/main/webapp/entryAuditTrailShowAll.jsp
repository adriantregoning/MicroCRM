<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.EntryAuditTrail" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Entry Audit Trail</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='entryAuditTrailServlet.html';">BACK</button>
	</div>
		<h2>
		Entry Audit Trail 
		</h2>
		<table class="table1">
			<tr><th>Id</th><th>Entry Audit Trail</th></tr>
				<%
				List<EntryAuditTrail> list = (List<EntryAuditTrail>) request.getAttribute("entryAuditTrailTypesSent");
				for (EntryAuditTrail e : list) {
				%>
				<tr><td><%= e.getId() %></td><td><%= e.getName()%></td></tr>
				<% 
				}
				%>
		</table>
		<br>
		<div class="back-button-container bottom">
			<button class="button-back-minimal" onclick="location.href='entryAuditTrailServlet.html';">BACK</button>
		</div>
</body>
</html>