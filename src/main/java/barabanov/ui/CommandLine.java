package barabanov.ui;


import barabanov.ORM.*;
import barabanov.entity.*;
import barabanov.service.DBService;
import barabanov.service.JsonService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static barabanov.ui.Checker.*;


public class CommandLine
{

    private static final String url = "jdbc:postgresql://localhost:5432/java_task_1";
    private static final String user = "postgres";
    private static final String password = "87690";


    public static void main(String[] args) throws SQLException, IOException, ParseException
    {
        try (Connection dbConnection = DriverManager.getConnection(url, user, password))
        {
            DBService dbService = new DBService(dbConnection);
            JsonService jsonService = new JsonService();

            List<Player> players = jsonService.readPlayersWithAttributes("./players.json");

            //dbService.writePlayersWithAttributes(players);
            Map<Long, Player> idToPlayer = players.stream()
                    .collect(Collectors.toMap(Player::getPlayerId, p -> p));

            DAOPlayer playerDB = new DAOPlayerJDBC(dbConnection);
            DAOItem itemDB = new DAOItemJDBC(dbConnection);
            DAOCurrency currencyDB = new DAOCurrencyJDBC(dbConnection);
            DAOProgress progressDB = new DAOProgressJDBC(dbConnection);

            Scanner scanner = new Scanner(System.in);

            while (true)
            {
                System.out.print("Enter command: ");
                String[] cmdWords = scanner.nextLine().trim().split("\\s+");

                if (cmdWords.length == 1 && cmdWords[0].equals("out"))
                    break;

                String checkResult = checkCmdSyntax(cmdWords);

                if (checkResult.equals("checked"))
                    switch (cmdWords[1])
                    {
                        case "player" -> executeCmdForPlayer(idToPlayer, cmdWords, playerDB, scanner);

                        case "item" -> executeCmdForItem(idToPlayer, cmdWords, itemDB, scanner);

                        case "currency" -> executeCmdForCurrency(idToPlayer, cmdWords, currencyDB, scanner);

                        case "progress" -> executeCmdForProgress(idToPlayer, cmdWords, progressDB, scanner);
                    }
                else
                    System.out.println(checkResult);
            }

        }

    }


    private static void executeCmdForProgress(Map<Long, Player> idToPlayer, String[] cmdWords, DAOProgress progressDB, Scanner scanner)
    {
        try
        {
            switch (cmdWords[0])
            {
                case "create" -> {
                    long playerId = Long.parseLong(cmdWords[5]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[5] + " не существует");
                    else
                    {
                        System.out.println("Введите: id, resourceId, score, maxScore");
                        String[] progressArgs = scanner.nextLine().trim().split("\\s*,\\s*");
                        if (checkProgressArgToCreation(progressArgs))
                        {
                            Progress progress = new Progress(new IDToken(Long.parseLong(progressArgs[0]), playerId,
                                    Long.parseLong(progressArgs[1])), Long.parseLong(progressArgs[2]), Long.parseLong(progressArgs[3]));

                            idToPlayer.get(playerId).addProgress(progress);
                            progressDB.create(progress);
                        }
                        else
                            System.out.println("Неверно заданы аргументы progress");
                    }
                }

                case "read" -> {
                    Progress progress = progressDB.readById(Long.parseLong(cmdWords[3]));
                    System.out.println(progress);
                }

                case "delete" -> {
                    long id = Long.parseLong(cmdWords[3]);
                    long playerId = Long.parseLong(cmdWords[5]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[5] + " не существует");
                    else
                    {
                        idToPlayer.get(playerId).deleteProgressWithId(id);
                        progressDB.delete(id);
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static void executeCmdForCurrency(Map<Long, Player> idToPlayer, String[] cmdWords, DAOCurrency currencyDB, Scanner scanner)
    {
        try
        {
            switch (cmdWords[0])
            {
                case "create" -> {
                    long playerId = Long.parseLong(cmdWords[5]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[5] + " не существует");
                    else
                    {
                        System.out.println("Введите: id, resourceId, name, count");
                        String[] currencyArgs = scanner.nextLine().trim().split("\\s*,\\s*");
                        if (checkCurrencyArgToCreation(currencyArgs))
                        {
                            Currency currency = new Currency(new IDToken(Long.parseLong(currencyArgs[0]), playerId,
                                    Long.parseLong(currencyArgs[1])), currencyArgs[2], Long.parseLong(currencyArgs[3]));

                            idToPlayer.get(playerId).addCurrency(currency);
                            currencyDB.create(currency);
                        }
                        else
                            System.out.println("Неверно заданы аргументы currency");
                    }
                }

                case "read" -> {
                    Currency currency = currencyDB.readById(Long.parseLong(cmdWords[3]));
                    System.out.println(currency);
                }

                case "delete" -> {
                    long id = Long.parseLong(cmdWords[3]);
                    long playerId = Long.parseLong(cmdWords[5]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[5] + " не существует");
                    else
                    {
                        idToPlayer.get(playerId).deleteCurrencyWithId(id);
                        currencyDB.delete(id);
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static void executeCmdForItem(Map<Long, Player> idToPlayer, String[] cmdWords, DAOItem itemDB, Scanner scanner)
    {
        try {
            switch (cmdWords[0])
            {
                case "create" -> {
                    long playerId = Long.parseLong(cmdWords[5]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[5] + " не существует");
                    else
                    {
                        System.out.println("Введите: id, resourceId, count, level");
                        String[] itemArgs = scanner.nextLine().trim().split("\\s*,\\s*");
                        if (checkItemArgToCreation(itemArgs))
                        {
                            Item item = new Item(new IDToken(Long.parseLong(itemArgs[0]), playerId,
                                    Long.parseLong(itemArgs[1])), Long.parseLong(itemArgs[2]), Long.parseLong(itemArgs[3]));

                            idToPlayer.get(playerId).addItem(item);
                            itemDB.create(item);
                        }
                        else
                            System.out.println("Неверно заданы аргументы item");
                    }
                }

                case "read" -> {
                    Item item = itemDB.readById(Long.parseLong(cmdWords[3]));
                    System.out.println(item);
                }

                case "delete" -> {
                    long id = Long.parseLong(cmdWords[3]);
                    long playerId = Long.parseLong(cmdWords[5]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[5] + " не существует");
                    else
                    {
                        idToPlayer.get(playerId).deleteItemWithId(id);
                        itemDB.delete(id);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static void executeCmdForPlayer(Map<Long, Player> idToPlayer, String[] cmdWords, DAOPlayer playerDB, Scanner scanner)
    {
        try {
            switch (cmdWords[0])
            {
                case "create" -> {
                    System.out.println("Введите: playerId, nickname");
                    String[] playerArgs = scanner.nextLine().trim().split("\\s*,\\s*");

                    if (checkPlayerArgToCreation(playerArgs))
                    {
                        Player pl = new Player(Long.parseLong(playerArgs[0]), playerArgs[1]);

                        playerDB.create(pl);
                        idToPlayer.put(pl.getPlayerId(), pl);
                    }
                    else
                        System.out.println("Неверно заданы аргументы player");
                }

                case "read" -> {
                    Player pl = playerDB.readById(Long.parseLong(cmdWords[3]));
                    System.out.println(pl);
                }

                case "update" -> {
                    long playerId = Long.parseLong(cmdWords[3]);
                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[3] + " не существует");
                    else
                    {
                        idToPlayer.get(playerId).setNickname(cmdWords[5]);
                        playerDB.update(idToPlayer.get(playerId));
                    }
                }

                case "delete" -> {
                    long playerId = Long.parseLong(cmdWords[3]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[3] + " не существует");
                    else
                    {
                        playerDB.delete(playerId);
                        idToPlayer.remove(playerId);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
