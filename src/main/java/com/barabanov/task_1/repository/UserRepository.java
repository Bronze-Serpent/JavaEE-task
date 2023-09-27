package com.barabanov.task_1.repository;

import com.barabanov.task_1.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;


public class UserRepository extends BaseRepository<Long, Player>
{
    public UserRepository(EntityManager entityManager)
    {
        super(entityManager, Player.class);
    }

    public Optional<Player> findByName(String nickname)
    {
        // TODO: 27.09.2023 это бы заменить,
        //  ведь использование Exception для управления потоком выполнения программы - плохая практика, но как
        try
        {
            return Optional.of(getEntityManager().createQuery("select p from Player p where p.nickname = :nick", Player.class)
                    .setParameter("nick", nickname).getSingleResult());
        }
        catch (NoResultException e)
        {
            return Optional.empty();
        }
    }
}
