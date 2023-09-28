package com.barabanov.task.repository;

import com.barabanov.task.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class BaseRepository<T extends Serializable, E extends BaseEntity<T>> implements Repository<T, E>
{
    @Getter
    private final EntityManager entityManager;

    @Getter
    private final Class<E> clazz;


    public E save(E entity)
    {
       entityManager.persist(entity);
       return entity;
    }

    public Optional<E> findById(T id, Map<String, Object> properties)
    {
        return Optional.ofNullable(entityManager.find(clazz, id, properties));
    }

    public void update(E entity)
    {
        entityManager.merge(entity);
    }

    public void delete(E entity)
    {
        entityManager.remove(entity);
        entityManager.flush();
    }
}
