package com.hautrieu.chat.domains;

public abstract class BaseEntity {
	
	private long id;
	
	public long getId() {
		return id;
	}
	
	public void setId(long idAsInput) {
		id = idAsInput;
	}
}
