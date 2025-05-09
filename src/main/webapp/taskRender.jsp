<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.Task" %>
<%@ page import="com.at.entity.Resource" %>
<%@ page import="com.at.entity.Entry" %>
<%@ page import="com.at.entity.TaskStatus" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.at.utils.DateUtils" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Task Details</title>
		<link rel="stylesheet" href="style.css">
	</head>
<body>
	<% 
	Task task = (Task) request.getAttribute("task");
	boolean isNew = (task.getId() == null);
	String header = isNew ? "Add New Task" : "Edit Task: " + task.getTitle();
	List<String> errors = (List<String>) request.getAttribute("errors");
	%>
	
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='taskServlet.html';">BACK</button>
	</div>
	<h2><%= header %></h2>
	
	<% if (errors != null && !errors.isEmpty()) { %>
		<div class="error-box">
			<ul>
				<% for (String error : errors) { %>
					<li><%= error %></li>
				<% } %>
			</ul>
		</div>
	<% } %>
	
	<form action="TaskServlet" method="post">
		<input type="hidden" name="action" value="Save">
		<input type="hidden" name="idInput" value="<%= task.getId() != null ? task.getId() : "" %>">
		
		<table class="table2">
			<tr>
				<td>Title:</td>
				<td><input type="text" name="title" value="<%= task.getTitle() != null ? task.getTitle() : "" %>" required></td>
			</tr>
			<tr>
				<td>Owner:</td>
				<td>
					<select name="ownerId" required>
						<option value="">Select Owner</option>
						<% 
						List<Resource> resourceList = (List<Resource>) request.getAttribute("resourceList");
						if (resourceList != null) {
							for (Resource resource : resourceList) {
						%>
							<option value="<%= resource.getId() %>" 
								<%= task.getOwner() != null && task.getOwner().getId() != null && 
								    resource.getId() != null && resource.getId().equals(task.getOwner().getId()) ? "selected" : "" %>>
								<%= resource.getName() != null ? resource.getName() : "Resource " + resource.getId() %>
							</option>
						<%
							}
						}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>Assigned To:</td>
				<td>
					<select name="assignedToId">
						<option value="">Select Resource</option>
						<% 
						if (resourceList != null) {
							for (Resource resource : resourceList) {
						%>
							<option value="<%= resource.getId() %>" 
								<%= task.getAssignedTo() != null && task.getAssignedTo().getId() != null && 
								    resource.getId() != null && resource.getId().equals(task.getAssignedTo().getId()) ? "selected" : "" %>>
								<%= resource.getName() != null ? resource.getName() : "Resource " + resource.getId() %>
							</option>
						<%
							}
						}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>Related Entry:</td>
				<td>
					<select name="entryId">
						<option value="">None</option>
						<% 
						List<Entry> entryList = (List<Entry>) request.getAttribute("entryList");
						if (entryList != null) {
							for (Entry entry : entryList) {
						%>
							<option value="<%= entry.getId() %>" 
								<%= task.getEntry() != null && task.getEntry().getId() != null && 
								    entry.getId() != null && entry.getId().equals(task.getEntry().getId()) ? "selected" : "" %>>
								<%= entry.getName() %>
							</option>
						<%
							}
						}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><textarea name="description" rows="4" cols="50"><%= task.getDescription() != null ? task.getDescription() : "" %></textarea></td>
			</tr>
			<tr>
				<td>Task Status:</td>
				<td>
					<select name="taskStatus" required>
						<option value="">Select Status</option>
						<option value="ASSIGNED" <%= task.getTaskStatus() == TaskStatus.ASSIGNED ? "selected" : "" %>>Assigned</option>
						<option value="RECEIVEDANDINPROGRESS" <%= task.getTaskStatus() == TaskStatus.RECEIVEDANDINPROGRESS ? "selected" : "" %>>Received and In Progress</option>
						<option value="COMPLETEDBYRESOURCE" <%= task.getTaskStatus() == TaskStatus.COMPLETEDBYRESOURCE ? "selected" : "" %>>Completed by Resource</option>
						<option value="SIGNEDOFF" <%= task.getTaskStatus() == TaskStatus.SIGNEDOFF ? "selected" : "" %>>Signed Off</option>
						<option value="INTRANSFER" <%= task.getTaskStatus() == TaskStatus.INTRANSFER ? "selected" : "" %>>In Transfer</option>
						<option value="DECLINED" <%= task.getTaskStatus() == TaskStatus.DECLINED ? "selected" : "" %>>Declined</option>
						<option value="NEEDSDECISION" <%= task.getTaskStatus() == TaskStatus.NEEDSDECISION ? "selected" : "" %>>Needs Decision</option>
						<option value="SITUATIONDEMANDSYOURATTENTION" <%= task.getTaskStatus() == TaskStatus.SITUATIONDEMANDSYOURATTENTION ? "selected" : "" %>>Situation Demands Your Attention</option>
						<option value="PROBLEMMAYOCCUR" <%= task.getTaskStatus() == TaskStatus.PROBLEMMAYOCCUR ? "selected" : "" %>>Problem May Occur</option>
						<option value="ACKNOWLEDGED" <%= task.getTaskStatus() == TaskStatus.ACKNOWLEDGED ? "selected" : "" %>>Acknowledged</option>
						<option value="DELAYED" <%= task.getTaskStatus() == TaskStatus.DELAYED ? "selected" : "" %>>Delayed</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Created On:</td>
				<td><input type="datetime-local" name="createdOn" value="<%= task.getCreatedOn() != null ? DateUtils.calendarDisplayConverter(task.getCreatedOn(), "yyyy-MM-dd'T'HH:mm") : "" %>"></td>
			</tr>
			<tr>
				<td>Next Action:</td>
				<td><input type="datetime-local" name="nextAction" value="<%= task.getNextAction() != null ? DateUtils.calendarDisplayConverter(task.getNextAction(), "yyyy-MM-dd'T'HH:mm") : "" %>"></td>
			</tr>
			<tr>
				<td>Due On:</td>
				<td><input type="datetime-local" name="dueOn" value="<%= task.getDueOn() != null ? DateUtils.calendarDisplayConverter(task.getDueOn(), "yyyy-MM-dd'T'HH:mm") : "" %>"></td>
			</tr>
			<tr>
				<td>Completed On:</td>
				<td><input type="datetime-local" name="completedOn" value="<%= task.getCompletedOn() != null ? DateUtils.calendarDisplayConverter(task.getCompletedOn(), "yyyy-MM-dd'T'HH:mm") : "" %>"></td>
			</tr>
		</table>
		<br>
			<input type="submit" class="buttonsave" value="SAVE" />
	</form>
	
	<br>
	<div class="back-button-container bottom">
		<button class="button-back-minimal" onclick="location.href='taskServlet.html';">BACK</button>
	</div>
</body>
</html>