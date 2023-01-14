package hau_trieu.chat;

import java.util.List;

public class Group {
	
	private List<User> members;
	
	public Group(){
		
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}
	
}
