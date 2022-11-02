package barabanov.service;

import barabanov.ORM.*;
import barabanov.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DBService
{

    private final DAOPlayer DAO_PLAYER;
    private final DAOCurrency DAO_CURRENCY;
    private final DAOItem DAO_ITEM;
    private final DAOProgress DAO_PROGRESS;


    public DBService(Connection dbConnection)
    {
        DAO_PLAYER = new DAOPlayerJDBC(dbConnection);
        DAO_CURRENCY = new DAOCurrencyJDBC(dbConnection);
        DAO_ITEM = new DAOItemJDBC(dbConnection);
        DAO_PROGRESS = new DAOProgressJDBC(dbConnection);
    }


    public List<Player> readPlayersWithAttributes() throws SQLException
    {

        List<Player> players = DAO_PLAYER.readAll();

        for (Player player : players)
        {
            player.addCurrency(DAO_CURRENCY.readAllWithPlId(player.getPlayerId()));
            player.addItem(DAO_ITEM.readAllWithPlId(player.getPlayerId()));
            player.addProgress(DAO_PROGRESS.readAllWithPlId(player.getPlayerId()));
        }

        return players;
    }


    public void writePlayersWithAttributes(List<Player> players) throws SQLException
    {
        for (Player player : players)
        {
            DAO_PLAYER.create(player);

            // записываем все сurrency игрока
            for (Currency currency : player.getCurrencies())
                DAO_CURRENCY.create(currency);

            // записываем все item игрока
            for (Item item : player.getItems())
                DAO_ITEM.create(item);

            // записываем все progress игрока
            for(Progress progress : player.getProgresses())
                DAO_PROGRESS.create(progress);
        }
    }
}
