package barabanov.ui;


import barabanov.ORM.*;
import barabanov.entity.*;
import barabanov.service.CurrencyService;
import barabanov.service.ItemService;
import barabanov.service.PlayerService;
import barabanov.service.ProgressService;
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
            CurrencyService currencyS = new CurrencyService(new DAOCurrencyJDBC(dbConnection));
            ItemService itemS = new ItemService(new DAOItemJDBC(dbConnection));
            ProgressService progressS = new ProgressService(new DAOProgressJDBC(dbConnection));
            PlayerService playerS = new PlayerService(new DAOPlayerJDBC(dbConnection), currencyS, itemS, progressS);

            List<Player> players = PlayerService.readFromJson("players.json");

            //playerS.writeToDB(players);
            Map<Long, Player> idToPlayer = players.stream()
                    .collect(Collectors.toMap(Player::getPlayerId, p -> p));


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
                        case "player" -> executeCmdForPlayer(idToPlayer, cmdWords, playerS, scanner);

                        case "item" -> executeCmdForItem(idToPlayer, cmdWords, itemS, scanner);

                        case "currency" -> executeCmdForCurrency(idToPlayer, cmdWords, currencyS, scanner);

                        case "progress" -> executeCmdForProgress(idToPlayer, cmdWords, progressS, scanner);
                    }
                else
                    System.out.println(checkResult);
            }

        }

    }


    private static void executeCmdForProgress(Map<Long, Player> idToPlayer, String[] cmdWords, ProgressService progressS, Scanner scanner)
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
                            progressS.writeToDB(progress);
                        }
                        else
                            System.out.println("Неверно заданы аргументы progress");
                    }
                }

                case "read" -> {
                    Progress progress = progressS.readFromDB(Long.parseLong(cmdWords[3]));
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
                        progressS.deleteDB(id);
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static void executeCmdForCurrency(Map<Long, Player> idToPlayer, String[] cmdWords, CurrencyService currencyS, Scanner scanner)
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
                            currencyS.writeToDB(currency);
                        }
                        else
                            System.out.println("Неверно заданы аргументы currency");
                    }
                }

                case "read" -> {
                    Currency currency = currencyS.readFromDB(Long.parseLong(cmdWords[3]));
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
                        currencyS.deleteFromDB(id);
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static void executeCmdForItem(Map<Long, Player> idToPlayer, String[] cmdWords, ItemService itemS, Scanner scanner)
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
                            itemS.writeToDB(item);
                        }
                        else
                            System.out.println("Неверно заданы аргументы item");
                    }
                }

                case "read" -> {
                    Item item = itemS.readFromDB(Long.parseLong(cmdWords[3]));
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
                        itemS.deleteFromDB(id);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static void executeCmdForPlayer(Map<Long, Player> idToPlayer, String[] cmdWords, PlayerService playerS, Scanner scanner)
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

                        playerS.writeToDB(pl);
                        idToPlayer.put(pl.getPlayerId(), pl);
                    }
                    else
                        System.out.println("Неверно заданы аргументы player");
                }

                case "read" -> {
                    Player pl = playerS.readFromDB(Long.parseLong(cmdWords[3]));
                    System.out.println(pl);
                }

                case "update" -> {
                    long playerId = Long.parseLong(cmdWords[3]);
                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[3] + " не существует");
                    else
                    {
                        idToPlayer.get(playerId).setNickname(cmdWords[5]);
                        playerS.updateDB(idToPlayer.get(playerId));
                    }
                }

                case "delete" -> {
                    long playerId = Long.parseLong(cmdWords[3]);

                    if ( !idToPlayer.containsKey(playerId))
                        System.out.println("Player с playerId= " + cmdWords[3] + " не существует");
                    else
                    {
                        playerS.deleteFromDB(playerId);
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
