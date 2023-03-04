package com.hautrieu.chat.services;

import java.util.HashMap;
import java.util.List;

import com.hautrieu.chat.data.DataStorage;
import com.hautrieu.chat.domains.Group;
import com.hautrieu.chat.domains.User;
import com.hautrieu.chat.repositories.Repository;

public class GroupService {

	private final DataStorage storage;

	public GroupService(DataStorage storage) {
		this.storage = storage;
	}

	public boolean createGroup(String name, boolean isPrivate) {
		Group newGroup = new Group(name, isPrivate);
		Group existing = storage.getGroups().getFirst(group -> group.getName().equals(name));

		if (existing == null) {
			storage.getGroups().add(newGroup);
		} else 
		{
			return false;
		}
		return true;
	}

	public Group getGroup(String groupName) {
		Group existing = storage.getGroups().getFirst(group -> group.getName().equals(groupName));

		return existing;
	}

	public void moveToGroup(long id) {
//		this.currentGroup = storage.getGroups().i;
	}

	public boolean kickMember(User admin, User user, Group group) {

		boolean kicked = false;

		if (group.checkAdmin(admin) && !group.checkAdmin(user)) {

			group.kickMember(user);

			kicked = true;
		}

		return kicked;
	}

	public boolean addMember(User user, Group group) {
		return group.addMember(user);
	}

	public boolean joinGroup(String groupName, User user) {
		Group existingGroup = storage.getGroups().getFirst(group -> group.getName().equalsIgnoreCase(groupName));
		if (existingGroup != null) {
			return existingGroup.addMember(user);
		} else {
			return false;
		}
	}

	public void setNewAdmin(User admin, User promotedUser, Group group) {

		if (group.getAdmin().getId() == admin.getId()) {
			group.setAdmin(promotedUser);
		}

	}
	public void privateGroupAdminPromote(User creator,Group group) {
		group.setAdmin(creator);
	}
}
