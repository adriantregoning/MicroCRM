<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.at.entity.EntryAuditTrail" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Entry Audit Trail Render</title>
</head>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<h2>
		Entry Audit Trail (to delete this is the entryAuditTrailRender.jsp file) 6 MAY 2025
	</h2>
	<%-- NOT NEEDED AT ALL :-) NOT NEEDED AT ALL :-) NOT NEEDED AT ALL :-) NOT NEEDED AT ALL :-) NOT NEEDED AT ALL :-)
	<% 
		List<String> errors = (List<String>) request.getAttribute("errors");
		if (errors!=null && errors.size()>0) {
	        for (String err : errors) {
   	%>
	<br>Error:  <%= err %>
	<%
			}
		}
	%>
	<form action="EntryAuditTrail" method="POST">
	<span class="p1">NAME: </span>
	<%
		EntryAuditTrail entryAuditTrail = (EntryAuditTrail) request.getAttribute("entryAuditTrail");
		String printName =  entryAuditTrail.getName()==null ? "" : entryAuditTrail.getName(); 
	%>
		<input type="text" id="entryAuditTrailName" name="entryAuditTrailName" value="<%= printName %>" class="">
		<br>
		<br>
		<% 
			String idLong = entryAuditTrail.getId()==null ? "" : entryAuditTrail.getId().toString();
		%>
		<input type="hidden" name="idInput" value="<%= idLong %>">
		<input type="submit" class="buttonback" name="action" value="save">
		</form>
		<input type="button" input class="buttonback" onclick="location.href='entryAuditTrailServlet.html';" value="BACK" />
		--%>
</body>
</html>