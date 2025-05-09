package com.at.utils;

import com.at.entity.ListItem;

public class ValidationUtils {

	public static boolean isNullOrEmpty(Object o) {
		if (o == null) {
			return true;
		} else {
			if (o instanceof String) {
				return ((String)(o)).trim().isEmpty();
			} else if (o instanceof String[]) {
				return ((String[])(o))[0].trim().isEmpty();
			} else {
				return false;
			}
		}
	}
	
	public static boolean isIdentifiableValid(ListItem jId) {
		if (jId==null || isNullOrEmpty(jId.getId()) || !isNumeric(jId.getId())) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isNumeric(Object o) {
		if (o==null) {
			return false;
		} else {
			if (o instanceof String[]) {
				return isNumeric(((String[])(o))[0]);
			} else {
				return isNumeric(o.toString());				
			}
		}
	}
	
	public static boolean isSameString(String value1, String value2) {
		System.out.println("isSameString comparing: '" + value1 + "' vs '" + value2 + "'");
		
		if(value1 == null && value2 == null) {
			System.out.println("Both null, returning true");
			return true;
		}
		
		if((value1 == null && value2 != null) || (value1 != null && value2 == null)) {
			System.out.println("One null, one not null, returning false");
			return false;
		}
		
		boolean result = value1.equals(value2);
		System.out.println("String comparison result: " + result);
		return result;
	}
	
	
}