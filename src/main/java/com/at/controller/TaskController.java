package com.at.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.at.entity.Task;
import com.at.utils.ValidationUtils;

public class TaskController extends BaseController { 
	
	public TaskController() {
		super();
	}
	
	public TaskController(Connection c) {
		conn = c;
	}
	
	public List<String> validateTask(Task task) {
		List<String> errors = new ArrayList<String>();
		if (task==null) {
			throw new NullPointerException("Task cannot be null");
		}
		
		if (task.getEntry()==null) {
			errors.add("Entry Folder is required");
		} else {
			System.out.println("TODO ActivityController.validateActivity() - Test to see if entry is valid/exists");
		}
		
		if (task.getOwner()==null || task.getOwner().getId()==null) {
			errors.add("Owner/Resource is required");
		}
		
		if (task.getAssignedTo()==null || task.getAssignedTo().getId()==null) {
			errors.add("Assigned To/Resource is required");
		}
		
		if (ValidationUtils.isNullOrEmpty(task.getTitle())) {
			errors.add("Task Title cannot be empty");
		} else if (task.getTitle().length() > 150) {
			errors.add("Task cannot exceed 150 characters."); 
		}
		
		if (ValidationUtils.isNullOrEmpty(task.getTaskStatus())) {
			errors.add("Task Status may not be null");
		}
		
		if (ValidationUtils.isNullOrEmpty(task.getCreatedOn())) {
			errors.add("Created On date may not be null");
		}
		
		if (ValidationUtils.isNullOrEmpty(task.getNextAction())) {
			errors.add("Next Action date may not be null");
		}
		
		if (ValidationUtils.isNullOrEmpty(task.getDueOn())) {
			errors.add("Due On date may not be null");
		}
		
		return errors;
	}
	
	public Task getTaskById(Long id) {
    TypedQuery<Task> q = getSession().createQuery("from " + Task.class.getName() + " where id = :id", Task.class);
    q.setParameter("id", id);
    
    List<Task> result = q.getResultList();
    if (!result.isEmpty()) {
        return result.get(0);
    } else {
        return null;
   	 }
	}
	
	public List<Task> getTaskAll() {
		try {
			// Use TypedQuery with proper generics
			TypedQuery<Task> q = getSession().createQuery("from " + Task.class.getName() + " order by id", Task.class);
			
			return q.getResultList();
		} finally {
			getSession().close();
		}
	}
	
	public String getTaskFriendlyName(Task task) {
		if(task!=null) {
			return task.getTitle();
		}
		return "";
	}

}
