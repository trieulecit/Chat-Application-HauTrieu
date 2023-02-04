package hau_trieu.chat;

import java.util.ArrayList;
import java.util.List;

public class PublicGroup implements Group{
	
	private List<User> members = new ArrayList<>();
	private List<Message> messages= new ArrayList<>();
	
	
	public PublicGroup() {
		
	}
	


	public void setMembers(List<User> members) {
		this.members = members;
	}



	@Override
	public void createGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMembers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMembers() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeMembers() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeGroups() {
		// TODO Auto-generated method stub
		
	}

}
