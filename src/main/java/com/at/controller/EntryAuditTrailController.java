package com.at.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import com.at.entity.Entry;
import com.at.entity.EntryAuditTrail;
import com.at.utils.HibernateUtil;
import com.at.utils.ValidationUtils;

public class EntryAuditTrailController extends BaseController {
	
	public EntryAuditTrailController() {
		super();
	}
	
	public EntryAuditTrailController(Connection c) {
		super();
		conn = c;
	}
	
	public List<String> validateEntryAuditTrail(EntryAuditTrail entAudTr) {
		List<String> errors = new ArrayList<String>();
		if (entAudTr == null) {
			throw new NullPointerException("Entry Audit Trail cannot be empty.");
		}
		// TODO ******
		if (entAudTr.getEntry() == null || entAudTr.getEntry().getId() == null
				|| entAudTr.getEntry().getId().equals(0L)) {
			errors.add("Entry Audit Trail Entry ID is required.");
		}

		if (ValidationUtils.isNullOrEmpty(entAudTr.getDescription())) {
			errors.add("Entry Audit Trail Description cannot be empty.");
		}
		
		if (ValidationUtils.isNullOrEmpty(entAudTr.getAuthorId())) {
			errors.add("Entry Audit Trail Author Id cannot be empty.");
		}
		
		if (entAudTr.getChangedOn()==null) {
			errors.add("Entry Audit Trail Changed On may not be null.");
		}
		return errors;
	}
	
	public EntryAuditTrail getEntryAuditTrailById(Long id) {
    TypedQuery<EntryAuditTrail> q = getSession().createQuery("from " + EntryAuditTrail.class.getName() + " where id = :id", EntryAuditTrail.class);
    q.setParameter("id", id);
    
    List<EntryAuditTrail> result = q.getResultList();
    if (!result.isEmpty()) {
        return result.get(0);
    } else {
        return null;
    	}
	}
	
	public List<EntryAuditTrail> getEntryAuditTrailAll() {
		try {
			TypedQuery<EntryAuditTrail> q = getSession().createQuery("from " + EntryAuditTrail.class.getName() + " order by id", EntryAuditTrail.class);
			
			return q.getResultList();
		} finally {
			getSession().close();
		}
	}
	
	public String getEntryAuditTrailFriendlyName(EntryAuditTrail entryAuditTrail) {
		if(entryAuditTrail!=null) {
			return entryAuditTrail.getName();
		}
		return "";
	}

	public void updateEntry(Entry entry) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>Updating entry with id: " + entry.getId());
		entry = (Entry) session.merge(entry);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Updated entry with id: " + entry.getId());
		session.getTransaction().commit();
	}

	public void saveEntryAuditTrail(EntryAuditTrail entryAuditTrail) {
		System.out.println("=== INSIDE saveEntryAuditTrail METHOD ===");
		
		try {
			System.out.println("Creating new Hibernate session");
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try {
				System.out.println("Beginning transaction");
				session.beginTransaction();
				
				System.out.println("Saving entryAuditTrail object: " + entryAuditTrail);
				session.save(entryAuditTrail);
				
				System.out.println("Committing transaction");
				session.getTransaction().commit();
				
				System.out.println("Transaction committed successfully");
			} catch (Exception e) {
				System.out.println("ERROR during transaction: " + e.getMessage());
				e.printStackTrace();
				if (session.getTransaction() != null && session.getTransaction().isActive()) {
					System.out.println("Rolling back transaction");
					session.getTransaction().rollback();
				}
			} finally {
				System.out.println("Closing session");
				session.close();
			}
		} catch (Exception e) {
			System.out.println("ERROR creating session: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void createAuditTrail(Entry entry, int authorId, boolean isNew) {
    if (entry == null || entry.getId() == null) return;
    
    // Create audit trail entry
    EntryAuditTrail auditTrail = new EntryAuditTrail();
    auditTrail.setEntry(entry);
    auditTrail.setDescription(isNew ? "Created new entry" : "Updated entry"); 
	/* Sets a description based on whether this is a new entry or an update:
	If isNew is true, sets "Created new entry". If isNew is false, sets "Updated entry"
	This uses a ternary operator (condition ? value-if-true : value-if-false) */ 
	
    auditTrail.setAuthorId(authorId);	//Sets the ID of the user who made the change
    
    // Use current timestamp
    Calendar now = Calendar.getInstance();
    auditTrail.setChangedOn(now);
    
    // Use the inherited save method from BaseController
    save(auditTrail);
    
    System.out.println("Successfully created audit trail entry for entry #" + entry.getId());
	}
}
