package com.hau_trieu.chat.domains;

import java.util.ArrayList;
import java.util.List;

public class Group extends BaseEntity {
	private User admin;
	private List<User> members = new ArrayList<>();
	private List<Message> messages = new ArrayList<>();
	private boolean isPrivate;
	
	public Group(int id) {
		super(id);
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		if (!isPrivate) {
			return;
		}
		for (User member : members) {
			if (member.getId() == admin.getId()) {
				this.admin = member;
			}
		}
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
		}
		
		return userIsInGroup;

	}

	public void kickMember(User user) {
		if (!isPrivate) {
			return;
		}
		for (User member : members) {
			if (member.getId() == user.getId()) {
				members.remove(member);
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
}
