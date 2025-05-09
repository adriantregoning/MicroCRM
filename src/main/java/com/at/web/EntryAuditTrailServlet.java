package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.at.entity.EntryAuditTrail;

@WebServlet("/EntryAuditTrailServlet")
public class EntryAuditTrailServlet extends BaseServlet{
	
	public EntryAuditTrailServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = getParameter(request, "action");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if (action==null || action.equals("")) {
			out.print("<html>NO action specified!</html>");
			
		} else if (action.equalsIgnoreCase("all")) {		// goes to "show all"
			List<EntryAuditTrail> entryAuditTrailTypesSent = getEntryAuditTrailController().getEntryAuditTrailAll();
			request.setAttribute("entryAuditTrailTypesSent", entryAuditTrailTypesSent);
			request.getRequestDispatcher("/entryAuditTrailShowAll.jsp").forward(request, response);	
		} else {
			out.print("<html>INVALID action specified! " + action + "</html>");
		}
		out.close();
	}
	
}
