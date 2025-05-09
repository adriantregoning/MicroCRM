package com.at.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityType extends AbstractEntity implements ListItem {

	private String name;

	public ActivityType() {
		super();
	}

	public ActivityType(ResultSet rs, Connection conn) throws SQLException {
		super();

		setId(rs.getLong("id"));
		setName(rs.getString("name"));
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ActivityType [id=" + getId() + ", name=" + name + "]";
	}
}
