package com.hautrieu.chat.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.hautrieu.chat.domains.BaseEntity;

public class InMemoryRepository<T extends BaseEntity> implements Repository<T> {
	
	private Map<Integer, T> dictionary;
	
	public InMemoryRepository() {
		dictionary = new HashMap<>();
	}
	
	@Override
	public T getById(int id) {		
		return dictionary.get(id);
	}

	@Override
	public boolean add(T addingEntity) {
		dictionary.put(addingEntity.getId(), addingEntity);
		return true;
	}

	@Override
	public void deleteAll() {
		dictionary.clear();		
	}

	@Override
	public T getFirst(Predicate<T> predicate) {
		for(T value: dictionary.values()) {
			if(predicate.test(value)) {
				return value;
			}
		}
		return null;
	}

}
