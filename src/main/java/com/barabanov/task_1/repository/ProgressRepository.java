package com.barabanov.task_1.repository;

import com.barabanov.task_1.entity.Progress;
import jakarta.persistence.EntityManager;

public class ProgressRepository extends PlayersThingRepository<Long, Progress>
{
    public ProgressRepository(EntityManager entityManager)
    {
        super(entityManager, Progress.class);
    }
}
