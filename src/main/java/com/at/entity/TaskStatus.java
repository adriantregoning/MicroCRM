package com.at.entity;

public enum TaskStatus {
	 
	ASSIGNED("assigned", 1),
	RECEIVEDANDINPROGRESS("receivedandinprogress", 2),
	COMPLETEDBYRESOURCE("completedbyresource", 3),
	SIGNEDOFF("signedoff", 4),
	INTRANSFER("intransfer", 5),
	DECLINED("declined", 6),
	NEEDSDECISION("needsdecision", 7),
	SITUATIONDEMANDSYOURATTENTION("situationdemandsyourattention", 8),
	PROBLEMMAYOCCUR("problemmayoccur", 9),
	ACKNOWLEDGED("acknowledged", 10),
	DELAYED("delayed",11);
	
	private final String name;
	private final int value;
	
	private TaskStatus(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
	
	public static TaskStatus fromInt(int value) {
		for(TaskStatus taskStatus : getTaskStatuses()) {
			if(taskStatus.getValue() == value) {
				return taskStatus;
			}
		}
		return null;
	}
	
	public static TaskStatus[] getTaskStatuses() {
		return TaskStatus.values();
	}
	
	 public static TaskStatus mapIntToTaskStatus(int value) {
			switch (value) {
			case 1:
				return TaskStatus.ASSIGNED;
			case 2:
				return TaskStatus.RECEIVEDANDINPROGRESS;
			case 3:
				return TaskStatus.COMPLETEDBYRESOURCE;
			case 4:
				return TaskStatus.SIGNEDOFF;
			case 5:
				return TaskStatus.INTRANSFER;
			case 6:
				return TaskStatus.DECLINED;
			case 7:
				return TaskStatus.NEEDSDECISION;
			case 8:
				return TaskStatus.SITUATIONDEMANDSYOURATTENTION;
			case 9:
				return TaskStatus.PROBLEMMAYOCCUR;
			case 10:
				return TaskStatus.ACKNOWLEDGED;
			case 11:
				return TaskStatus.DELAYED;
	
			default:
				throw new IllegalArgumentException("Unknown TaskStatus value: " + value);
			}
	 }
	
}
