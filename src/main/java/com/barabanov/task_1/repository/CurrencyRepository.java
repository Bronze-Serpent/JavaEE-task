package com.barabanov.task_1.repository;

import com.barabanov.task_1.entity.Currency;
import jakarta.persistence.EntityManager;


public class CurrencyRepository extends PlayersThingRepository<Long, Currency>
{
    public CurrencyRepository(EntityManager entityManager)
    {
        super(entityManager, Currency.class);
    }
}
