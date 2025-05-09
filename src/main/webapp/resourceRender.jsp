<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.at.entity.Resource" %>
<%@ page import="com.at.entity.ResourceType" %>
<%@ page import="com.at.utils.DateUtils" %>
<%@ page import="java.util.Calendar" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Resource</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="back-button-container top">
        <button class="button-back-minimal" onclick="location.href='resourceServlet.html';">BACK</button>
    </div>
    
    <% 
        Resource resource = (Resource) request.getAttribute("resource");
        boolean isNew = (resource.getId() == null);
        String header = isNew ? "Create Resource" : "Edit Resource";
    %>
    
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
    <% } %>
    
    <form action="ResourceServlet" method="POST">
        <% 
            List<ResourceType> resourceTypes = (List<ResourceType>) request.getAttribute("resourceTypes");
        %>
        
        <table class="table2">
            <tr>
                <td>Resource Type:</td>
                <td>
                    <select name="resourceType">
                        <option value="">Select Resource Type</option>
                        <% 
                            for (ResourceType type : resourceTypes) {
                                boolean selected = resource.getResourceType() != null && 
                                    type.getId().equals(resource.getResourceType().getId());
                        %>
                        <option value="<%= type.getId() %>" <%= selected ? "selected" : "" %>><%= type.getName() %></option>
                        <% } %>
                    </select>
                </td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td>
                    <input type="text" name="firstName" value="<%= resource.getFirstName() == null ? "" : resource.getFirstName() %>">
                </td>
            </tr>
            <tr>
                <td>Surname:</td>
                <td>
                    <input type="text" name="surname" value="<%= resource.getSurname() == null ? "" : resource.getSurname() %>">
                </td>
            </tr>
            <tr>
                <td>Display Name:</td>
                <td>
                    <input type="text" name="displayName" value="<%= resource.getDisplayName() == null ? "" : resource.getDisplayName() %>">
                </td>
            </tr>
            <tr>
                <td>Username:</td>
                <td>
                    <input type="text" name="username" value="<%= resource.getUsername() == null ? "" : resource.getUsername() %>">
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <input type="password" name="password" value="<%= resource.getPassword() == null ? "" : resource.getPassword() %>">
                </td>
            </tr>
            <tr>
                <td>Force Password Change:</td>
                <td>
                    <input type="checkbox" name="passwordChangeForce" value="true" <%= resource.isPasswordChangeForce() ? "checked" : "" %>>
                </td>
            </tr>
            <tr>
                <td>System Admin:</td>
                <td>
                    <input type="checkbox" name="roleSystemAdmin" value="true" <%= resource.isRoleSystemAdmin() ? "checked" : "" %>>
                </td>
            </tr>
            <tr>
                <td>HR Role:</td>
                <td>
                    <input type="checkbox" name="roleHr" value="true" <%= resource.isRoleHr() ? "checked" : "" %>>
                </td>
            </tr>
            <tr>
                <td>Active:</td>
                <td>
                    <input type="checkbox" name="active" value="true" <%= resource.isActive() ? "checked" : "" %>>
                </td>
            </tr>
            <tr>
                <td>Internal:</td>
                <td>
                    <input type="checkbox" name="internal" value="true" <%= resource.isInternal() ? "checked" : "" %>>
                </td>
            </tr>
            <tr>
                <td>Last Logon:</td>
                <td>
                    <input type="date" name="lastLogon" value="<%= resource.getLastLogon() == null ? "" : DateUtils.calendarDisplayConverter(resource.getLastLogon(), "yyyy-MM-dd") %>">
                </td>
            </tr>
        </table>
        
        <input type="hidden" name="idInput" value="<%= resource.getId() == null ? "" : resource.getId() %>">
        <input type="hidden" name="action" value="Save">
        <br>
        <input type="submit" class="buttonsave" value="SAVE">
    </form>
    
    <br>
    <div class="back-button-container bottom">
        <button class="button-back-minimal" onclick="location.href='resourceServlet.html';">BACK</button>
    </div>
</body>
</html>