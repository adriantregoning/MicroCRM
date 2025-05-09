package com.at.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.at.entity.Entry;
import com.at.entity.EntryAuditTrail;
import com.at.utils.ValidationUtils;

public class EntryController extends BaseController {

	public EntryController() {
		super();
	}

	public EntryController(Connection c) {
		super();
		conn = c;
	}

	public List<EntryAuditTrail> getEntryAuditTrailByEntryId(Long id) {
    try {
        // Use TypedQuery with proper generics
        TypedQuery<EntryAuditTrail> q = getSession().createQuery("from " + EntryAuditTrail.class.getName() + " where entry.id = :id", EntryAuditTrail.class);
        q.setParameter("id", id);
        
        return q.getResultList();
    } finally {
        getSession().close();
    	}
	}
	
	public List<String> validateEntry(Entry entry) {
		List<String> errors = new ArrayList<String>();
		if (entry == null) {
			throw new NullPointerException("Entry cannot be null.");
		}

		if (ValidationUtils.isNullOrEmpty(entry.getAuthor())) {
			errors.add("Author ID/Resource is required.");
		}

		if (ValidationUtils.isNullOrEmpty(entry.getName())) {
			errors.add("Entry Name cannot be empty.");
		} else if (entry.getName().length() > 100) {
			errors.add("Entry Name cannot exceed 100 characters."); 
		} else if (entry.getName().length() < 3) {
			errors.add("Entry Name must be more than 3 characters.");
		}

		if (ValidationUtils.isNullOrEmpty(entry.getCreatedOn())) { 
			errors.add("Created On Value may not be null.");
		}
		return errors;
	}
	
	public Entry getEntryById(Long id) {
    TypedQuery<Entry> query = getSession().createQuery("from " + Entry.class.getName() + " where id = :id", Entry.class);
    query.setParameter("id", id);
    try {
        return query.getSingleResult();
    } catch (NoResultException e) {
        return null;
    	}
	}

	public List<Entry> getEntryAll() {
		TypedQuery<Entry> query = getSession().createQuery("from " + Entry.class.getName() + " order by id", Entry.class);
		return query.getResultList();
	}
	
	public String getEntryFriendlyName(Entry entry) {
		if(entry!=null) {
			return entry.getName();
		}
		return "";
	}

	public void updateEntry(Entry entry) {
		List<String> errors = validateEntry(entry);
		if (errors.isEmpty()) {
			getSession().update(entry);
		} else {
			// Handle validation errors
		}
	}
}
