package com.barabanov.task_1.repository;

import com.barabanov.task_1.entity.Item;
import jakarta.persistence.EntityManager;


public class ItemRepository extends PlayersThingRepository<Long, Item>
{
    public ItemRepository(EntityManager entityManager)
    {
        super(entityManager, Item.class);
    }
}
