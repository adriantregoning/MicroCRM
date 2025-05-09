<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.at.entity.ResourceType" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Resource Type</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="back-button-container top">
        <button class="button-back-minimal" onclick="location.href='resourceTypeServlet.html';">BACK</button>
    </div>
    
    <% 
    ResourceType resType = (ResourceType) request.getAttribute("resourceType");
    boolean isNew = (resType.getId() == null);
    String header = isNew ? "Add Resource Type" : "Edit Resource Type: " + resType.getName();
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
    
    <form action="ResourceTypeServlet" method="POST">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="idInput" value="<%= resType.getId() != null ? resType.getId() : "" %>">
        
        <table class="table2">
            <tr>
                <td>Name:</td>
                <td><input type="text" name="resName" value="<%= resType.getName() != null ? resType.getName() : "" %>" required></td>
            </tr>
        </table>
        <br>
        <input type="submit" class="buttonsave" value="SAVE" />
    </form>
    
    <br>
    <div class="back-button-container bottom">
        <button class="button-back-minimal" onclick="location.href='resourceTypeServlet.html';">BACK</button>
    </div>
</body>
</html>