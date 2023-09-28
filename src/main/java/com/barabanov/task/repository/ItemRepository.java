package com.barabanov.task.repository;

import com.barabanov.task.entity.Item;
import jakarta.persistence.EntityManager;


public class ItemRepository extends PlayersThingRepository<Long, Item>
{
    public ItemRepository(EntityManager entityManager)
    {
        super(entityManager, Item.class);
    }
}
