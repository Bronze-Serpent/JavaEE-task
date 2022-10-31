package barabanov.service;

import barabanov.entity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class JsonService
{

    // метод реализован через примитивную json-simple. Существуют гораздо менее многословные библиотеки
    // для парсинга json в Java. Однако json-simple использовалась для простоты восприятия т.к. это первая работа с json
    public static List<Player> readPlayersWithAttributes(String fileName) throws IOException, ParseException
    {
        // считываем json файл
        JSONArray jsonArr = (JSONArray) new JSONParser().parse(new FileReader(fileName));

        List<Player> players = new LinkedList<>();

        // идём по всем игрокам
        for (Object obj : jsonArr)
        {
            JSONObject playerJson = (JSONObject) obj;
            Player readPlayer = jsonToPlayer(playerJson);

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


    public static void writePlayersWithAttributes(List<Player> players, String fileName)
    {
        try (FileWriter file = new FileWriter(fileName))
        {
            JSONArray jsonPlayers = playersToJsonView(players);

            for (Object objPlayer : jsonPlayers)
            {
                JSONObject jsonPlayer = (JSONObject) objPlayer;

                file.write(jsonPlayer.toJSONString());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    private static Player jsonToPlayer(JSONObject playerJson)
    {
        return new Player((long) playerJson.get("playerId"), (String) playerJson.get("nickname"));
    }


    private static List<Progress> jsonToProgresses(JSONArray jArrProgress)
    {
        List<Progress> progresses = new LinkedList<>();

        for (Object o : jArrProgress)
        {
            JSONObject jPrgs = (JSONObject) o;
            progresses.add(new Progress(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId")), (long) jPrgs.get("score"), (long) jPrgs.get("maxScore")));
        }

        return progresses;
    }


    private static List<Currency> jsonToCurrencies(JSONArray jArrCurrency)
    {
        List<Currency> currencies = new LinkedList<>();

        for (Object o : jArrCurrency)
        {
            JSONObject jPrgs = (JSONObject) o;
            currencies.add(new Currency(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId")), (String) jPrgs.get("name"), (long) jPrgs.get("count")));
        }

        return currencies;
    }


    private static List<Item> jsonToItems(JSONArray jArrItem)
    {
        List<Item> items = new LinkedList<>();

        for (Object o : jArrItem)
        {
            JSONObject jPrgs = (JSONObject) o;
            items.add(new Item(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId")), (long) jPrgs.get("count"), (long) jPrgs.get("level")));
        }

        return items;
    }


    private static JSONArray playersToJsonView(List<Player> players)
    {
        JSONArray playersJsonView = new JSONArray();

        for (Player player : players)
        {
            JSONObject jsonPlayer = playerToJson(player);
            jsonPlayer.put("progresses", progressesToJson(player.getProgresses()));
            jsonPlayer.put("currencies", currenciesToJson(player.getCurrencies()));
            jsonPlayer.put("items", itemsToJson(player.getItems()));

            playersJsonView.add(jsonPlayer);
        }

        return playersJsonView;
    }


    private static JSONObject playerToJson(Player player)
    {
        JSONObject jsonPlayer = new JSONObject();

        jsonPlayer.put("playerId", player.getPlayerId());
        jsonPlayer.put("nickname", player.getNickname());

        return jsonPlayer;
    }


    private static JSONArray progressesToJson(List<Progress> progresses)
    {
        JSONArray jsonProgressArr = new JSONArray();
        for (Progress progress : progresses)
        {
            JSONObject jsonProgress = new JSONObject();

            jsonProgress.put("id", progress.getToken().getId());
            jsonProgress.put("playerId", progress.getToken().getPlayerId());
            jsonProgress.put("resourceId", progress.getToken().getResourceId());
            jsonProgress.put("score", progress.getScore());
            jsonProgress.put("maxScore", progress.getMaxScore());

            jsonProgressArr.add(jsonProgress);
        }

        return jsonProgressArr;
    }


    private static JSONArray currenciesToJson(List<Currency> currencies)
    {
        JSONArray jsonCurrenciesArr = new JSONArray();
        for (Currency currency : currencies)
        {
            JSONObject jsonCurrency = new JSONObject();

            jsonCurrency.put("id", currency.getToken().getId());
            jsonCurrency.put("playerId", currency.getToken().getPlayerId());
            jsonCurrency.put("resourceId", currency.getToken().getResourceId());
            jsonCurrency.put("name", currency.getName());
            jsonCurrency.put("count", currency.getCount());

            jsonCurrenciesArr.add(jsonCurrency);
        }

        return jsonCurrenciesArr;
    }


    private static JSONArray itemsToJson(List<Item> items)
    {
        JSONArray jsonItemsArr = new JSONArray();
        for (Item item : items)
        {
            JSONObject jsonItem = new JSONObject();

            jsonItem.put("id", item.getToken().getId());
            jsonItem.put("playerId", item.getToken().getPlayerId());
            jsonItem.put("resourceId", item.getToken().getResourceId());
            jsonItem.put("count", item.getCount());
            jsonItem.put("level", item.getLevel());

            jsonItemsArr.add(jsonItem);
        }

        return jsonItemsArr;
    }
}
