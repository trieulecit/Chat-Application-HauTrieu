package hau_trieu.chat.infrastructure.data;

import hau_trieu.chat.Group;

public interface GroupRespository {
	Group get(Group groupId);
	void create();
	void update();
	void remove();
	
}
