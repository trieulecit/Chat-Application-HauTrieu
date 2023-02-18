package com.hautrieu.chat.repositories;

import java.util.function.Predicate;

import com.hautrieu.chat.domains.BaseEntity;

public interface Repository<T extends BaseEntity> {
	T getById(int id);
    boolean add(T addingEntity);
    void deleteAll();

    T getFirst(Predicate<T> predicate);
}
