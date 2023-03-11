package com.hautrieu.chat.repositories;

import java.util.List;
import java.util.function.Predicate;

import com.hautrieu.chat.domains.BaseEntity;

public interface Repository<T extends BaseEntity> {
	List<T> getAllMatching(Predicate<T> predicate);
	
	T getById(long id);
	T getFirst(Predicate<T> predicate);
    T removeFirst(Predicate<T> predicate);
    
    void add(T addingEntity);
    void deleteAll();
    
    long getNextId();    
    long getSize();
    
}
