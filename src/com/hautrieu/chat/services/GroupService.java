package com.hautrieu.chat.services;

import java.util.HashMap;
import java.util.List;

import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.User;

public class GroupService {

	private Group currentGroup = null;

	private HashMap<String, Group> groups = new HashMap<>();

	public void moveToGroup(String groupName) {
		this.currentGroup = groups.get(groupName);
	}

	public boolean kickMember(User admin, User user) {

		boolean kicked = false;

		if (currentGroup.checkAdmin(admin) && !currentGroup.checkAdmin(user)) {
			
			currentGroup.kickMember(user);
			
			kicked = true;
		}
		
		return kicked;
	}

	public void addMember(User user) {
		currentGroup.addMember(user);
	}

	public boolean joinGroup(String groupName, User user) {
		boolean joined = groups.get(groupName).addMember(user);
		return joined;
	}

	public void setAdmin(User admin, User promotedUser, Group group) {

		if (currentGroup.getAdmin().getId() == admin.getId()) {
			currentGroup.setAdmin(promotedUser);
		}

	}
}
