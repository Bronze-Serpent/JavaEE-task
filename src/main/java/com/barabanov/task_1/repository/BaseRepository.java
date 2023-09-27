package com.barabanov.task_1.repository;

import com.barabanov.task_1.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class BaseRepository<T extends Serializable, E extends BaseEntity<T>>
{
    @Getter
    private final EntityManager entityManager;

    @Getter
    private final Class<E> clazz;


    public void create(E entity)
    {
        entityManager.persist(entity);
    }

    public Optional<E> findById(T id)
    {
        return Optional.ofNullable(entityManager.find(clazz, id));
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
