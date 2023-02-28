package com.hautrieu.chat.domains;

public class BaseEntity {
	
	private long id;
	
	public BaseEntity(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setIdIfNotExist(long id) {
        if (id != 0) {
            setId(id);
        }
    }
	
}
