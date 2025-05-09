package com.at.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.at.entity.ResourceType;
import com.at.utils.ValidationUtils;

public class ResourceTypeController extends BaseController {
	
	public ResourceTypeController() {
		super();
	}
	
	public ResourceTypeController(Connection c) {
		super();
		conn = c;
	}
	
	public List<String> validateResourceType(ResourceType resourceType) {
		List<String> errors = new ArrayList<String>();
		if (resourceType == null) {
			throw new NullPointerException("Resource Type cannot be null.");
		}

		if (ValidationUtils.isNullOrEmpty(resourceType.getName())) {
			errors.add("Resource Type Name cannot be empty.");
		} else if (resourceType.getName().length() > 30) {
			errors.add("Resource Type Name cannot exceed 30 characters.");
		} else if (resourceType.getName().length() < 3) {
			errors.add("Resource Type Name must be more than 3 characters.");
		}
		return errors;
	}
	
	public ResourceType getResourceTypeById(Long id) {
    TypedQuery<ResourceType> query = getSession().createQuery("from " + ResourceType.class.getName() + " where id = :id", ResourceType.class);
    query.setParameter("id", id);
    try {
        return query.getSingleResult();
    } catch (NoResultException e) {
        return null;
    	}
	}

	public List<ResourceType> getResourceTypeAll() {
		TypedQuery<ResourceType> query = getSession().createQuery("from " + ResourceType.class.getName() + " order by id", ResourceType.class);
		return query.getResultList();
	}
	
	public String getResourceTypeFriendlyName(ResourceType resourceType) {
		if(resourceType!=null) {
			return resourceType.getName();
		}
		return "";
	}

}
