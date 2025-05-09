package com.at.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourceType extends AbstractEntity implements ListItem {

	private String name;
	
	public ResourceType() {
		super();
	}
	
	public ResourceType(ResultSet rs, Connection conn) throws SQLException {
		super();
		
		setId(rs.getLong("id"));
		setName(rs.getString("name"));
	}
	
	public String toSqlAdd() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO resource_type VALUES (");
		sb.append(getId());
		sb.append(", ");
		sb.append(getSqlValue(getName()));
		sb.append(");");
		return sb.toString();
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
		return "ResourceType [id = " + getId() + ", name=" + name + "]";
	}
}

