package com.at.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.at.entity.ActivityType;
import com.at.utils.ValidationUtils;

public class ActivityTypeController extends BaseController {
	
	public ActivityTypeController() {
		super();
	}
	
	public ActivityTypeController(Connection c) {
		super();
		conn = c;
	}
	
	public List<String> validateActivityType(ActivityType activityType) {	
		List<String> errors = new ArrayList<String>();
		if (activityType == null) {
			throw new NullPointerException("Activity Type cannot be empty.");
		}

		if (ValidationUtils.isNullOrEmpty(activityType.getName())) {
			errors.add("Activity Type Name cannot be empty.");
		} else if (activityType.getName().length() > 50) {
			errors.add("Activity Type Name cannot exceed 50 characters.");
		} else if (activityType.getName().length() < 3) {
			errors.add("Activity Type Name must be more than 3 characters.");
		}
		return errors;
	}

	public ActivityType getActivityTypeById(Long id) {
		return (ActivityType) getSession().get(ActivityType.class, id);

	}
	
	public List<ActivityType> getActivityTypesAll() {
    TypedQuery<ActivityType> q = getSession().createQuery("from " + ActivityType.class.getName() + " order by id", ActivityType.class);
    return q.getResultList();
	}
	
	public String getActivityTypeFriendlyName(ActivityType activityType) {
		if(activityType!=null) {
			return activityType.getName();
		}
		return "";
	}
	
}
