package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.at.controller.EntryController;
import com.at.controller.ResourceController;
import com.at.entity.Entry;
import com.at.entity.EntryAuditTrail;
import com.at.entity.Resource;

@WebServlet("/EntryServlet")
public class EntryServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    public EntryServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        //System.out.println(">>> Action received: " + action);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();     

        if (action == null || action.trim().isEmpty()) {
            out.print("<html>NO action specified!</html>");
            return;

        } else if (action.equalsIgnoreCase("all")) { // SHOW ALL 
            List<Entry> entriesSent = getEntryController().getEntryAll();
            request.setAttribute("entriesSent", entriesSent);
            request.setAttribute("resourceCtrl", getResourceController());
            request.setAttribute("entryCtrl", getEntryController());
            request.getRequestDispatcher("/entryShowAll.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("add")) { // ADD NEW 
            List<Resource> resourceList = getResourceController().getResourcesAll();    
            if (resourceList != null && !resourceList.isEmpty()) {  
                request.setAttribute("resourceList", resourceList);
            }
            List<Entry> entryList = getEntryController().getEntryAll(); 
            if (entryList != null && !entryList.isEmpty()) {
                request.setAttribute("entryList", entryList);
            }
            request.setAttribute("entry", new Entry()); 
            request.getRequestDispatcher("/entryRender.jsp").forward(request, response);   
                                                                                
        } else if (action.equalsIgnoreCase("edit")) { // EDIT EXISTING 
            Long id = getParameterAsLong(request, "idInput");
            //System.out.println("Editing Entry ID: " + id);
            Entry entry = getEntryController().getEntryById(id);
            ResourceController rc = getResourceController();
            List<Resource> resourceList = rc.getResourcesAll();
            
            request.setAttribute("resourceList", resourceList);
            request.setAttribute("entry", entry);
            
            List<Entry> entryList = getEntryController().getEntryAll();
            request.setAttribute("entryList", entryList);
            
            EntryController entryController = getEntryController();
            if (entryController != null) {
                try {
                    List<EntryAuditTrail> entryAuditTrailList = entryController.getEntryAuditTrailByEntryId(id);
                    request.setAttribute("entryAuditTrailList", entryAuditTrailList);
                } catch (Exception e) {
                    System.out.println("Exception fetching audit trail: " + e.getMessage());
                }
            }
        
            request.getRequestDispatcher("/entryRender.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("Save")) { // SAVE 
            Long id = getParameterAsLong(request, "idInput");
            boolean isNew = (id == null);   
            
            Entry entry;
            
            if (!isNew) {
                entry = getEntryController().getEntryById(id);
            } else {
                entry = new Entry();
            }
            
            // Get parameters from request
            String name = getParameter(request, "name");
            Long authorId = getParameterAsLong(request, "authorId");
            Long parentId = getParameterAsLong(request, "parent");
            String referenceNumber = getParameter(request, "referenceNumber");
            String notes = getParameter(request, "notes");
            Boolean isGroup = getParameterAsBoolean(request, "isGroup");
            Boolean isPersonal = getParameterAsBoolean(request, "isPersonal");
            String alert = getParameter(request, "alert");
            Calendar createdOn = getParameterAsCalendar(request, "createdOn");
        
            Resource resource = getResourceController().getResourceById(authorId);
            Entry parentEntry = getEntryController().getEntryById(parentId);
            
            // Set entry attributes
            entry.setName(name);
            entry.setAuthor(resource);
            entry.setParent(parentEntry);
            entry.setReferenceNumber(referenceNumber);
            entry.setNotes(notes);
            entry.setGroup(isGroup);
            entry.setPersonal(isPersonal);
            entry.setAlert(alert);
            entry.setCreatedOn(createdOn);
            
            // Validate entry
            List<String> errors = getEntryController().validateEntry(entry);
            if (errors.size() == 0) {
                getEntryController().save(entry);

                // Always create audit trail in the servlet
                createAuditTrail(entry, authorId.intValue(), isNew);
                
                // Forward to show all entries
                List<Entry> entrySent = getEntryController().getEntryAll();
                request.setAttribute("entriesSent", entrySent);
                request.setAttribute("resourceCtrl", getResourceController());
                request.setAttribute("entryCtrl", getEntryController());
                request.getRequestDispatcher("/entryShowAll.jsp").forward(request, response);
            } else {
                // Forward to render entry with errors
                List<Resource> resourceList = getResourceController().getResourcesAll();
                request.setAttribute("resourceList", resourceList);
                List<Entry> entryList = getEntryController().getEntryAll();
                request.setAttribute("entryList", entryList);
                request.setAttribute("name", name);
                request.setAttribute("errors", errors);
                request.setAttribute("entry", entry);
                request.getRequestDispatcher("/entryRender.jsp").forward(request, response);
            }

        } else if (action.equalsIgnoreCase("viewAuditTrail")) {     // VIEWING AUDIT TRAIL 
            Long id = Long.parseLong(request.getParameter("idInput"));
            
            // Get the selected entry
            Entry selectedEntry = getEntryController().getEntryById(id);
            request.setAttribute("selectedEntry", selectedEntry);
            request.setAttribute("selectedEntryId", id);
            
            // Get all entries for the navigation
            List<Entry> entriesSent = getEntryController().getEntryAll();
            request.setAttribute("entriesSent", entriesSent);
            
            // Get the audit trail for the selected entry
            try {
                List<EntryAuditTrail> entryAuditTrailList = getEntryController().getEntryAuditTrailByEntryId(id);
                request.setAttribute("entryAuditTrailList", entryAuditTrailList);
            } catch (Exception e) {
                System.out.println("Error fetching audit trail: " + e.getMessage());
                e.printStackTrace();
            }
            
            request.getRequestDispatcher("/entryAuditTrail.jsp").forward(request, response);

        } else {
            out.print("<html>INVALID action specified! " + action + "</html>");
        }
    }

    private void createAuditTrail(Entry entry, int authorId, boolean isNew) {
        getEntryAuditTrailController().createAuditTrail(entry, authorId, isNew);
    }
    
}