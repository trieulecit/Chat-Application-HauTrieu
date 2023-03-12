package com.hautrieu.chat.domains;

import java.util.ArrayList;
import java.util.List;

public class Group extends BaseEntity implements MessageReceivable  {
	
	private User admin;
	private String name;
	
	private List<User> members;
	private List<Message> messages;

	private boolean isPrivate;

	public Group(String nameAsInput, boolean isPrivateAsInput) {
		
		members = new ArrayList<>();
		messages = new ArrayList<>();

		name = nameAsInput;
		isPrivate = isPrivateAsInput;
	}

	public User getAdmin() {
		return admin;
	}

	public boolean checkAdmin(User user) {		
		return admin.getId() == user.getId();
	}

	public void setAdmin(User adminAsInput) {
		
		if (!isPrivate) {
			return;
		}
		admin = adminAsInput;		
	}

	public boolean addMember(User user) {

		boolean userIsInGroup = false;

		for (User member : members) {			
			if (member.getId() == user.getId()) {				
				userIsInGroup = true;				
				break;
			}
		}
		
		if (!userIsInGroup) {
			members.add(user);
			user.addGroup(this);			
			return true;
		}
		return false;
	}

	public void kickMember(User user) {
		if (!isPrivate) {
			return;
		}
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getId() == user.getId()) {
				members.get(i).leaveGroup(this);
				members.remove(i);
			}
		}
	}

	public boolean userLeave(User user) {
		
		for (int index = 0; index < members.size(); index++) {			
			User member = members.get(index);
			
			if (member.getId() == user.getId()) {
				
				member.leaveGroup(this);
				members.remove(index);
				
				return true;
			}
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String nameAsInput) {
		name = nameAsInput;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> membersAsInput) {
		members = membersAsInput;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messagesAsInput) {
		messages = messagesAsInput;
	}

	public void sendMessages(Message userMessage) {
		messages.add(userMessage);
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivateAsInput, User creator) {
		
		if (isPrivate == false) {
			admin = null;
		} else {
			admin = creator;
			isPrivate = isPrivateAsInput;
		}		
	}

	@Override
	public long getReceiverId() {
		return getId();
	}
}