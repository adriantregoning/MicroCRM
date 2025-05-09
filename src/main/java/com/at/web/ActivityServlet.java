package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.at.entity.Activity;
import com.at.entity.ActivityType;
import com.at.entity.Entry;
import com.at.entity.Resource;
import com.at.entity.Task;

@WebServlet("/ActivityServlet")
public class ActivityServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public ActivityServlet() {
        super();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = getParameter(request, "action");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if (action==null || action.equals("")) {
			out.print("<html>NO action specified!</html>");
			
		} else if (action.equalsIgnoreCase("all") || action.equalsIgnoreCase("list")) {
            // Get all activities
            List<Activity> activities = getActivityController().getActivityAll();
            
            // Apply status filter
            String filterStatus = getParameter(request, "filterStatus");
            if (filterStatus != null) {
                if (filterStatus.equalsIgnoreCase("open")) {
                    activities = activities.stream()
                        .filter(activity -> !activity.isClosed())
                        .collect(Collectors.toList());
                } else if (filterStatus.equalsIgnoreCase("closed")) {
                    activities = activities.stream()
                        .filter(Activity::isClosed)
                        .collect(Collectors.toList());
                }
            }
            
            // Apply Task filter
            String filterTaskId = getParameter(request, "filterTaskId");
            if (filterTaskId != null && !filterTaskId.isEmpty()) {
                Long taskId = Long.parseLong(filterTaskId);
                activities = activities.stream()
                    .filter(a -> a.getTask() != null && a.getTask().getId().equals(taskId))
                    .collect(Collectors.toList());
            }
            
            // Apply Resource filter
            String filterResourceId = getParameter(request, "filterResourceId");
            if (filterResourceId != null && !filterResourceId.isEmpty()) {
                Long resourceId = Long.parseLong(filterResourceId);
                activities = activities.stream()
                    .filter(a -> a.getResource() != null && a.getResource().getId().equals(resourceId))
                    .collect(Collectors.toList());
            }
            
            // Apply Entry filter
            String filterEntryId = getParameter(request, "filterEntryId");
            if (filterEntryId != null && !filterEntryId.isEmpty()) {
                Long entryId = Long.parseLong(filterEntryId);
                activities = activities.stream()
                    .filter(a -> a.getEntry() != null && a.getEntry().getId().equals(entryId))
                    .collect(Collectors.toList());
            }
            
            // Apply Date filters - basic implementation
            try {
                // Start date from
                String startDateFrom = getParameter(request, "filterStartDateFrom");
                if (startDateFrom != null && !startDateFrom.isEmpty()) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDateFrom);
                    activities = activities.stream()
                        .filter(a -> a.getStartedOn() != null && a.getStartedOn().getTime().after(date))
                        .collect(Collectors.toList());
                }
                
                // Start date to
                String startDateTo = getParameter(request, "filterStartDateTo");
                if (startDateTo != null && !startDateTo.isEmpty()) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDateTo);
                    activities = activities.stream()
                        .filter(a -> a.getStartedOn() != null && a.getStartedOn().getTime().before(date))
                        .collect(Collectors.toList());
                }
                
                // Completed date from
                String completedDateFrom = getParameter(request, "filterCompletedDateFrom");
                if (completedDateFrom != null && !completedDateFrom.isEmpty()) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(completedDateFrom);
                    activities = activities.stream()
                        .filter(a -> a.getCompletedOn() != null && a.getCompletedOn().getTime().after(date))
                        .collect(Collectors.toList());
                }
                
                // Completed date to
                String completedDateTo = getParameter(request, "filterCompletedDateTo");
                if (completedDateTo != null && !completedDateTo.isEmpty()) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(completedDateTo);
                    activities = activities.stream()
                        .filter(a -> a.getCompletedOn() != null && a.getCompletedOn().getTime().before(date))
                        .collect(Collectors.toList());
                }
            } catch (Exception e) {
                // If date parsing fails, continue without this filter
            }
            
            // Apply TimeTaken filter
            try {
                String timeTakenFrom = getParameter(request, "filterTimeTakenFrom");
                if (timeTakenFrom != null && !timeTakenFrom.isEmpty()) {
                    float minTime = Float.parseFloat(timeTakenFrom);
                    activities = activities.stream()
                        .filter(a -> a.getTimeTaken() != null && a.getTimeTaken().floatValue() >= minTime)
                        .collect(Collectors.toList());
                }
                
                String timeTakenTo = getParameter(request, "filterTimeTakenTo");
                if (timeTakenTo != null && !timeTakenTo.isEmpty()) {
                    float maxTime = Float.parseFloat(timeTakenTo);
                    activities = activities.stream()
                        .filter(a -> a.getTimeTaken() != null && a.getTimeTaken().floatValue() <= maxTime)
                        .collect(Collectors.toList());
                }
            } catch (Exception e) {
                // If number parsing fails, continue without this filter
            }
            
            // Apply Billable filter
            String filterBillable = getParameter(request, "filterBillable");
            if (filterBillable != null && !filterBillable.isEmpty()) {
                boolean isBillable = Boolean.parseBoolean(filterBillable);
                activities = activities.stream()
                    .filter(a -> {
                    Boolean billableValue = a.getBillable();
                    return billableValue != null && billableValue.equals(isBillable);
                })
                    .collect(Collectors.toList());
            }
            
            // Set attributes for JSP
            request.setAttribute("activities", activities);
            request.setAttribute("activityTypeCtrl", getActivityTypeController());
            request.setAttribute("resourceCtrl", getResourceController());
            request.setAttribute("taskCtrl", getTaskController());
            request.setAttribute("entryCtrl", getEntryController());
            request.setAttribute("currentFilter", filterStatus != null ? filterStatus : "all");
            
            // Forward to JSP for display
            request.getRequestDispatcher("/activityShowAll.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("view")) {
            // Set up data for form
            Activity activity = new Activity();
            if (action.equalsIgnoreCase("view")) {
                Long id = getParameterAsLong(request, "idInput");
                activity = getActivityController().getActivityById(id);
            }
            
            // Get all necessary data for form dropdowns
            List<ActivityType> activityTypes = getActivityTypeController().getActivityTypesAll();
            List<Resource> resources = getResourceController().getResourcesAll();
            List<Task> tasks = getTaskController().getTaskAll();
            List<Entry> entries = getEntryController().getEntryAll();
            
            // Set attributes for JSP
            request.setAttribute("activity", activity);
            request.setAttribute("activityTypes", activityTypes);
            request.setAttribute("resources", resources);
            request.setAttribute("tasks", tasks);
            request.setAttribute("entries", entries);
            
            // Forward to JSP for rendering
            request.getRequestDispatcher("/activityRender.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("edit")) {
			Long id = getParameterAsLong(request, "idInput");
			Activity activity = getActivityController().getActivityById(id);
			
			// Only allow editing of open activities
			if (activity.isClosed()) {
				// Redirect to view mode if attempting to edit a closed activity
				response.sendRedirect("ActivityServlet?action=view&idInput=" + id);
				return;
			}
			
            // Get all necessary data for form dropdowns
            List<ActivityType> activityTypes = getActivityTypeController().getActivityTypesAll();
            List<Resource> resources = getResourceController().getResourcesAll();
            List<Task> tasks = getTaskController().getTaskAll();
            List<Entry> entries = getEntryController().getEntryAll();
            
            // Set attributes for JSP
            request.setAttribute("activity", activity);
            request.setAttribute("activityTypes", activityTypes);
            request.setAttribute("resources", resources);
            request.setAttribute("tasks", tasks);
            request.setAttribute("entries", entries);
            
            // Forward to JSP for rendering
            request.getRequestDispatcher("/activityRender.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("Save")) {
			Long id = getParameterAsLong(request, "idInput");
			String title = getParameter(request, "title");
			String description = getParameter(request, "description");
			Calendar startedOn = getParameterAsCalendar(request, "startedOn");
			Calendar completedOn = getParameterAsCalendar(request, "completedOn");
			Boolean billable = getParameterAsBoolean(request, "billable");
			BigDecimal timeTaken = getParameterAsBigDecimal(request, "timeTaken");
			Boolean closed = getParameterAsBoolean(request, "closed");
			
            // Get reference objects from their controllers
			ActivityType actType = getActivityTypeController().getActivityTypeById(getParameterAsLong(request, "actTypeId"));
			Task task = getTaskController().getTaskById(getParameterAsLong(request, "task"));
			Resource resource = getResourceController().getResourceById(getParameterAsLong(request, "resource")); 
			Entry entry = getEntryController().getEntryById(getParameterAsLong(request, "entry")); 	
			
			// Create or update the activity
			Activity activity;
			boolean isNew = (id == null);
			
			if (!isNew) {
			    activity = getActivityController().getActivityById(id);
			    
			    // Prevent updates to closed activities
			    if (activity.isClosed()) {
			        response.sendRedirect("ActivityServlet?action=view&idInput=" + id);
			        return;
			    }
			} else {
			    activity = new Activity();
			}
			
			// Set all the activity attributes
			activity.setId(id);
			activity.setActivityType(actType);
			activity.setTask(task);
			activity.setResource(resource);
			activity.setEntry(entry);
			activity.setTitle(title);					
			activity.setDescription(description);
			activity.setStartedOn(startedOn);
			activity.setCompletedOn(completedOn);
			activity.setBillable(billable);
			activity.setTimeTaken(timeTaken);
			activity.setClosed(closed);
			
			// Validate the activity
			List<String> errors = getActivityController().validateActivity(activity);
			
			if (errors.size() == 0) {
				// If no errors, save and go to show all
				getActivityController().save(activity);
				
				// Preserve filter parameters in redirect
				StringBuilder redirectUrl = new StringBuilder("ActivityServlet?action=list");
				
				// Add filter parameters if they exist
				String[] filterParams = {"filterStatus", "filterTaskId", "filterResourceId", "filterEntryId", 
				                        "filterStartDateFrom", "filterStartDateTo", "filterCompletedDateFrom", 
				                        "filterCompletedDateTo", "filterTimeTakenFrom", "filterTimeTakenTo", "filterBillable"};
				
				for (String param : filterParams) {
				    String value = getParameter(request, param);
				    if (value != null && !value.isEmpty()) {
				        redirectUrl.append("&").append(param).append("=").append(value);
				    }
				}
				
				response.sendRedirect(redirectUrl.toString());
			} else {
				// If errors, return to form with error messages
                // Get all necessary data for form dropdowns
                List<ActivityType> activityTypes = getActivityTypeController().getActivityTypesAll();
                List<Resource> resources = getResourceController().getResourcesAll();
                List<Task> tasks = getTaskController().getTaskAll();
                List<Entry> entries = getEntryController().getEntryAll();
                
                // Set attributes for JSP
                request.setAttribute("activity", activity);
                request.setAttribute("activityTypes", activityTypes);
                request.setAttribute("resources", resources);
                request.setAttribute("tasks", tasks);
                request.setAttribute("entries", entries);
                request.setAttribute("errors", errors);
                
                // Forward to JSP for rendering with errors
                request.getRequestDispatcher("/activityRender.jsp").forward(request, response);
			}
		} else {
			out.print("<html>INVALID action specified! " + action + "</html>");
		}
		out.close();
	}
}