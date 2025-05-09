package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.at.entity.ActivityType;

@WebServlet("/ActivityTypeServlet")
public class ActivityTypeServlet extends BaseServlet{
	
	public ActivityTypeServlet() {
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
			
		} else if (action.equalsIgnoreCase("all")) {			// goes to "show all"
			//goToShowAll(out);		// OLD
			List<ActivityType> actTypesSent = getActivityTypeController().getActivityTypesAll();
			request.setAttribute("actTypesSent", actTypesSent);
			request.getRequestDispatcher("/activityTypeShowAll.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("view")) {			// goes to "select by id"
			Long id = Long.parseLong(request.getParameter("idInput"));
			//goToShowActivityType(out, id);	// OLD
			ActivityType actTypeSent = getActivityTypeController().getActivityTypeById(id);
			request.setAttribute("actTypeSent", actTypeSent);
			request.getRequestDispatcher("/activityTypeById.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("add")) {			// for inserting a new activity type
			//renderActivityType(out, new ActivityType(), null); 	// OLD
			request.setAttribute("activityType", new ActivityType());
			request.getRequestDispatcher("/activityTypeRender.jsp").forward(request, response);
		
		} else if (action.equalsIgnoreCase("edit")) {			// for editing
			Long id = getParameterAsLong(request, "idInput");
			ActivityType activityType = getActivityTypeController().getActivityTypeById(id);
			//renderActivityType(out, activityType, null);		// OLD
			request.setAttribute("activityType", activityType);
			request.getRequestDispatcher("/activityTypeRender.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("save")) {
			ActivityType at = null;
			Long id = getParameterAsLong(request, "idInput");
			if (id==null) {
				// New
				at = new ActivityType();
			} else {
				at = getActivityTypeController().getActivityTypeById(id); 
			}
			
			String name = getParameter(request, "actName");
			at.setName(name);  // Set the name BEFORE validation
			
			List<String> errors = getActivityTypeController().validateActivityType(at);
			if (errors.size()==0) {
				getActivityTypeController().save(at);
				//goToShowAll(out);		OLD
				List<ActivityType> actTypesSent = getActivityTypeController().getActivityTypesAll();
				request.setAttribute("actTypesSent", actTypesSent);
				request.getRequestDispatcher("/activityTypeShowAll.jsp").forward(request, response);
			} else {
				//renderActivityType(out, at, errors); 			
				request.setAttribute("name", name);
				request.setAttribute("errors", errors);
				request.setAttribute("activityType", at);
				request.getRequestDispatcher("/activityTypeRender.jsp").forward(request, response);
			}
		
		} else {
			out.print("<html>INVALID action specified! " + action + "</html>");
		}
		out.close();
	}

}
