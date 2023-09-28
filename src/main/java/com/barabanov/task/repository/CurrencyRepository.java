package com.barabanov.task.repository;

import com.barabanov.task.entity.Currency;
import jakarta.persistence.EntityManager;


public class CurrencyRepository extends PlayersThingRepository<Long, Currency>
{
    public CurrencyRepository(EntityManager entityManager)
    {
        super(entityManager, Currency.class);
    }

}
