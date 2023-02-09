package com.hau_trieu.chat.infrastructure;

import com.hau_trieu.chat.domains.User;

public interface UserRepository {
	User get(Long id);
	
	void create(User user);
	
	void update(User user);
	
	void remove(User user);
}
