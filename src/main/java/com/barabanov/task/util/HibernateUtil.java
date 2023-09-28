package com.barabanov.task.util;

import com.barabanov.task.entity.Currency;
import com.barabanov.task.entity.Item;
import com.barabanov.task.entity.Player;
import com.barabanov.task.entity.Progress;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;


@UtilityClass
public class HibernateUtil
{

    public static SessionFactory buildSessionFactory()
    {
        var configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }


    public static Configuration buildConfiguration() {

        var configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        configuration.addAnnotatedClass(Currency.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Progress.class);
        configuration.addAnnotatedClass(Player.class);

        return configuration;
    }
}
