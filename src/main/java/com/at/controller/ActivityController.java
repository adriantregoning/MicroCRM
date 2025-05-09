package com.at.controller;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.at.entity.Activity;
import com.at.utils.ValidationUtils;

public class ActivityController extends BaseController {

	public ActivityController() {
		super();
	}
	
	public ActivityController(Connection c) {
		super();
		conn = c;
	}
	
	public List<String> validateActivity(Activity act) {
		List<String> errors = new ArrayList<String>();
		if (act==null) {
			throw new NullPointerException("Activity cannot be null");
		}
		
		if (ValidationUtils.isNullOrEmpty(act.getTitle())) {
			errors.add("Activity Title cannot be empty");
		} else if (act.getTitle().length() > 150) {
			errors.add("Activity Title cannot exceed 150 characters."); 
		}
		
		if (act.getActivityType()==null || act.getActivityType().getId()==null) {
			errors.add("Activity Type is required");
		}
		
		if (act.getResource()==null || act.getResource().getId()==null) {
			errors.add("Resource/Author is required");
		}
		
		if (act.getTask()!=null) {
			System.out.println("TODO ActivityController.validateActivity() - Test to see if task number is valid/exists");
		}
		
		if (act.getEntry()==null) {
			errors.add("Entry Folder is required");
		} else {
			System.out.println("TODO ActivityController.validateActivity() - Test to see if entry is valid/exists");
		}
		
		if (ValidationUtils.isNullOrEmpty(act.getStartedOn())) {
			errors.add("Start On Value may not be null");
		}
		
		if (act.isClosed() && ValidationUtils.isNullOrEmpty(act.getCompletedOn())) {		
			errors.add("Cannot close activity without setting the Completed On Date");
		}
		
		if (!ValidationUtils.isNullOrEmpty(act.getTimeTaken())) {
			if (act.getTimeTaken().intValue()<0) {
				errors.add("Time Taken cannot be negative");	
			}
		}
		return errors;
	}

	public Activity getActivityById(Long id) {
    // Use TypedQuery instead of raw Query
    TypedQuery<Activity> q = getSession().createQuery("from " + Activity.class.getName() + " where id = :id", Activity.class);
    q.setParameter("id", id);
    
    List<Activity> result = q.getResultList();
    if (!result.isEmpty()) {
        return result.get(0);
		} else {
			return null;
		}
	}
	
	public List<Activity> getActivityAll() {
		try {
			// Use TypedQuery with proper generics
			TypedQuery<Activity> q = getSession().createQuery("from " + Activity.class.getName() + " order by id",  Activity.class);
			
			return q.getResultList();
		} finally {
			getSession().close();
		}
	}
	
}
