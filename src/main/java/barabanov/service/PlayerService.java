package barabanov.service;

import barabanov.ORM.DAOPlayer;
import barabanov.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static barabanov.service.CurrencyService.currencyToJson;
import static barabanov.service.CurrencyService.jsonToCurrencies;
import static barabanov.service.ItemService.itemToJson;
import static barabanov.service.ItemService.jsonToItems;
import static barabanov.service.ProgressService.jsonToProgresses;
import static barabanov.service.ProgressService.progressesToJson;


public class PlayerService
{

    private final DAOPlayer daoPlayer;
    private final CurrencyService currencyS;
    private final ItemService itemS;
    private final ProgressService progressS;


    public PlayerService(DAOPlayer daoPlayer, CurrencyService currencyS, ItemService itemS, ProgressService progressS)
    {
        this.daoPlayer = daoPlayer;
        this.currencyS = currencyS;
        this.itemS = itemS;
        this.progressS = progressS;
    }


    public Player readFromDB(long id) throws SQLException {

        Player pl = daoPlayer.readById(id);

        pl.addCurrency(currencyS.readAllWithPlId(pl.getPlayerId()));
        pl.addItem(itemS.readAllWithPlId(pl.getPlayerId()));
        pl.addProgress(progressS.readAllWithPlId(pl.getPlayerId()));

        return pl;
    }


    public void writeToDB(Player pl) throws SQLException {
        daoPlayer.create(pl);

        currencyS.writeTODB(pl.getCurrencies());
        itemS.writeToDB(pl.getItems());
        progressS.writeToDB(pl.getProgresses());
    }


    public void updateDB(Player pl) throws SQLException { daoPlayer.update(pl);}

    public void deleteFromDB(long id) throws SQLException { daoPlayer.delete(id); }


    public void writeToDB(List<Player> players) throws SQLException
    {
        for (Player player : players)
            writeToDB(player);
    }


    public List<Player> readAllPlayers() throws SQLException
    {
        List<Player> players = daoPlayer.readAll();

        for (Player player : players)
        {
            player.addCurrency(currencyS.readAllWithPlId(player.getPlayerId()));
            player.addItem(itemS.readAllWithPlId(player.getPlayerId()));
            player.addProgress(progressS.readAllWithPlId(player.getPlayerId()));
        }

        return players;
    }


    // метод реализован через примитивную json-simple. Существуют гораздо менее многословные библиотеки
    // для парсинга json в Java. Однако json-simple использовалась для простоты восприятия т.к. это первая работа с json
    public static List<Player> readFromJson(String fileName) throws IOException, ParseException
    {
        // считываем json файл
        JSONArray jsonArr = (JSONArray) new JSONParser().parse(new FileReader(fileName));

        List<Player> players = new LinkedList<>();

        // идём по всем игрокам
        for (Object obj : jsonArr)
        {
            JSONObject playerJson = (JSONObject) obj;
            Player readPlayer = new Player((long) playerJson.get("playerId"), (String) playerJson.get("nickname"));

            // записываем currencies для игрока
            readPlayer.addCurrency(jsonToCurrencies((JSONArray) playerJson.get("currencies")));

            // записываем items для игрока
            readPlayer.addItem(jsonToItems((JSONArray) playerJson.get("items")));

            // записываем progresses для игрока
            readPlayer.addProgress(jsonToProgresses((JSONArray) playerJson.get("progresses")));

            players.add(readPlayer);
        }

        return players;
    }


    public static JSONObject playerToJson(Player player)
    {
        JSONObject jsonPlayer = new JSONObject();

        jsonPlayer.put("playerId", player.getPlayerId());
        jsonPlayer.put("nickname", player.getNickname());
        jsonPlayer.put("progresses", progressesToJson(player.getProgresses()));
        jsonPlayer.put("currencies", currencyToJson(player.getCurrencies()));
        jsonPlayer.put("items", itemToJson(player.getItems()));

        return jsonPlayer;
    }


    private static JSONArray playerToJson(List<Player> players)
    {
        JSONArray playersJsonView = new JSONArray();

        for (Player player : players)
            playersJsonView.add(playerToJson(player));

        return playersJsonView;
    }
}
