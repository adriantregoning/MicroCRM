<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.at.entity.Entry"%>
<%@ page import="com.at.entity.Resource"%>
<%@ page import="com.at.controller.EntryController" %>
<%@ page import="com.at.utils.DateUtils"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Entry</title>
		<link rel="stylesheet" href="style.css">
	</head>
<body>
	<% 
	Entry ent = (Entry) request.getAttribute("entry");
	boolean isNew = (ent.getId() == null);
	String header = isNew ? "Add New Entry" : "Edit Entry: " + ent.getName();
	%>
	
	<div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='entryServlet.html';">BACK</button>
	</div>
	<h2><%= header %></h2>
	<%
	List<String> errors = (List<String>) request.getAttribute("errors");
	if (errors != null && !errors.isEmpty()) { 
	%>
		<div class="error-box">
			<ul>
				<% for (String error : errors) { %>
					<li><%= error %></li>
				<% } %>
			</ul>
		</div>
	<%
	}
	%>
	
	<form action="EntryServlet" method="POST">
		<input type="hidden" name="action" value="Save">
		<input type="hidden" id="idInput" name="idInput" value="<%= ent.getId() != null ? ent.getId() : "" %>">
		
		<table class="table2">
			<tr>
				<td>Name:</td>
				<td>
					<input type="text" id="name" name="name" value="<%= ent.getName() != null ? ent.getName() : "" %>" required>
				</td>
			</tr>
			
			<!-- Author (references resource) -->
			<tr>
				<td>Author:</td>
				<td>
					<select name="authorId">
						<option value="">Select Author</option>
						<%
						List<Resource> resourceList = (List<Resource>) request.getAttribute("resourceList");
						if(resourceList != null && !resourceList.isEmpty()) {
							for(Resource res : resourceList) {
						%>
							<option value="<%= res.getId() %>" 
								<%= ent.getAuthor() != null && ent.getAuthor().getId() != null && 
									res.getId() != null && res.getId().equals(ent.getAuthor().getId()) ? "selected" : "" %>>
								<%= res.getFullName() %>
							</option>
						<%
							}
						}
						%>
					</select>
				</td>
			</tr>
			
			<!-- Parent (references entry) --> 
			<tr>
				<td>Parent:</td>
				<td>
					<select name="parent">
						<option value="">Select Parent</option>
						<%
						List<Entry> entryList = (List<Entry>) request.getAttribute("entryList");
						if(entryList != null) {
							for(Entry entP : entryList) {
						%>
							<option value="<%= entP.getId() %>" 
								<%= ent.getParent() != null && ent.getParent().getId() != null && 
									entP.getId() != null && entP.getId().equals(ent.getParent().getId()) ? "selected" : "" %>>
								<%= entP.getName() %>
							</option>
						<%
							}
						}
						%>
					</select>
				</td>
			</tr>
			
			<!-- Reference Number --> 
			<tr>
				<td>Reference Number:</td>
				<td>
					<input type="text" id="referenceNumber" name="referenceNumber" value="<%= ent.getReferenceNumber() != null ? ent.getReferenceNumber() : "" %>">
				</td>
			</tr>
			
			<!-- Note --> 
			<tr>
				<td>Notes:</td>
				<td>
					<textarea id="notes" name="notes" rows="4" cols="50"><%= ent.getNotes() != null ? ent.getNotes() : "" %></textarea>
				</td>
			</tr>
			
			<!-- Is Group --> 
			<tr>
				<td>Is Group:</td>
				<td>
					<input type="checkbox" id="isGroup" name="isGroup" <%= ent.isGroup() ? "checked" : "" %>>
				</td>
			</tr>
			
			<!-- Is Personal --> 
			<tr>
				<td>Is Personal:</td>
				<td>
					<input type="checkbox" id="isPersonal" name="isPersonal" <%= ent.isPersonal() ? "checked" : "" %>>
				</td>
			</tr>
			
			<!-- Alert --> 
			<tr>
				<td>Alert:</td>
				<td>
					<input type="text" id="alert" name="alert" value="<%= ent.getAlert() != null ? ent.getAlert() : "" %>">
				</td>
			</tr>
			
			<!-- Created On --> 
			<tr>
				<td>Created On:</td>
				<td>
					<input type="datetime-local" id="createdOn" name="createdOn" value="<%= ent.getCreatedOn() != null ? DateUtils.calendarDisplayConverter(ent.getCreatedOn(), "yyyy-MM-dd'T'HH:mm") : "" %>">
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" class="buttonsave" value="SAVE">
	</form>
	
	<br>
	<div class="back-button-container bottom">
		<button class="button-back-minimal" onclick="location.href='entryServlet.html';">BACK</button>
	</div>
</body>
</html>