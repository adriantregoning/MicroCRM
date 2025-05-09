<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.at.entity.Activity"%>
<%@ page import="com.at.entity.ActivityType"%>
<%@ page import="com.at.entity.Resource"%>
<%@ page import="com.at.entity.Task"%>
<%@ page import="com.at.entity.Entry"%>
<%@ page import="com.at.utils.DateUtils"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Activity</title>
        <link rel="stylesheet" href="style.css">
    </head>
<body>
    <div class="back-button-container top">
        <button class="button-back-minimal" onclick="location.href='ActivityServlet?action=all';">BACK</button>
    </div>

    <%
    // Check if the activity is closed or we are in view-only mode
    Activity act = (Activity) request.getAttribute("activity");
    boolean isViewOnly = act.isClosed() || "view".equalsIgnoreCase(request.getParameter("action"));
    String header = (act.getId() == null) ? "Add Activity" : (isViewOnly ? "View Activity" : "Edit Activity");
    
    // Display notification if activity is closed
    if (act.isClosed()) {
    %>
    <div class="error-box">
        <p>This activity is closed. You can only view it.</p>
    </div>
    <% } %>

    <h2><%= header %><%= act.getTitle() != null && !act.getTitle().isEmpty() ? ": " + act.getTitle() : "" %></h2>
    
    <%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if (errors != null && !errors.isEmpty()) {
    %>
    <div class="error-box">
        <ul>
            <% for (String err : errors) { %>
                <li><%= err %></li>
            <% } %>
        </ul>
    </div>
    <% } %>
    
    <form action="ActivityServlet" method="GET">
        <input type="hidden" name="idInput" value="<%= act.getId()==null ? "" : act.getId().toString() %>">
        
        <table class="table2">
            <!-- Title -->
            <tr>
                <td>Title:</td>
                <td>
                    <input type="text" id="title" name="title" 
                           value="<%= act.getTitle()==null ? "" : act.getTitle() %>" 
                           <%= isViewOnly ? "readonly" : "" %>>
                </td>
            </tr>
            
            <!-- Activity Type (references activity_type) -->
            <tr>
                <td>Activity Type:</td>
                <td>
                    <select name="actTypeId" <%= isViewOnly ? "disabled" : "" %>>
                    <%
                        List<ActivityType> actTypeList = (List<ActivityType>) request.getAttribute("activityTypes");
                        if(actTypeList != null && !actTypeList.isEmpty()) {
                            for(ActivityType activityType : actTypeList) { 
                                boolean isSelected = act.getActivityType() != null && 
                                                    act.getActivityType().getId().equals(activityType.getId());
                    %>
                        <option <%= isSelected ? "selected" : "" %> value="<%= activityType.getId() %>">
                            <%= activityType.getName() %>
                        </option>
                    <%
                            }
                        }
                    %>
                    </select>
                </td>
            </tr>
            
            <!-- Resource (references resource) -->
            <tr>
                <td>Resource:</td>
                <td>
                    <select name="resource" <%= isViewOnly ? "disabled" : "" %>>
                    <%
                        List<Resource> resourceList = (List<Resource>) request.getAttribute("resources");
                        if(resourceList != null && !resourceList.isEmpty()) {
                            for(Resource res : resourceList) {
                                boolean isSelected = act.getResource() != null && 
                                                   act.getResource().getId().equals(res.getId());
                    %>
                        <option <%= isSelected ? "selected" : "" %> value="<%= res.getId() %>">
                            <%= res.getFullName() %>
                        </option>
                    <%
                            }
                        }
                    %>
                    </select>
                </td>
            </tr>
            
            <!-- Task (references task) -->
            <tr>
                <td>Task:</td>
                <td>
                    <select name="task" <%= isViewOnly ? "disabled" : "" %>>
                    <%
                        List<Task> taskList = (List<Task>) request.getAttribute("tasks");
                        if(taskList != null && !taskList.isEmpty()) {
                            for(Task task : taskList) {
                                boolean isSelected = act.getTask() != null && 
                                                   act.getTask().getId().equals(task.getId());
                    %>
                        <option <%= isSelected ? "selected" : "" %> value="<%= task.getId() %>">
                            <%= task.getTitle() %>
                        </option>
                    <%
                            }
                        }
                    %>
                    </select>
                </td>
            </tr>
            
            <!-- Entry (references entry) -->
            <tr>
                <td>Entry:</td>
                <td>
                    <select name="entry" <%= isViewOnly ? "disabled" : "" %>>
                    <%
                        List<Entry> entryList = (List<Entry>) request.getAttribute("entries");
                        if(entryList != null && !entryList.isEmpty()) {
                            for(Entry entry : entryList) {
                                boolean isSelected = act.getEntry() != null && 
                                                   act.getEntry().getId().equals(entry.getId());
                    %>
                        <option <%= isSelected ? "selected" : "" %> value="<%= entry.getId() %>">
                            <%= entry.getName() %>
                        </option>
                    <%
                            }
                        }
                    %>
                    </select>
                </td>
            </tr>
            
            <!-- Description -->
            <tr>
                <td>Description:</td>
                <td>
                    <input type="text" id="description" name="description" 
                           value="<%= act.getDescription()==null ? "" : act.getDescription() %>" 
                           <%= isViewOnly ? "readonly" : "" %>>
                </td>
            </tr>
            
            <!-- Started On -->
            <tr>
                <td>Started On:</td>
                <td>
                    <input type="datetime-local" id="startedOn" name="startedOn" 
                           value="<%= act.getStartedOn()==null ? "" : DateUtils.calendarDisplayConverter(act.getStartedOn(), "yyyy-MM-dd HH:mm") %>" 
                           <%= isViewOnly ? "readonly" : "" %>>
                </td>
            </tr>
            
            <!-- Completed On -->
            <tr>
                <td>Completed On:</td>
                <td>
                    <input type="datetime-local" id="completedOn" name="completedOn" 
                           value="<%= act.getCompletedOn()==null ? "" : DateUtils.calendarDisplayConverter(act.getCompletedOn(), "yyyy-MM-dd HH:mm") %>" 
                           <%= isViewOnly ? "readonly" : "" %>>
                </td>
            </tr>
            
            <!-- Billable -->
            <tr>
                <td>Billable:</td>
                <td>
                    <input type="checkbox" id="billable" name="billable" 
                           <%= act.getBillable()==true ? "checked" : "" %> 
                           <%= isViewOnly ? "disabled" : "" %>>
                </td>
            </tr>
            
            <!-- Time Taken -->
            <tr>
                <td>Time Taken:</td>
                <td>
                    <input type="text" id="timeTaken" name="timeTaken" 
                           value="<%= act.getTimeTaken()==null ? "" : act.getTimeTaken().toString() %>" 
                           <%= isViewOnly ? "readonly" : "" %>>
                </td>
            </tr>
            
            <!-- Closed -->
            <tr>
                <td>Closed:</td>
                <td>
                    <input type="checkbox" id="closed" name="closed" 
                           <%= act.isClosed()==true ? "checked" : "" %> 
                           <%= isViewOnly ? "disabled" : "" %>>
                </td>
            </tr>
        </table>
        <br>
        <% if (!isViewOnly) { %>
            <input type="hidden" name="action" value="Save">
            <input type="submit" class="buttonsave" value="SAVE">
        <% } %>
    </form>
    
    <br>
    <div class="back-button-container bottom">
        <button class="button-back-minimal" onclick="location.href='ActivityServlet?action=all';">BACK</button>
    </div>
</body>
</html>