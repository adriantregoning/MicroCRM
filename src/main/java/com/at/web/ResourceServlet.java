package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.at.entity.Resource;

@WebServlet("/ResourceServlet")
public class ResourceServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    
    public ResourceServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = getParameter(request, "action");
        if (action == null || action.equals("")) {
            out.print("<html>NO action specified!</html>");
            
        } else if (action.equalsIgnoreCase("all") || action.equalsIgnoreCase("list")) {
            List<Resource> resources = getResourceController().getResourcesAll();
            
            request.setAttribute("resources", resources);
            request.setAttribute("resourceTypeCtrl", getResourceTypeController());
            
            request.getRequestDispatcher("/resourceShowAll.jsp").forward(request, response);
            
        } else if (action.equalsIgnoreCase("view")) {
            Long id = getParameterAsLong(request, "idInput");
            
            Resource resource = getResourceController().getResourceById(id);
            
            request.setAttribute("resource", resource);
            request.setAttribute("resourceTypeCtrl", getResourceTypeController());
            
            request.getRequestDispatcher("/resourceView.jsp").forward(request, response);
            
        } else if (action.equalsIgnoreCase("edit")) {
            Long id = getParameterAsLong(request, "idInput");
            Resource resource = getResourceController().getResourceById(id);
            
            request.setAttribute("resource", resource);
            request.setAttribute("resourceTypes", getResourceTypeController().getResourceTypeAll());
            
            request.getRequestDispatcher("/resourceRender.jsp").forward(request, response);
            
        } else if (action.equalsIgnoreCase("add")) {
            Resource resource = new Resource();
            
            request.setAttribute("resource", resource);
            request.setAttribute("resourceTypes", getResourceTypeController().getResourceTypeAll());
            
            request.getRequestDispatcher("/resourceRender.jsp").forward(request, response);
            
        } else if (action.equalsIgnoreCase("Save")) {
            Long id = getParameterAsLong(request, "idInput");
            Long resourceTypeId = getParameterAsLong(request, "resourceType");
            String firstName = getParameter(request, "firstName");
            String surname = getParameter(request, "surname");
            String displayName = getParameter(request, "displayName");
            String username = getParameter(request, "username");
            String password = getParameter(request, "password");
            Boolean passwordChangeForce = getParameterAsBoolean(request, "passwordChangeForce");
            Boolean roleSystemAdmin = getParameterAsBoolean(request, "roleSystemAdmin");
            Boolean roleHr = getParameterAsBoolean(request, "roleHr");
            Boolean active = getParameterAsBoolean(request, "active");
            Boolean internal = getParameterAsBoolean(request, "internal");
            Calendar lastLogon = getParameterAsCalendar(request, "lastLogon");
            
            Resource resource;
            boolean isNew = (id == null);
            
            if (!isNew) {
                resource = getResourceController().getResourceById(id);
            } else {
                resource = new Resource();
            }
            
            resource.setId(id);
            resource.setResourceType(getResourceTypeController().getResourceTypeById(resourceTypeId));
            resource.setFirstName(firstName);
            resource.setSurname(surname);
            resource.setDisplayName(displayName);
            resource.setUsername(username);
            resource.setPassword(password);
            resource.setPasswordChangeForce(passwordChangeForce);
            resource.setRoleSystemAdmin(roleSystemAdmin);
            resource.setRoleHr(roleHr);
            resource.setActive(active);
            resource.setInternal(internal);
            resource.setLastLogon(lastLogon);
            
            List<String> errors = getResourceController().validateResource(resource);
            
            if (errors.size() == 0) {
                getResourceController().save(resource);
                response.sendRedirect("ResourceServlet?action=list");
            } else {
                request.setAttribute("resource", resource);
                request.setAttribute("resourceTypes", getResourceTypeController().getResourceTypeAll());
                request.setAttribute("errors", errors);
                
                request.getRequestDispatcher("/resourceRender.jsp").forward(request, response);
            }
            
        } else {
            out.print("<html>INVALID action specified! " + action + "</html>");
        }
    }

}