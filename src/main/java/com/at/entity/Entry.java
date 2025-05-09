package com.at.entity;

import java.util.Calendar;

public class Entry extends AbstractEntity implements ListItem {

	private String name;
	private Entry parent;
	private String referenceNumber;
	private String notes;
	private boolean isGroup;
	private boolean isPersonal;
	private String alert;
	private Resource author;
	private Calendar createdOn;

	public Entry() {
		super();
	}
	
	public Entry(Long id) {
		super();
		setId(id);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean group) {
		isGroup = group;
	}

	public boolean isPersonal() {
		return isPersonal;
	}

	public void setPersonal(boolean personal) {
		isPersonal = personal;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	
	public Resource getAuthor() {
		return author;
	}

	public void setAuthor(Resource author) {
		this.author = author;
	}

	public void setAuthorId(Long authorId) {
		Resource author = new Resource();
		author.setId(authorId);
		this.author = author;
	}

	public Entry getParent() {
		return parent;
	}

	public void setParent(Entry parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Entry: [Id=" + getId() + ", name=" + name + ", parent=" + parent + ", referenceNumber=" + referenceNumber + ", notes="
				+ notes + ", isGroup=" + isGroup + ", isPersonal=" + isPersonal + ", alert=" + alert + ", createdOn="
				+ createdOn + "]";
	}

}
