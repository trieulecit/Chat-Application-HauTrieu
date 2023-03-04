package com.hautrieu.chat.domains;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Group extends BaseEntity implements MessageReceivable {
	
	private User admin;
	private String name;
	
	private List<User> members = new ArrayList<>();
	private List<Message> messages = new ArrayList<>();
	
	private boolean isPrivate;
	
	public Group(String name, boolean isPrivate) {
		super(generateIdByTime());
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
		}
		for (User member : members) {
			if (member.getId() == admin.getId()) {
				this.admin = member;
			}
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
			return true;
		}
		
		return false;

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
	public static long generateIdByTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyyddHHmmss");
		String formattedDate = now.format(formatter);
		return Long.parseLong(formattedDate);
	}

}
