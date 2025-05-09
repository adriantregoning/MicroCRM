<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.Entry" %>
<%@ page import="java.util.List" %>
<%@ page import="com.at.utils.DateUtils" %>
<%@ page import="com.at.controller.ResourceController" %>
<%@ page import="com.at.controller.EntryController" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Entry</title>
		<link rel="stylesheet" href="style.css">
	</head>
<body>
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='entryServlet.html';">BACK</button>
	</div>
		<h2>
		Entry Management
		</h2>
		<div class="add-button-container">
			<input type="button" class="buttonadd" onclick="location.href='EntryServlet?action=add';" value="ADD NEW ENTRY" />
		</div>
		<h4>
		Click Id to edit a particular row
		</h4>
		<table class="table1">
			<tr><th>Id</th>
			<th>Author</th>
			<th>Name</th>
			<th>Parent</th>
			<th>Reference Number</th>
			<th>Notes</th>
			<th>Is Group</th>
			<th>Is Personal</th>
			<th>Alert</th>
			<th>Created On</th>
			<th>Audit Trail</th></tr>
			<% 
			List<Entry> list = (List<Entry>) request.getAttribute("entriesSent");
			ResourceController resCtrl = (ResourceController) request.getAttribute("resourceCtrl");
			EntryController entryCtrl = (EntryController) request.getAttribute("entryCtrl");
			for (Entry e : list) {
			%>	
				<tr><td><a href="EntryServlet?action=edit&idInput=<%=e.getId()%>"> <%= e.getId() %></td>
				<td><%= resCtrl.getResourceFriendlyName(e.getAuthor())%> 		
				<td><%= e.getName()%></td> 
				<td><%= entryCtrl.getEntryFriendlyName(e.getParent())%></td>  
				<td><%= e.getReferenceNumber()%></td> 
				<td><%= e.getNotes()%></td>  
				<td><%= e.isGroup()%></td>  
				<td><%= e.isPersonal()%></td>  
				<td><%= e.getAlert() %></td> 
				<td><%= DateUtils.calendarDisplayConverter(e.getCreatedOn(), "yyyy-MM-dd")%></td>
				<td><a href="EntryServlet?action=viewAuditTrail&idInput=<%=e.getId()%>">View Audit</a></td>
				</tr>
			<%				
				}
			%>
		</table>
		<br>
		<div class="back-button-container bottom">
			<button class="button-back-minimal" onclick="location.href='entryServlet.html';">BACK</button>
		</div>
	</body>
</html>