package com.hautrieu.chat.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.hautrieu.chat.domains.BaseEntity;

public class InMemoryRepository<T extends BaseEntity> implements Repository<T> {
	
	private Map<Long, T> dictionary;
	
	public InMemoryRepository() {
		dictionary = new HashMap<>();
	}
	
	@Override
	public T getById(long id) {		
		return dictionary.get(id);
	}

	@Override
	public void add(T addingEntity) {
		long id = dictionary.size() == 0 ? 0 : getNextId();
		addingEntity.setId(id);
		dictionary.put(id, addingEntity);
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

	@Override
	public List<T> getAllMatching(Predicate<T> predicate) {
		List<T> list = new ArrayList<>();	
		
		for(T value: dictionary.values()) {
			if(predicate.test(value)) {
				list.add(value);
			}
		}
		
		return list;
	}

	@Override
	public long getNextId() {
		return dictionary.size() + 1;
	}

	@Override
	public T removeFirst(Predicate<T> predicate) {
		
		for(T value: dictionary.values()) {
			if(predicate.test(value)) {
				dictionary.remove(value.getId());
				return value;
			}
		}		
		return null;
	}

	@Override
	public long getSize() {
		return dictionary.size();
	}

	@Override
	public List<T> toList() {
		return new ArrayList<>(dictionary.values());
	}
	
	@Override
	public boolean compareById(T firstEntity, T secondEntity) {
		
		long firstId = firstEntity.getId();
		long secondId = secondEntity.getId();
		
		boolean condition = firstId == secondId;
		
		return condition;
	}
}
