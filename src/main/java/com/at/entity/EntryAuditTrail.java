package com.at.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.at.controller.ResourceController;
import com.at.utils.HibernateUtil;

public class EntryAuditTrail extends AbstractEntity implements ListItem {
	
	private Entry entry;
	private String description;
	private Integer authorId;		// was a String
	private Calendar changedOn;
	
	public EntryAuditTrail() {
			super();
		}
	
		public EntryAuditTrail(String description, String oldValue, String newValue, Entry entry, String authorIdStr) {
			this.description = description;
			this.entry = entry;
		
		// Convert String to Integer
		try {
			this.authorId = Integer.parseInt(authorIdStr);
		} catch (NumberFormatException e) {
			System.out.println("Invalid author ID format: " + authorIdStr);
			this.authorId = 0; // Default value
		}
		
		this.changedOn = Calendar.getInstance();
	}
	
	public EntryAuditTrail(String fieldName, String oldValue, String newValue, Entry entry, Integer authorId) {
	    this.entry = entry;
	    this.description = fieldName + " changed from " + oldValue + " to " + newValue;
	    this.authorId = authorId;
	    this.changedOn = Calendar.getInstance();
	}
	
	public EntryAuditTrail(String fieldName, String newValue, Entry entry, Integer authorId) {
	    this.entry = entry;
	    this.description = fieldName + " added with value " + newValue;
	    this.authorId = authorId;
	    this.changedOn = Calendar.getInstance();
	}

	public EntryAuditTrail(String field, Long oldValue, String newValue, Entry entry, Integer authorId) {
		this.entry = entry;
		this.authorId = authorId;
		new Date();
	}

	public EntryAuditTrail(String description, Entry entry, Integer authorId) {
		this.description = description;
		this.entry = entry;
		this.authorId = authorId;
		this.changedOn = Calendar.getInstance(); // Set the changedOn field to the current date and time
	}

	public void updateEntryAuditTrail(Entry entry) {
		EntryAuditTrail auditTrail = new EntryAuditTrail();
		auditTrail.setEntry(entry);
		auditTrail.setChangedOn(Calendar.getInstance());
		auditTrail.setAuthorId(entry.getAuthor());
		auditTrail.setDescription("Entry updated");

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(auditTrail);
		session.getTransaction().commit();
	}

	public void setAuthorId(Resource authorId) {
		this.authorId = Integer.parseInt(authorId.toString());
	}

	private ResourceController getResourceController() {
			return null;
    // implementation of getResourceController
	}
	
	public void setAuthorId(Long authorId) {
		getResourceController().getResourceById(authorId);
	}

	public void updateEntryAuditTrail(EntryAuditTrail entryAuditTrail) {
		List<String> errors = validateEntryAuditTrail(entryAuditTrail);
		if (errors.isEmpty()) {
			getSession().update(entryAuditTrail);
		} else {
			// Handle validation errors
		}
	}
	public List<String> validateEntryAuditTrail(EntryAuditTrail entryAuditTrail) {
    List<String> errors = new ArrayList<String>();
    if (entryAuditTrail == null) {
        errors.add("EntryAuditTrail cannot be null.");
    }
    
    return errors;
	}

	public Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public String getName() {
		return getDescription();
	}
	
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Calendar getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(Calendar changedOn) {
		this.changedOn = changedOn;
	}

	@Override
	public String toString() {
		return "EntryAuditTrail [entryId=" + entry + ", description=" + description + ", authorId=" + authorId
				+ ", changedOn=" + changedOn + "]";
	}
}
