package com.at.entity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class AbstractEntity {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSqlValue(String str) {
		if (str == null || str.isEmpty()) {
			return "NULL";
		} else {
			return "\"" + str + "\"";
		}
	}

	public String getSqlValue(Calendar date) {
		if (date == null) {
			return "NULL";
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateStr = dateFormat.format(date.getTime());
			return "'" + dateStr + "'";
		}
	}

	public String getSqlValue(BigDecimal bigD) {
		if (bigD == null || bigD.toString().isEmpty()) {
			return "NULL";
		} else {
			return "\"" + bigD + "\"";
		}
	}
	
	public int getSqlValue(boolean bool) {	
		return bool ? 1: 0;	
	}
	
	public String getSqlValue(Entry entry) {
		if (entry == null) {
			return null; 
		} else {
			return "\"" + entry.getId() + "\"";
		}
	}
	
	public String getSqlValue(Resource resource) {
		if (resource == null) {
			return null; 
		} else {
			return "\"" + resource.getId() + "\"";
		}
	}
	
	public String getSqlValue(TaskStatus taskStatus) {
		if (taskStatus == null) {
			return "\"" + 1 + "\""; 
		} else {
			return "\"" + taskStatus.getValue() + "\"";
		}
	}
}
