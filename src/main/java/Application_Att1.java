import barabanov.entity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class Application_Att1
{

    public static void main(String[] args) throws IOException, ParseException, SQLException
    {
        List<Player> players = parsePlayersFromJson("./players.json");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection dBConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java_task_1",
                "postgres", "87690");

        createDatabase(dBConn);
        writePlayersToDB(players, dBConn);

        List<Player> playerFromDB =  readPlayersFromDB(dBConn);
        System.out.println(playerFromDB.size() == players.size());

        deleteDatabase(dBConn);
    }


    static void createDatabase(Connection dBConn) throws SQLException
    {
        try(Statement stmt = dBConn.createStatement())
        {
            stmt.executeUpdate("CREATE TABLE players (playerId INT PRIMARY KEY, nickname VARCHAR(150) NOT NULL)");

            stmt.executeUpdate("CREATE TABLE progresses (id INT PRIMARY KEY, playerId INT NOT NULL," +
                    " resourceID INT NOT NULL, score INT NOT NULL, maxScore INT NOT NULL)");
            stmt.execute("ALTER TABLE progresses ADD CONSTRAINT FK_progresses_players FOREIGN KEY (playerId)" +
                    " REFERENCES players(playerId) ON DELETE CASCADE");

            stmt.executeUpdate("CREATE TABLE currencies (id INT PRIMARY KEY, playerId INT NOT NULL," +
                    " resourceID INT NOT NULL, name VARCHAR(150) NOT NULL, count INT NOT NULL)");
            stmt.execute("ALTER TABLE currencies ADD CONSTRAINT FK_currencies_players FOREIGN KEY (playerId)" +
                    " REFERENCES players(playerId) ON DELETE CASCADE");

            stmt.executeUpdate("CREATE TABLE items (id INT PRIMARY KEY, playerId INT NOT NULL," +
                    " resourceID INT NOT NULL, count INT NOT NULL, level INT NOT NULL)");
            stmt.execute("ALTER TABLE items ADD CONSTRAINT FK_items_players FOREIGN KEY (playerId)" +
                    " REFERENCES players(playerId) ON DELETE CASCADE");
        }
    }


    static void writePlayersToDB(List<Player> players, Connection dBConn) throws SQLException
    {
        try(Statement stmt = dBConn.createStatement())
        {
            for (Player player : players)
            {
                stmt.executeUpdate("INSERT INTO players (playerId, nickname) " +
                        "VALUES (" + player.getPlayerId() + ", " + "'" + player.getNickname() + "'" + ");");

                for (Progress prs : player.getProgresses())
                {
                    IDToken idt = prs.getToken();
                    stmt.executeUpdate("INSERT INTO progresses (id, playerId, resourceID, score, maxScore) " +
                            "VALUES (" + idt.getId() + ", " + idt.getPlayerId() + ", " + idt.getResourceId() + ", "
                            + prs.getScore() + ", " + prs.getMaxScore() + ");");
                }
                for (Currency crcy : player.getCurrencies())
                {
                    IDToken idt = crcy.getToken();
                    stmt.executeUpdate("INSERT INTO currencies (id, playerId, resourceID, name, count) " +
                            "VALUES (" + idt.getId() + ", " + idt.getPlayerId() + ", " + idt.getResourceId() + ", "
                            + "'" + crcy.getName() + "'" + ", " + crcy.getCount() + ");");
                }
                for (Item itm : player.getItems())
                {
                    IDToken idt = itm.getToken();
                    stmt.executeUpdate("INSERT INTO items (id, playerId, resourceID, count, level) " +
                            "VALUES (" + idt.getId() + ", " + idt.getPlayerId() + ", " + idt.getResourceId() + ", "
                            + itm.getCount() + ", " + itm.getLevel() + ");");
                }
            }
        }
    }


    static List<Player> readPlayersFromDB(Connection dBConn) throws SQLException
    {
        List<Player> players = new LinkedList<>();

        try(Statement plStmt = dBConn.createStatement();
            Statement prStmt = dBConn.createStatement();
            Statement curStmt = dBConn.createStatement();
            Statement itmStmt = dBConn.createStatement())
        {
            ResultSet plResultSet = plStmt.executeQuery("SELECT * FROM players ");
            while (plResultSet.next())
            {
                Player readPlayer = new Player(plResultSet.getLong("playerId"),
                        plResultSet.getString("nickname"));

                ResultSet prsResultSet = prStmt.executeQuery("SELECT * FROM progresses WHERE playerId = " +
                        plResultSet.getLong("playerId"));
                while (prsResultSet.next())
                    readPlayer.addProgress(new Progress(new IDToken(prsResultSet.getLong("id"),
                            prsResultSet.getLong("playerId"), prsResultSet.getLong("resourceID")),
                            prsResultSet.getLong("score"), prsResultSet.getLong("maxScore")));

                ResultSet crcyResultSet = curStmt.executeQuery("SELECT * FROM currencies WHERE playerId = " +
                        plResultSet.getLong("playerId"));
                while (crcyResultSet.next())
                    readPlayer.addCurrency(new Currency(new IDToken(crcyResultSet.getLong("id"),
                            crcyResultSet.getLong("playerId"), crcyResultSet.getLong("resourceID")),
                            crcyResultSet.getString("name"),  crcyResultSet.getLong("count")));

                ResultSet itmResultSet = itmStmt.executeQuery("SELECT * FROM items WHERE playerId = " +
                        plResultSet.getLong("playerId"));
                while (itmResultSet.next())
                    readPlayer.addCurrency(new Currency(new IDToken(itmResultSet.getLong("id"),
                            itmResultSet.getLong("playerId"), itmResultSet.getLong("resourceID")),
                            itmResultSet.getString("count"),  itmResultSet.getLong("level")));

                players.add(readPlayer);
            }
        }

        return players;
    }


    static void deleteDatabase(Connection dBConn) throws SQLException
    {
        try(Statement stmt = dBConn.createStatement())
        {
            stmt.executeUpdate("DROP TABLE progresses");
            stmt.executeUpdate("DROP TABLE currencies");
            stmt.executeUpdate("DROP TABLE items");
            stmt.executeUpdate("DROP TABLE players");
        }
    }


    // метод реализован через примитивную json-simple. Существуют гораздо менее многословные библиотеки
    // для парсинга json в Java. Однако json-simple использовалась для простоты восприятия т.к. это первая работа с json
    static List<Player> parsePlayersFromJson(String fileName) throws IOException, ParseException
    {
        // считываем json файл
        JSONArray jsonArr = (JSONArray) new JSONParser().parse(new FileReader(fileName));

        List<Player> players = new LinkedList<>();

        // идём по всем игрокам
        for (Object obj : jsonArr)
        {
            JSONObject playerJson = (JSONObject) obj;
            Player readPlayer = new Player((long) playerJson.get("playerId"), (String) playerJson.get("nickname"));

            JSONArray jArrPrgs = (JSONArray) playerJson.get("progresses");

            // идём по progresses в 1 игроке
            for (Object o : jArrPrgs)
            {
                JSONObject jPrgs = (JSONObject) o;
                readPlayer.addProgress(new Progress(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                        (long) jPrgs.get("resourceId")), (long) jPrgs.get("score"), (long) jPrgs.get("maxScore")));
            }

            JSONArray jArrCrcy = (JSONArray) playerJson.get("currencies");

            // идём по currencies в 1 игроке
            for (Object o : jArrCrcy)
            {
                JSONObject jPrgs = (JSONObject) o;
                readPlayer.addCurrency(new Currency(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                        (long) jPrgs.get("resourceId")), (String) jPrgs.get("name"), (long) jPrgs.get("count")));
            }

            JSONArray jArrItm = (JSONArray) playerJson.get("items");

            // идём по items в 1 игроке
            for (Object o : jArrItm)
            {
                JSONObject jPrgs = (JSONObject) o;
                readPlayer.addItem(new Item(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                        (long) jPrgs.get("resourceId")), (long) jPrgs.get("count"), (long) jPrgs.get("level")));
            }

            players.add(readPlayer);
        }

        return players;
    }
}
