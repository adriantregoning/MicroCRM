package com.at.entity;
import java.math.BigDecimal;
import java.util.Calendar;

public class Activity extends AbstractEntity implements ListItem {
	
	public static String SQL_TABLE = "activity";
	
	private ActivityType activityType; 
	private Resource resource;
	private Task task; 
	private Entry entry; 
	private String title;
	private String description;
	private Calendar startedOn;
	private Calendar completedOn; 
	private boolean billable; 
	private BigDecimal timeTaken; 
	private boolean closed;
	
	public Activity() {
		super();
	}
	
	public ActivityType getActivityType() {
		return activityType;
	}
	
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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

	public boolean getBillable() {
		return billable;
	}

	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public Calendar getStartedOn() {
		return startedOn;
	}

	public void setStartedOn(Calendar startedOn) {
		this.startedOn = startedOn;
	}

	public Calendar getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(Calendar completedOn) {
		this.completedOn = completedOn;
	}

	public BigDecimal getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(BigDecimal timeTaken) {
		this.timeTaken = timeTaken;
	}

	@Override
	public String toString() {
		return "Activity: [id=" + getId() + ",\n " + activityType + ",\n resource=" + resource + ", task=" + task + ",\n entry="
				+ entry + ", title=" + title + ", description=" + description + ", startedOn=" + startedOn
				+ ",\n completedOn=" + completedOn + ",\n billable=" + billable + ",\n timeTaken=" + timeTaken + ",\n closed="
				+ closed + "]";
	}

	@Override
	public String getName() {
		return null;
	}
}
