package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.at.entity.ResourceType;

@WebServlet("/ResourceTypeServlet")
public class ResourceTypeServlet extends BaseServlet{
	
	public ResourceTypeServlet() {
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
			
		} else if (action.equalsIgnoreCase("all")) {		// goes to "show all"
			List<ResourceType> resTypesSent = getResourceTypeController().getResourceTypeAll();
			request.setAttribute("resTypesSent", resTypesSent);
			request.getRequestDispatcher("/resourceTypeShowAll.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("view")) {		// goes to "select by id"
			Long id = Long.parseLong(request.getParameter("idInput"));
			ResourceType resTypeSent = getResourceTypeController().getResourceTypeById(id);
			request.setAttribute("resTypeSent", resTypeSent);
			request.getRequestDispatcher("/resourceTypeById.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("add")) {		// for inserting a new resource type
			request.setAttribute("resourceType", new ResourceType());
			request.getRequestDispatcher("/resourceTypeRender.jsp").forward(request, response);
		
		} else if (action.equalsIgnoreCase("edit")) {		// for editing
			Long id = getParameterAsLong(request, "idInput");
			ResourceType resourceType = getResourceTypeController().getResourceTypeById(id);
			request.setAttribute("resourceType", resourceType);
			request.getRequestDispatcher("/resourceTypeRender.jsp").forward(request, response);
			
		} else if (action.equalsIgnoreCase("save")) {
					
			ResourceType rt = null;
			Long id =  getParameterAsLong(request, "idInput");
			if (id==null) {
				// New
				rt = new ResourceType();
			} else {
				rt = getResourceTypeController().getResourceTypeById(id); 
			}
			
			String name = getParameter(request, "resName");
			
			rt.setName(name);
			List<String> errors = getResourceTypeController().validateResourceType(rt);
			if (errors.size()==0) {
				getResourceTypeController().save(rt);
				List<ResourceType> resTypesSent = getResourceTypeController().getResourceTypeAll();
				request.setAttribute("resTypesSent", resTypesSent);
				request.getRequestDispatcher("/resourceTypeShowAll.jsp").forward(request, response);
			} else {
				request.setAttribute("errors", errors);
		        request.setAttribute("resourceType", rt);
				request.getRequestDispatcher("/resourceTypeRender.jsp").forward(request, response);
			}
		
		} else {
			out.print("<html>INVALID action specified! " + action + "</html>");
		}
		out.close();
	}


}
