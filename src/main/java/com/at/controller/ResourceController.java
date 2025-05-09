package com.at.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.at.entity.Resource;
import com.at.utils.ValidationUtils;

public class ResourceController extends BaseController {
	
	public ResourceController() {
		super();
	}
	
	public ResourceController(Connection c) {
		conn = c;
	}
	
	public List<String> validateResource(Resource res) {
		List<String> errors = new ArrayList<String>();
		if (res==null) {
			throw new NullPointerException("Resource cannot be null");
		}
		
		if (res.getResourceType()==null || res.getResourceType().getId()==null) {
			errors.add("Resource Type ID is required");
		}
		
		if (ValidationUtils.isNullOrEmpty(res.getFirstName())) {
			errors.add("First Name cannot be empty");
		} else if (res.getFirstName().length() > 50) {
			errors.add("First Name cannot exceed 50 characters."); 
		}
		
		if (ValidationUtils.isNullOrEmpty(res.getSurname())) {
			errors.add("Surname cannot be empty");
		} else if (res.getSurname().length() > 50) {
			errors.add("Surname cannot exceed 50 characters."); 
		}
		
		if (ValidationUtils.isNullOrEmpty(res.getDisplayName())) {
			errors.add("Display Name cannot be empty");
		} else if (res.getDisplayName().length() > 50) {
			errors.add("Display Name cannot exceed 50 characters."); 
		}
		
		if (ValidationUtils.isNullOrEmpty(res.getUsername())) {
			errors.add("Username cannot be empty");
		} else if (res.getUsername().length() > 30) {
			errors.add("Username cannot exceed 30 characters."); 
		}
		
		if (ValidationUtils.isNullOrEmpty(res.getPassword())) {
			errors.add("Password cannot be empty");
		} else if (res.getPassword().length() > 30) {
			errors.add("Password cannot exceed 30 characters."); 
		}
		return errors;
	}

	public Resource getResourceById(Long id) {
    TypedQuery<Resource> query = getSession().createQuery("from " + Resource.class.getName() + " where id = :id", Resource.class);
    query.setParameter("id", id);
    try {
        return query.getSingleResult();
    } catch (NoResultException e) {
        return null;
    	}
	}

	public List<Resource> getResourcesAll() {
		TypedQuery<Resource> query = getSession().createQuery("from " + Resource.class.getName() + " order by id", Resource.class);
		return query.getResultList();
	}

	public String getResourceFriendlyName(Resource resource) {
		if(resource!=null) {
			return resource.getDisplayName();
		}
		return "";
	}
}
