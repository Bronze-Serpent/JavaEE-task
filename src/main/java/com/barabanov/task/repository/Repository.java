package com.barabanov.task.repository;

import com.barabanov.task.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


public interface Repository<T extends Serializable, E extends BaseEntity<T>>
{
    E save(E entity);

    default Optional<E> findById(T id)
    {
        return findById(id, Collections.emptyMap());
    }

    Optional<E> findById(T id, Map<String, Object> properties);

    void update(E entity);

    void delete(E entity);
}
