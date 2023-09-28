package com.barabanov.task.repository;

import com.barabanov.task.entity.Progress;
import jakarta.persistence.EntityManager;

public class ProgressRepository extends PlayersThingRepository<Long, Progress>
{
    public ProgressRepository(EntityManager entityManager)
    {
        super(entityManager, Progress.class);
    }

}
