package barabanov.service;

import barabanov.ORM.*;
import barabanov.entity.*;

import java.sql.SQLException;
import java.util.List;


public class DBService
{

    private final static DAOPlayer DAO_PLAYER = new DAOPlayerJDBC();
    private final static DAOCurrency DAO_CURRENCY = new DAOCurrencyJDBC();
    private final static DAOItem DAO_ITEM = new DAOItemJDBC();
    private final static DAOProgress DAO_PROGRESS = new DAOProgressJDBC();


    public static List<Player> readPlayersWithAttributes() throws SQLException
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


    public static void writePlayersWithAttributes(List<Player> players) throws SQLException
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
