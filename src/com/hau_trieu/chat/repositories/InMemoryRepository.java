package com.hau_trieu.chat.repositories;

import java.util.function.Predicate;

import com.hautrieu.chat.domains.BaseEntity;

public class InMemoryRepository<T extends BaseEntity> implements Repository<T> {

	@Override
	public T getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T addingEntity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getFirst(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

}
