package com.hautrieu.chat.repositories;

import java.util.List;
import java.util.function.Predicate;

import com.hautrieu.chat.domains.BaseEntity;

public interface Repository<T extends BaseEntity> {	
	T getById(long id);
    boolean add(T addingEntity);
    void deleteAll();
    long getSize();
    T getFirst(Predicate<T> predicate);
    List<T> getAllMatching(Predicate<T> predicate);
}
