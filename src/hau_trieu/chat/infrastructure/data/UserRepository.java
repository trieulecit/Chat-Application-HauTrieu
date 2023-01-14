package hau_trieu.chat.infrastructure.data;

import hau_trieu.chat.User;

public interface UserRepository {
	User get(Long id);
	void add(User user);
	void update(User user);
	void remove(User user);
}
