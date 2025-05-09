<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.at.entity.Activity"%>
<%@ page import="com.at.entity.ActivityType"%>
<%@ page import="com.at.entity.Resource"%>
<%@ page import="com.at.entity.Task"%>
<%@ page import="com.at.entity.Entry"%>
<%@ page import="com.at.controller.ActivityTypeController"%>
<%@ page import="com.at.controller.ResourceController"%>
<%@ page import="com.at.controller.TaskController"%>
<%@ page import="com.at.controller.EntryController"%>
<%@ page import="com.at.utils.DateUtils"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Activity List</title>
		<link rel="stylesheet" href="style.css">
	</head>
<body>
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='activityServlet.html';">BACK</button>
	</div>
	<h2>Activity</h2>
	<div class="add-button-container">
		<input type="button" class="buttonadd" onclick="location.href='ActivityServlet?action=add';" value="ADD NEW ACTIVITY" />
	</div>
	<!-- Enhanced Filter options -->
	<div class="filter-container">
		<form action="ActivityServlet" method="get" id="filterForm">
			<input type="hidden" name="action" value="list">
			
			<div class="filter-row">
				<!-- Status Filter -->
				<div class="filter-group">
					<label for="filterStatus">Status:</label>
					<select name="filterStatus" id="filterStatus">
						<option value="all" <%= "all".equals(request.getAttribute("currentFilter")) || request.getAttribute("currentFilter") == null ? "selected" : "" %>>All Activities</option>
						<option value="open" <%= "open".equals(request.getAttribute("currentFilter")) ? "selected" : "" %>>Open Activities</option>
						<option value="closed" <%= "closed".equals(request.getAttribute("currentFilter")) ? "selected" : "" %>>Closed Activities</option>
					</select>
				</div>
				
				<!-- Task Filter - Dropdown -->
				<div class="filter-group">
					<label for="filterTaskId">Task:</label>
					<select name="filterTaskId" id="filterTaskId">
						<option value="">All Tasks</option>
						<%
						TaskController taskController = (TaskController) request.getAttribute("taskCtrl");
						if (taskController != null) {
							List<Task> tasks = taskController.getTaskAll();
							for (Task task : tasks) {
								String selected = String.valueOf(task.getId()).equals(request.getParameter("filterTaskId")) ? "selected" : "";
						%>
						<option value="<%= task.getId() %>" <%= selected %>><%= task.getId() %> - <%= task.getTitle() %></option>
						<%
							}
						}
						%>
					</select>
				</div>
				
				<!-- Resource Filter - Dropdown -->
				<div class="filter-group">
					<label for="filterResourceId">Resource:</label>
					<select name="filterResourceId" id="filterResourceId">
						<option value="">All Resources</option>
						<%
						ResourceController resourceController = (ResourceController) request.getAttribute("resourceCtrl");
						if (resourceController != null) {
							List<Resource> resources = resourceController.getResourcesAll();
							for (Resource resource : resources) {
								String selected = String.valueOf(resource.getId()).equals(request.getParameter("filterResourceId")) ? "selected" : "";
						%>
						<option value="<%= resource.getId() %>" <%= selected %>><%= resource.getName() %></option>
						<%
							}
						}
						%>
					</select>
				</div>
				
				<!-- Entry Filter - Dropdown -->
				<div class="filter-group">
					<label for="filterEntryId">Entry:</label>
					<select name="filterEntryId" id="filterEntryId">
						<option value="">All Entries</option>
						<%
						EntryController entryController = (EntryController) request.getAttribute("entryCtrl");
						if (entryController != null) {
							List<Entry> entries = entryController.getEntryAll();
							for (Entry entry : entries) {
								String selected = String.valueOf(entry.getId()).equals(request.getParameter("filterEntryId")) ? "selected" : "";
						%>
						<option value="<%= entry.getId() %>" <%= selected %>><%= entry.getId() %> - <%= entry.getName() %></option>
						<%
							}
						}
						%>
					</select>
				</div>
			</div>
			
			<div class="filter-row">
				<!-- Date Range Filters -->
				<div class="filter-group">
					<label for="filterStartDateFrom">Started Between:</label>
					<div style="display: flex; gap: 5px; align-items: center;">
						<input type="date" name="filterStartDateFrom" id="filterStartDateFrom" 
							value="<%= request.getParameter("filterStartDateFrom") != null ? request.getParameter("filterStartDateFrom") : "" %>">
						<span>and</span>
						<input type="date" name="filterStartDateTo" id="filterStartDateTo"
							value="<%= request.getParameter("filterStartDateTo") != null ? request.getParameter("filterStartDateTo") : "" %>">
					</div>
				</div>
				
				<div class="filter-group">
					<label for="filterCompletedDateFrom">Completed Between:</label>
					<div style="display: flex; gap: 5px; align-items: center;">
						<input type="date" name="filterCompletedDateFrom" id="filterCompletedDateFrom"
							value="<%= request.getParameter("filterCompletedDateFrom") != null ? request.getParameter("filterCompletedDateFrom") : "" %>">
						<span>and</span>
						<input type="date" name="filterCompletedDateTo" id="filterCompletedDateTo"
							value="<%= request.getParameter("filterCompletedDateTo") != null ? request.getParameter("filterCompletedDateTo") : "" %>">
					</div>
				</div>
			</div>
			
			<div class="filter-row">
				<!-- Time Taken Range Filter (Double/Decimal) -->
				<div class="filter-group">
					<label for="filterTimeTakenFrom">Time Taken Between:</label>
					<div style="display: flex; gap: 5px; align-items: center;">
						<input type="number" name="filterTimeTakenFrom" id="filterTimeTakenFrom" step="0.01" min="0"
							value="<%= request.getParameter("filterTimeTakenFrom") != null ? request.getParameter("filterTimeTakenFrom") : "" %>"
							placeholder="Min">
						<span>and</span>
						<input type="number" name="filterTimeTakenTo" id="filterTimeTakenTo" step="0.01" min="0"
							value="<%= request.getParameter("filterTimeTakenTo") != null ? request.getParameter("filterTimeTakenTo") : "" %>"
							placeholder="Max">
					</div>
				</div>
				
				<!-- Billable Filter -->
				<div class="filter-group">
					<label for="filterBillable">Billable:</label>
					<select name="filterBillable" id="filterBillable">
						<option value="" <%= request.getParameter("filterBillable") == null || "".equals(request.getParameter("filterBillable")) ? "selected" : "" %>>All</option>
						<option value="true" <%= "true".equals(request.getParameter("filterBillable")) ? "selected" : "" %>>Yes</option>
						<option value="false" <%= "false".equals(request.getParameter("filterBillable")) ? "selected" : "" %>>No</option>
					</select>
				</div>
			</div>
			
			<div class="filter-actions">
				<input type="submit" class="buttonfilter" value="Apply Filters">
				<input type="button" class="buttonclear" value="Clear Filters" onclick="clearFilters()">
			</div>
		</form>
	</div>
	
	<h4>Click Id to edit a particular row (only open activities can be edited)</h4>
	
	<table class="table1">
		<tr>
			<th>Id</th>
			<th>Activity Type Id</th>
			<th>Author Id</th>
			<th>Task Id</th>
			<th>Entry Id</th>
			<th>Title</th>
			<th>Description</th>
			<th>Started On</th>
			<th>Completed On</th>
			<th>Billable</th>
			<th>Time Taken</th>
			<th>Closed</th>
		</tr>
		<%
		try {
			List<Activity> list = (List<Activity>) request.getAttribute("activities");
			ActivityTypeController activityTypeCtrl = (ActivityTypeController) request.getAttribute("activityTypeCtrl");
			ResourceController resourceCtrl = (ResourceController) request.getAttribute("resourceCtrl");
			TaskController taskCtrl = (TaskController) request.getAttribute("taskCtrl");
			EntryController entryCtrl = (EntryController) request.getAttribute("entryCtrl");
			
			if (list != null) {
				for (Activity e : list) {
					boolean isClosed = e.isClosed();
		%>
		<tr class="<%= isClosed ? "closed-row" : "" %>">
			<td>
				<% if (isClosed) { %>
					<%= e.getId() %>
				<% } else { %>
					<a href="ActivityServlet?action=edit&idInput=<%= e.getId() %>"><%= e.getId() %></a>
				<% } %>
			</td>
			<td><%= activityTypeCtrl.getActivityTypeFriendlyName(e.getActivityType()) %></td>
			<td><%= resourceCtrl.getResourceFriendlyName(e.getResource()) %></td>
			<td><%= taskCtrl.getTaskFriendlyName(e.getTask()) %></td>
			<td><%= entryCtrl.getEntryFriendlyName(e.getEntry()) %></td>
			<td><%= e.getTitle() %></td>
			<td><%= e.getDescription() %></td>
			<td><%= DateUtils.calendarDisplayConverter(e.getStartedOn(), "yyyy-MM-dd") %></td>
			<td><%= DateUtils.calendarDisplayConverter(e.getCompletedOn(), "yyyy-MM-dd") %></td>
			<td><%= e.getBillable() %></td>
			<td><%= e.getTimeTaken() %></td>
			<td><%= e.isClosed() %></td>
		</tr>
		<%
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		%>
	</table>
	<br>
	<div class="back-button-container bottom">
		<button class="button-back-minimal" onclick="location.href='activityServlet.html';">BACK</button>
	</div>

	<script>
		function clearFilters() {
			// Reset all form inputs
			document.getElementById('filterStatus').value = 'all';
			document.getElementById('filterTaskId').value = '';
			document.getElementById('filterResourceId').value = '';
			document.getElementById('filterEntryId').value = '';
			document.getElementById('filterStartDateFrom').value = '';
			document.getElementById('filterStartDateTo').value = '';
			document.getElementById('filterCompletedDateFrom').value = '';
			document.getElementById('filterCompletedDateTo').value = '';
			document.getElementById('filterTimeTakenFrom').value = '';
			document.getElementById('filterTimeTakenTo').value = '';
			document.getElementById('filterBillable').value = '';
			
			// Submit the form to refresh the page with cleared filters
			document.getElementById('filterForm').submit();
		}
	</script>
</body>
</html>