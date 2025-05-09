<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.Task" %>
<%@ page import="com.at.entity.TaskStatus" %>
<%@ page import="java.util.List" %>
<%@ page import="com.at.utils.DateUtils" %>
<%@ page import="com.at.controller.ResourceController" %>
<%@ page import="com.at.controller.EntryController" %>
<%@ page import="com.at.controller.TaskController" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Tasks</title>
		<link rel="stylesheet" href="style.css">
	</head>
<body>
		<div class="back-button-container top">
			<button class="button-back-minimal" onclick="location.href='taskServlet.html';">BACK</button>
		</div>
		<h2>
		Task Management
		</h2>
		<div class="add-button-container">
			<input type="button" class="buttonadd" onclick="location.href='TaskServlet?action=add';" value="ADD NEW TASK" />
			<!-- Filter container -->
			<div class="filter-container">
				<form action="TaskServlet" method="get" style="padding: 0;">
					<input type="hidden" name="action" value="all">
					<label for="assignedToFilter">Filter by Assigned To:</label>
					<select name="assignedToFilter" id="assignedToFilter">
						<option value="">All Users</option>
						<% 
						ResourceController resourceCtrl = (ResourceController) request.getAttribute("resourceCtrl");
						List<com.at.entity.Resource> allResources = resourceCtrl.getResourcesAll();
						
						// Get the current filter value, if any
						String currentFilter = request.getParameter("assignedToFilter");
						
						for (com.at.entity.Resource resource : allResources) {
						%>
							<option value="<%= resource.getId() %>" 
								<%= (currentFilter != null && currentFilter.equals(resource.getId().toString())) ? "selected" : "" %>>
								<%= resource.getName() %>
							</option>
						<% } %>
					</select>
					<input type="submit" value="Filter" class="buttonfilter">
				</form>
			</div>
		</div>
		<h4>
		Click Id to edit a particular task
		</h4>
		<table class="table1">
			<tr>
				<th>Id</th>
				<th>Title</th>
				<th>Owner</th>
				<th>Assigned To</th>
				<th>Entry</th>
				<th>Description</th>
				<th>Status</th>
				<th>Created On</th>
				<th>Next Action</th>
				<th>Due On</th>
				<th>Completed On</th>
			</tr>
			<% 
			List<Task> list = (List<Task>) request.getAttribute("tasksSent");
			ResourceController resCtrl = (ResourceController) request.getAttribute("resourceCtrl");
			EntryController entryCtrl = (EntryController) request.getAttribute("entryCtrl");
			
			for (Task t : list) {
			%>	
				<tr>
					<td><a href="TaskServlet?action=edit&idInput=<%=t.getId()%>"> <%= t.getId() %></a></td>
					<td><%= t.getTitle() != null ? t.getTitle() : "" %></td> 
					<td><%= t.getOwner() != null ? resCtrl.getResourceFriendlyName(t.getOwner()) : "Not Assigned" %></td>
					<td><%= t.getAssignedTo() != null ? resCtrl.getResourceFriendlyName(t.getAssignedTo()) : "Not Assigned" %></td>
					<td><%= t.getEntry() != null ? entryCtrl.getEntryFriendlyName(t.getEntry()) : "None" %></td>  
					<td><%= t.getDescription() != null ? t.getDescription() : "" %></td>
					<td><%= t.getTaskStatus() != null ? t.getTaskStatus().getName() : "Not Set" %></td>  
					<td><%= t.getCreatedOn() != null ? DateUtils.calendarDisplayConverter(t.getCreatedOn(), "yyyy-MM-dd") : "-" %></td>
					<td><%= t.getNextAction() != null ? DateUtils.calendarDisplayConverter(t.getNextAction(), "yyyy-MM-dd") : "-" %></td>
					<td><%= t.getDueOn() != null ? DateUtils.calendarDisplayConverter(t.getDueOn(), "yyyy-MM-dd") : "-" %></td>  
					<td><%= t.getCompletedOn() != null ? DateUtils.calendarDisplayConverter(t.getCompletedOn(), "yyyy-MM-dd") : "-" %></td>
				</tr>
			<%				
			}
			%>
		</table>
		<div class="back-button-container bottom">
			<button class="button-back-minimal" onclick="location.href='taskServlet.html';">BACK</button>
		</div>
</body>
</html>