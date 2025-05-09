<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.at.entity.Resource" %>
<%@ page import="com.at.controller.ResourceTypeController" %>
<%@ page import="com.at.utils.DateUtils" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>Resources</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="back-button-container top">
		<button class="button-back-minimal" onclick="location.href='resourceServlet.html';">BACK</button>
	</div>
    <h2>Resource Management</h2>
    <div class="add-button-container">
        <input type="button" class="buttonadd" onclick="location.href='ResourceServlet?action=add';" value="ADD NEW RESOURCE" />
    </div>
    <h4>Click Id to edit a particular resource</h4>
    
    <table class="table1">
        <tr>
            <th>Id</th>
            <th>Resource Type</th>
            <th>First Name</th>
            <th>Surname</th>
            <th>Display Name</th>
            <th>Username</th>
            <th>Password Change Force</th>
            <th>System Admin</th>
            <th>HR</th>
            <th>Active</th>
            <th>Internal</th>
            <th>Last Logon</th>
        </tr>
        <%
        List<Resource> resources = (List<Resource>) request.getAttribute("resources");
        ResourceTypeController resourceTypeCtrl = (ResourceTypeController) request.getAttribute("resourceTypeCtrl");
        
        for (Resource resource : resources) {
        %>
        <tr>
            <td><a href="ResourceServlet?action=edit&idInput=<%=resource.getId()%>"><%=resource.getId()%></a></td>
            <td><%=resourceTypeCtrl.getResourceTypeFriendlyName(resource.getResourceType())%></td>
            <td><%=resource.getFirstName() != null ? resource.getFirstName() : ""%></td>
            <td><%=resource.getSurname() != null ? resource.getSurname() : ""%></td>
            <td><%=resource.getDisplayName() != null ? resource.getDisplayName() : ""%></td>
            <td><%=resource.getUsername() != null ? resource.getUsername() : ""%></td>
            <td><%=resource.isPasswordChangeForce()%></td>
            <td><%=resource.isRoleSystemAdmin()%></td>
            <td><%=resource.isRoleHr()%></td>
            <td><%=resource.isActive()%></td>
            <td><%=resource.isInternal()%></td>
            <td><%=resource.getLastLogon() != null ? DateUtils.calendarDisplayConverter(resource.getLastLogon(), "yyyy-MM-dd") : ""%></td>
        </tr>
        <% 
        }
        %>
    </table>
    <br>
    <div class="back-button-container bottom">
        <button class="button-back-minimal" onclick="location.href='resourceServlet.html';">BACK</button>
    </div>
</body>
</html>