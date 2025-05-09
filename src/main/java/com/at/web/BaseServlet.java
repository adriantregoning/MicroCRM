package com.at.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.at.controller.ActivityController;
import com.at.controller.ActivityTypeController;
import com.at.controller.EntryAuditTrailController;
import com.at.controller.EntryController;
import com.at.controller.ResourceController;
import com.at.controller.ResourceTypeController;
import com.at.controller.TaskController;
import com.at.entity.Activity;
import com.at.entity.ActivityType;


public class BaseServlet extends HttpServlet {

	public ActivityController activityController;
	public ActivityTypeController activityTypeController;
	//public ResourceController resourceController;
	private static ResourceController resourceController;
	public ResourceTypeController resourceTypeController;
	public TaskController taskController;
	public EntryController entryController;
	public EntryAuditTrailController entryAuditTrailController;
	public ActivityType activityType;
	public Activity activity;

    public Long getParameterAsLong(HttpServletRequest req, String name) {
		System.out.println(">>> getParameterAsLong called for: " + name);	
		try {
			String paramValue = getParameter(req, name);
			System.out.println(">>> Parameter value: " + paramValue);	
			if (paramValue != null) {
				return Long.parseLong(paramValue);
			}
		} catch (Exception e) {
			System.out.println(">>> Exception in getParameterAsLong: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
    }
    
    public String getParameter(HttpServletRequest req, String name) {
        String p = req.getParameter(name);
        return p == null || p.trim().isEmpty() ? null : p.trim();
    }
    
    public Calendar getParameterAsCalendar(HttpServletRequest req, String name) {
        try{
        	if(getParameter(req, name) != null) {
        		String paramString = getParameter(req, name);
            	Calendar cal = Calendar.getInstance();
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            	cal.setTime(sdf.parse(paramString));
            	return cal;
        	}
        }catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
	  public boolean getParameterAsBoolean(HttpServletRequest req, String name) {
	  try{
		  //System.out.println("name >>>>>>>>>>> " + getParameter(req, name));
		  String billable = getParameter(req, name);
		  if(billable != null && (billable.equalsIgnoreCase("yes") || billable.equalsIgnoreCase("true") || billable.equalsIgnoreCase("on"))) {
			  return true;
		  }
	  	return false;
	  }catch (Exception e) {
	  	e.printStackTrace();
	      return false;
	  }
	}
    
    public BigDecimal getParameterAsBigDecimal(HttpServletRequest req, String name) {
        try{
        	if(getParameter(req, name) != null) {
        		return new BigDecimal(getParameter(req, name));
        	}
        }catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
	public ActivityTypeController getActivityTypeController() {
		if (activityTypeController == null) {
			activityTypeController = new ActivityTypeController();
		}
		return activityTypeController;
	}

	public void setActivityTypeController(ActivityTypeController activityTypeController) {
		this.activityTypeController = activityTypeController;
	}

	public ActivityController getActivityController() {
		if (activityController == null) {
			activityController = new ActivityController();
		}
		return activityController;
	}

	public void setActivityController(ActivityController activityController) {
		this.activityController = activityController;
	}

	public static ResourceController getResourceController() {
		if (resourceController == null) {
			resourceController = new ResourceController();
		}
		return resourceController;
	}

	public void setResourceController(ResourceController resourceController) {
		BaseServlet.resourceController = resourceController;
	}
	
	public ResourceTypeController getResourceTypeController() {
		if (resourceTypeController == null) {
			resourceTypeController = new ResourceTypeController();
		}
		return resourceTypeController;
	}

	public void setResourceTypeController(ResourceTypeController resourceTypeController) {
		this.resourceTypeController = resourceTypeController;
	}
	
	public TaskController getTaskController() {
		if (taskController == null) {
			taskController = new TaskController();
		}
		return taskController;
	}

	public void setTaskController(TaskController taskController) {
		this.taskController = taskController;
	}

	public EntryController getEntryController() {
		if (entryController == null) {
			entryController = new EntryController();
		}
		return entryController;
	}

	public void setEntryController(EntryController entryController) {
		this.entryController = entryController;
	}

	public EntryAuditTrailController getEntryAuditTrailController() {
		if (entryAuditTrailController == null) {
			entryAuditTrailController = new EntryAuditTrailController();
			System.out.println("DEBUG: Created new EntryAuditTrailController instance");
		}
		return entryAuditTrailController;
	}

	public void setEntryAuditTrailController(EntryAuditTrailController entryAuditTrailController) {
		this.entryAuditTrailController = entryAuditTrailController;
	}

	public ActivityType getActivityType() {
		if (activityType == null) {
			activityType = new ActivityType();
	}
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public Activity getActivity() {
		if (activity == null) {
			activity = new Activity();
	}
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
	
}
