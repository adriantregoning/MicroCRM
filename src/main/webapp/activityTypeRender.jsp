<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.at.entity.ActivityType" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Activity Type</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="back-button-container top">
        <button class="button-back-minimal" onclick="location.href='activityTypeServlet.html';">BACK</button>
    </div>
    
    <% 
        ActivityType activityType = (ActivityType) request.getAttribute("activityType");
        boolean isNew = (activityType.getId() == null);
        String header = isNew ? "Add Activity Type" : "Edit Activity Type: " + activityType.getName();
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
    
    <form action="ActivityTypeServlet" method="POST">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="idInput" value="<%= activityType.getId() != null ? activityType.getId() : "" %>">
        
        <table class="table2">
            <tr>
                <td>Name:</td>
                <td>
                    <input type="text" name="actName" value="<%= activityType.getName() != null ? activityType.getName() : "" %>" required>
                </td>
            </tr>
        </table>
        <br>
        <input type="submit" class="buttonsave" value="SAVE">
    </form>
    
    <br>
    <div class="back-button-container bottom">
        <button class="button-back-minimal" onclick="location.href='activityTypeServlet.html';">BACK</button>
    </div>
</body>
</html>