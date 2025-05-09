<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.ActivityType" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Activity Type</title>
</head>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='activityTypeServlet.html';">BACK</button>
	</div>
		<h2>
		Activity Type Management
		</h2>
		<div class="add-button-container">
			<input type="button" class="buttonadd" onclick="location.href='ActivityTypeServlet?action=add';" value="ADD NEW ACTIVITY TYPE" />
		</div>
		<h4>
		Click Id to edit a particular row
		</h4>
		
		<table class="table1">
			<tr><th>Id</th><th>Activity Type</th></tr>
				<%
				List<ActivityType> list = (List<ActivityType>) request.getAttribute("actTypesSent");
				for (ActivityType e : list) {
				%>
				<tr><td><a href="ActivityTypeServlet?action=edit&idInput=<%=e.getId() %>"> <%= e.getId() %>
				</td><td><%= e.getName()%></td></tr>
				<% 
				}
				%>
		</table>
		<br>
		<div class="back-button-container bottom">
			<button class="button-back-minimal" onclick="location.href='activityTypeServlet.html';">BACK</button>
		</div>
</body>
</html>