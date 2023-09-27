package com.barabanov.task_1.repository;

import com.barabanov.task_1.entity.BaseEntity;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;


public abstract class PlayersThingRepository <T extends Serializable, E extends BaseEntity<T>> extends BaseRepository<T, E>
{
    public PlayersThingRepository(EntityManager entityManager, Class<E> clazz)
    {
        super(entityManager, clazz);
    }


    public List<E> findAllWithPlayerId(Long id)
    {
        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(getClazz());

        var playersThing = criteria.from(getClazz());
        var player = playersThing.join("player");

        criteria.select(playersThing)
                .where(cb.equal(player.get("id"), id));

        return getEntityManager().createQuery(criteria)
                .getResultList();
    }
}
