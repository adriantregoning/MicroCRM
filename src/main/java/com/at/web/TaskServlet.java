package com.at.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.at.entity.Entry;
import com.at.entity.Resource;
import com.at.entity.Task;
import com.at.entity.TaskStatus;

@WebServlet("/TaskServlet")
public class TaskServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public TaskServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (action == null || action.trim().isEmpty()) {
			out.print("<html>NO action specified!</html>");
			return;

		} else if (action.equalsIgnoreCase("all")) { // SHOW ALL TASKS
			Long assignedToFilter = getParameterAsLong(request, "assignedToFilter");
			
			List<Task> tasksSent;
			
			if (assignedToFilter != null) {
				// Filter tasks by assigned user
				tasksSent = getTasksByAssignedTo(assignedToFilter);
			} else {
				tasksSent = getTaskController().getTaskAll();
			}
			
			// Initialize lazy-loaded relationships for each task to avoid LazyInitializationException
			initializeTaskRelationships(tasksSent);
			
			request.setAttribute("tasksSent", tasksSent);
			request.setAttribute("resourceCtrl", getResourceController());
			request.setAttribute("entryCtrl", getEntryController());
			request.setAttribute("taskCtrl", getTaskController());
			request.getRequestDispatcher("/taskShowAll.jsp").forward(request, response);

		} else if (action.equalsIgnoreCase("add")) { // ADD NEW TASK
			List<Resource> resourceList = getResourceController().getResourcesAll();
			if (resourceList != null && !resourceList.isEmpty()) {
				request.setAttribute("resourceList", resourceList);
			}

			List<Entry> entryList = getEntryController().getEntryAll();
			if (entryList != null && !entryList.isEmpty()) {
				request.setAttribute("entryList", entryList);
			}

			Task task = new Task();
			// Set default values
			task.setCreatedOn(Calendar.getInstance());

			request.setAttribute("task", task);
			request.getRequestDispatcher("/taskRender.jsp").forward(request, response);

		} else if (action.equalsIgnoreCase("edit")) { // EDIT EXISTING TASK
			Long id = getParameterAsLong(request, "idInput");
			if (id == null) {
				out.print("<html>Error: No task ID specified for edit action.</html>");
				return;
			}
			
			Task task = getTaskController().getTaskById(id);
			if (task == null) {
				out.print("<html>Error: Task with ID " + id + " not found.</html>");
				return;
			}
			
			// Get resource and entry lists for dropdowns
			List<Resource> resourceList = getResourceController().getResourcesAll();
			List<Entry> entryList = getEntryController().getEntryAll();
			
			request.setAttribute("task", task);
			request.setAttribute("resourceList", resourceList);
			request.setAttribute("entryList", entryList);
			
			request.getRequestDispatcher("/taskRender.jsp").forward(request, response);

		} else if (action.equalsIgnoreCase("Save")) { // SAVE TASK
			Long id = getParameterAsLong(request, "idInput");
			boolean isNew = (id == null);
			Task task;
			
			String title = getParameter(request, "title");
			Long ownerId = getParameterAsLong(request, "ownerId");
			Long assignedToId = getParameterAsLong(request, "assignedToId");
			Long entryId = getParameterAsLong(request, "entryId");
			String description = getParameter(request, "description");
			String taskStatusStr = getParameter(request, "taskStatus");
			TaskStatus taskStatus = taskStatusStr != null && !taskStatusStr.isEmpty()
					? TaskStatus.valueOf(taskStatusStr)
					: null;
			Calendar createdOn = getParameterAsCalendar(request, "createdOn");
			if (createdOn == null) {
				createdOn = Calendar.getInstance(); // Default to current time if not set
			}
			Calendar nextAction = getParameterAsCalendar(request, "nextAction");
			Calendar dueOn = getParameterAsCalendar(request, "dueOn");
			Calendar completedOn = getParameterAsCalendar(request, "completedOn");
			
			Session session = getTaskController().getSession();
			Transaction tx = null;
			
			try {
				tx = session.beginTransaction();
				
				if (!isNew) {
					task = (Task) session.get(Task.class, id);
					if (task == null) {
						task = new Task();
						task.setId(id);
					}
				} else {
					task = new Task();
				}
				
				task.setTitle(title);
				task.setDescription(description);
				task.setTaskStatus(taskStatus);
				task.setCreatedOn(createdOn);
				task.setNextAction(nextAction);
				task.setDueOn(dueOn);
				task.setCompletedOn(completedOn);
				
				Resource owner = null;
				Resource assignedTo = null;
				Entry entry = null;
				
				if (ownerId != null) {
					owner = (Resource) session.get(Resource.class, ownerId);
				}
				
				if (assignedToId != null) {
					assignedTo = (Resource) session.get(Resource.class, assignedToId);
				}
				
				if (entryId != null) {
					entry = (Entry) session.get(Entry.class, entryId);
				}
				
				task.setOwner(owner);
				task.setAssignedTo(assignedTo);
				task.setEntry(entry);
				
				List<String> errors = getTaskController().validateTask(task);
				
				if (errors.size() == 0) {
					session.saveOrUpdate(task);
					tx.commit();
					
					List<Task> tasksSent = getTaskController().getTaskAll();
					request.setAttribute("tasksSent", tasksSent);
					request.setAttribute("resourceCtrl", getResourceController());
					request.setAttribute("entryCtrl", getEntryController());
					request.setAttribute("taskCtrl", getTaskController());
					request.getRequestDispatcher("/taskShowAll.jsp").forward(request, response);
				} else {
					// Roll back if validation failed
					if (tx != null) {
						tx.rollback();
					}
					
					List<Resource> resourceList = getResourceController().getResourcesAll();
					List<Entry> entryList = getEntryController().getEntryAll();
					request.setAttribute("resourceList", resourceList);
					request.setAttribute("entryList", entryList);
					request.setAttribute("title", title);
					request.setAttribute("errors", errors);
					request.setAttribute("task", task);
					request.getRequestDispatcher("/taskRender.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// Roll back on error
				if (tx != null) {
					tx.rollback();
				}
				
				List<String> errors = new ArrayList<>();
				errors.add("Error saving task: " + e.getMessage());
				
				// Create a temporary task for display
				Task tempTask = new Task();
				if (!isNew) {
					tempTask.setId(id);
				}
				tempTask.setTitle(title);
				tempTask.setDescription(description);
				tempTask.setTaskStatus(taskStatus);
				tempTask.setCreatedOn(createdOn);
				tempTask.setNextAction(nextAction);
				tempTask.setDueOn(dueOn);
				tempTask.setCompletedOn(completedOn);
				
				List<Resource> resourceList = getResourceController().getResourcesAll();
				List<Entry> entryList = getEntryController().getEntryAll();
				request.setAttribute("resourceList", resourceList);
				request.setAttribute("entryList", entryList);
				request.setAttribute("title", title);
				request.setAttribute("errors", errors);
				request.setAttribute("task", tempTask);
				request.getRequestDispatcher("/taskRender.jsp").forward(request, response);
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}

		} else {
			out.print("<html>INVALID action specified! " + action + "</html>");
		}
	}

	/**
	 * Gets tasks filtered by assigned user
	 * 
	 * @param assignedToId The ID of the user tasks are assigned to
	 * @return List of tasks assigned to the specified user
	 */
	private List<Task> getTasksByAssignedTo(Long assignedToId) {
		Session session = getTaskController().getSession();
		try {
			// Create a query to filter tasks by assignedTo with join fetch to load relationships eagerly
			String hql = "FROM " + Task.class.getName() + " t " +
						"LEFT JOIN FETCH t.owner " +
						"LEFT JOIN FETCH t.assignedTo " +
						"LEFT JOIN FETCH t.entry " +
						"WHERE t.assignedTo.id = :assignedToId " +
						"ORDER BY t.id";
						
			return session.createQuery(hql, Task.class)
						.setParameter("assignedToId", assignedToId)
						.getResultList();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

		/**
	 * Initializes lazy-loaded relationships for a list of tasks
	 * to prevent LazyInitializationException in the JSP
	 * 
	 * @param tasks List of tasks to initialize
	 */
	private void initializeTaskRelationships(List<Task> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			return;
		}
		
		// Force initialization of relationships while session is still open
		for (Task task : tasks) {
			if (task.getOwner() != null) {
				// Touch the object to force loading
				task.getOwner().getId();
				if (task.getOwner().getFirstName() != null) {
					task.getOwner().getFirstName();
				}
				if (task.getOwner().getSurname() != null) {
					task.getOwner().getSurname();
				}
			}
			
			if (task.getAssignedTo() != null) {
				// Touch the object to force loading
				task.getAssignedTo().getId();
				if (task.getAssignedTo().getFirstName() != null) {
					task.getAssignedTo().getFirstName();
				}
				if (task.getAssignedTo().getSurname() != null) {
					task.getAssignedTo().getSurname();
				}
			}
			
			if (task.getEntry() != null) {
				// Touch the object to force loading
				task.getEntry().getId();
				if (task.getEntry().getName() != null) {
					task.getEntry().getName();
				}
			}
		}
	}
}