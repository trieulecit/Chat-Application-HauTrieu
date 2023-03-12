package com.hautrieu.chat.services;

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
		Group existing = getGroupByName(name);

		if (existing == null) {
			storage.getGroups().add(newGroup);
		} else {
			return false;
		}
		return true;
	}

	public Group getGroupByName(String groupName) {
		Repository<Group> groups = storage.getGroups();
		Group existing = groups.getFirst(group -> group.getName().equalsIgnoreCase(groupName));

		return existing;
	}

	public boolean kickMember(User admin, User user, Group group) {

		boolean kicked = false;
		
		if (!group.isPrivate()) {
			return kicked;
		}
		if (group.checkAdmin(admin)) {

			group.kickMember(user);

			kicked = true;
		}

		return kicked;
	}

	public boolean addMember(User user, Group group) {
		return group.addMember(user);
	}

	public boolean joinGroup(String groupName, User user) {
		
		boolean joined = false;
		Group existingGroup = getGroupByName(groupName);
		
		if (existingGroup != null && !existingGroup.isPrivate()) {
			user.addGroup(existingGroup);
			joined = existingGroup.addMember(user);
		}
		return joined;

	}

	public void promoteAdmin(User admin, User promotedUser, Group group) {

		if (group.getAdmin().getId() == admin.getId()) {
			group.setAdmin(promotedUser);
		}

	}

	public void setCreatorAsAdminInPrivateGroup(User creator, Group group) {
		if (group.isPrivate()) {
			group.setAdmin(creator);
		}
	}

	public boolean leaveGroup(User user, Group group) {
		
		if(!group.userLeave(user)) {
			return false;
		}
		
		return user.leaveGroup(group);
	}

	public List<Group> GetGroupsOfUser(User user) {
		return user.getGroups();
	}
	
	public void setAliasForUser(User assignor, User assignee, String codename) {
		assignee.addAlias(assignor.getUserName(), codename);
	}
	
	public boolean compareCorrectGroupName(Group group, String comparator) {
		
		String name = group.getName();
		boolean condition = name.equals(comparator);
		
		return condition;
	}
}