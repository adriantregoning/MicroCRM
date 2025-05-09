package com.at.entity;

import java.util.Calendar;

public class Task extends AbstractEntity implements ListItem {

    private Entry entry;
    private Resource owner;
    private Resource assignedTo;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private Calendar createdOn;
    private Calendar nextAction;
    private Calendar dueOn;
    private Calendar completedOn;

    public Task() {
        super();
    }
    
    public Task(Long id) {
        setId(id);
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Resource getOwner() {
        return owner;
    }

    public void setOwner(Resource owner) {
        this.owner = owner;
    }

    public Resource getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Resource assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
    
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public Calendar getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Calendar createdOn) {
        this.createdOn = createdOn;
    }

    public Calendar getNextAction() {
        return nextAction;
    }

    public void setNextAction(Calendar nextAction) {
        this.nextAction = nextAction;
    }

    public Calendar getDueOn() {
        return dueOn;
    }

    public void setDueOn(Calendar dueOn) {
        this.dueOn = dueOn;
    }

    public Calendar getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Calendar completedOn) {
        this.completedOn = completedOn;
    }
    
    @Override
    public String toString() {
        return "Task: [Id=" + getId() + ", entry=" + entry + ", owner=" + owner + ", assignedTo=" + assignedTo + 
               ", title=" + title + ", description=" + description + ", taskStatus=" + taskStatus + 
               ", createdOn=" + createdOn + ", nextAction=" + nextAction + ", dueOn=" + dueOn + 
               ", completedOn=" + completedOn + "]";
    }

    @Override
    public String getName() {
        return getTitle();
    }
}