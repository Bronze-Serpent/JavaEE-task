package barabanov.ORM;

import barabanov.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DAOPlayerJDBC implements DAOPlayer
{

    private final Connection dbConnection;


    public DAOPlayerJDBC(Connection connection)
    {
        this.dbConnection = connection;
    }


    @Override
    public void create(Player val) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    INSERT INTO players (playerId, nickname)
                    VALUES (?, ?)
                    """)) {
            statement.setLong(1, val.getPlayerId());
            statement.setString(2, val.getNickname());

            statement.execute();
        }
    }


    @Override
    public Player readById(long id) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                SELECT *
                FROM players
                WHERE playerId = ?
            """)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return new Player(resultSet.getLong("playerId"), resultSet.getString("nickname"));
            }
        }
    }


    @Override
    public void update(Player val) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    UPDATE players
                    SET nickname = ?
                    WHERE playerId = ?
                    """)) {
            statement.setString(1, val.getNickname());
            statement.setLong(2, val.getPlayerId());

            statement.execute();
        }
    }


    @Override
    public void delete(long id) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    DELETE FROM players
                    WHERE playerId = ?
                    """)) {
            statement.setLong(1, id);

            statement.execute();
        }
    }


    @Override
    public List<Player> readAll() throws SQLException
    {
        List<Player> players = new LinkedList<>();

        try (PreparedStatement statement = dbConnection.prepareStatement("""
                SELECT *
                FROM players
            """)) {
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                    players.add(new Player(resultSet.getLong("playerId"),
                            resultSet.getString("nickname")));

                return players;
            }
        }
    }
}
