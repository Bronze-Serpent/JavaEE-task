package com.barabanov.task.repository;

import com.barabanov.task.entity.BaseEntity;
import com.barabanov.task.entity.Progress;
import jakarta.persistence.EntityGraph;
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

    public EntityGraph<Progress> getGraphWithPlayer()
    {
        EntityGraph<Progress> progressesWithPlayer = getEntityManager().createEntityGraph(Progress.class);
        progressesWithPlayer.addAttributeNodes("player");

        return progressesWithPlayer;
    }
}
