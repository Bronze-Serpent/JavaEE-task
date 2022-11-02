package barabanov.ORM;

import barabanov.entity.IDToken;
import barabanov.entity.Progress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DAOProgressJDBC implements DAOProgress
{

    private final Connection dbConnection;


    public DAOProgressJDBC(Connection connection)
    {
        this.dbConnection = connection;
    }


    @Override
    public void create(Progress val) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    INSERT INTO progresses (id, playerId, resourceID, score, maxScore)
                    VALUES (?, ?, ?, ?, ?)
                    """)) {
            statement.setLong(1, val.getToken().getId());
            statement.setLong(2, val.getToken().getPlayerId());
            statement.setLong(3, val.getToken().getResourceId());
            statement.setLong(4, val.getScore());
            statement.setLong(5, val.getMaxScore());

            statement.execute();
        }
    }


    @Override
    public Progress readById(long id) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                SELECT *
                FROM progresses
                WHERE id = ?
            """)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return new Progress(new IDToken(resultSet.getLong("id"), resultSet.getLong("playerId"),
                        resultSet.getLong("resourceId")), resultSet.getLong("score"), resultSet.getLong("maxScore"));
            }
        }
    }


    @Override
    public void update(Progress val) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    UPDATE progresses
                    SET playerId = ?, resourceID = ?, score = ?, maxScore = ?
                    WHERE id = ?
                    """)) {
            statement.setLong(1, val.getToken().getPlayerId());
            statement.setLong(2, val.getToken().getResourceId());
            statement.setLong(3, val.getScore());
            statement.setLong(4, val.getMaxScore());
            statement.setLong(5, val.getToken().getId());

            statement.execute();
        }
    }


    @Override
    public void delete(long id) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    DELETE FROM progresses
                    WHERE id = ?
                    """)) {
            statement.setLong(1, id);

            statement.execute();
        }
    }


    @Override
    public List<Progress> readAllWithPlId(long playerID) throws SQLException
    {
        List<Progress> progresses = new LinkedList<>();

        try (PreparedStatement statement = dbConnection.prepareStatement("""
                SELECT *
                FROM progresses
                WHERE playerID = ?
            """)) {
            statement.setLong(1, playerID);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                    progresses.add(new Progress(new IDToken(resultSet.getLong("id"), resultSet.getLong("playerId"),
                            resultSet.getLong("resourceId")), resultSet.getLong("score"), resultSet.getLong("maxScore")));

                return progresses;
            }
        }
    }
}
