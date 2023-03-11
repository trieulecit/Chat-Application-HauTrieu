package com.hautrieu.chat.domains;

import java.util.ArrayList;
import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.data.InMemoryDataStorage;

public class Group extends BaseEntity implements MessageReceivable  {
	
	private User admin;
	private String name;
	
	private List<User> members;
	private List<Message> messages;

	private boolean isPrivate;

	public Group(String name, boolean isPrivate) {
		
		members = new ArrayList<>();
		messages = new ArrayList<>();

		this.name = name;
		this.isPrivate = isPrivate;
	}

	public User getAdmin() {
		return admin;
	}

	public boolean checkAdmin(User user) {
		if (user.getId() == this.admin.getId()) {
			return true;
		} else {
			return false;
		}
	}

	public void setAdmin(User admin) {
		if (!isPrivate) {
			return;
		} else {
			this.admin = admin;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
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
				members.remove(i);
			}
		}
	}

	public void userLeave(User user) {
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getId() == user.getId()) {
				members.remove(i);
			}
		}
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void sendMessages(Message userMessage) {
		this.messages.add(userMessage);
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate, User creator) {
		
		if (isPrivate == false) {
			this.admin = null;
		} else {
			this.admin = creator;
			this.isPrivate = isPrivate;
		}		
	}

	@Override
	public long getReceiverId() {
		return getId();
	}
}