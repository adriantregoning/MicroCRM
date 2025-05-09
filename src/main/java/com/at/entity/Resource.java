package com.at.entity;

import java.util.Calendar;

public class Resource extends AbstractEntity implements ListItem {
	
	public static String SQL_TABLE = "resource";

	private ResourceType resourceType;			
	private String firstName;
	private String surname;
	private String displayName;
	private String username;
	private String password;
	private boolean passwordChangeForce;
	private boolean roleSystemAdmin;
	private boolean roleHr;
	private boolean active;
	private boolean internal;
	private Calendar lastLogon;

	public Resource() {
		super();
	}
	
	public Resource(Long id) {
		setId(id);
	}
	// for the HashMap in ActivityController, getActivityByIdTwoTest() - which I must rename
	public Resource(Long id, String name, String surname) {	
		setId(id);
		setFirstName(name);
		setSurname(surname);
	}

	public String getFirstName() {
		return firstName;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPasswordChangeForce() {
		return passwordChangeForce;
	}

	public void setPasswordChangeForce(boolean passwordChangeForce) {
		this.passwordChangeForce = passwordChangeForce;
	}

	public boolean isRoleSystemAdmin() {
		return roleSystemAdmin;
	}

	public void setRoleSystemAdmin(boolean roleSystemAdmin) {
		this.roleSystemAdmin = roleSystemAdmin;
	}

	public boolean isRoleHr() {
		return roleHr;
	}

	public void setRoleHr(boolean roleHr) {
		this.roleHr = roleHr;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public Calendar getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(Calendar lastLogon) {
		this.lastLogon = lastLogon;
	}
	
	public String getFullName() {
		return firstName + " " + surname;
	}

	
	@Override
	public String toString() {
		return "Resource: [Id=" + getId() + ",\n resourceType= " + resourceType + ",\n firstName=" + firstName + ",\n surname=" + surname
				+ ",\n displayName=" + displayName + ",\n username=" + username + ",\n password=" + password
				+ ",\n passwordChangeForce=" + passwordChangeForce + ",\n roleSystemAdmin=" + roleSystemAdmin + ",\n roleHr="
				+ roleHr + ",\n active=" + active + ",\n internal=" + internal + ",\n lastLogon=" + lastLogon + "]";
	}

	@Override
	public String getName() {
		// Return the full name if both first name and surname are available
		if (firstName != null && surname != null) {
			return firstName + " " + surname;
		} 
		// If display name is set, use that
		else if (displayName != null) {
			return displayName;
		}
		// Fall back to username if available
		else if (username != null) {
			return username;
		}
		// If nothing else is available, use the ID
		else {
			return "Resource " + getId();
		}
	}
	
	
	
}
