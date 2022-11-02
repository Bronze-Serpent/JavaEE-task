package barabanov.ORM;


import barabanov.entity.IDToken;
import barabanov.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DAOItemJDBC implements DAOItem
{

    private final Connection dbConnection;


    public DAOItemJDBC(Connection connection)
    {
        this.dbConnection = connection;
    }


    @Override
    public void create(Item val) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    INSERT INTO items (id, playerId, resourceID, count, level)
                    VALUES (?, ?, ?, ?, ?)
                    """)) {
            statement.setLong(1, val.getToken().getId());
            statement.setLong(2, val.getToken().getPlayerId());
            statement.setLong(3, val.getToken().getResourceId());
            statement.setLong(4, val.getCount());
            statement.setLong(5, val.getLevel());

            statement.execute();
        }
    }


    @Override
    public Item readById(long id) throws SQLException {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                SELECT *
                FROM items
                WHERE id = ?
            """)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return new Item(new IDToken(resultSet.getLong("id"), resultSet.getLong("playerId"),
                        resultSet.getLong("resourceId")), resultSet.getLong("count"), resultSet.getLong("level"));
            }
        }
    }


    @Override
    public void update(Item val) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    UPDATE items
                    SET playerId = ?, resourceID = ?, count = ?, level = ?
                    WHERE id = ?
                    """)) {
            statement.setLong(1, val.getToken().getPlayerId());
            statement.setLong(2, val.getToken().getResourceId());
            statement.setLong(3, val.getCount());
            statement.setLong(4, val.getLevel());
            statement.setLong(5, val.getToken().getId());

            statement.execute();
        }
    }


    @Override
    public void delete(long id) throws SQLException
    {
        try (PreparedStatement statement = dbConnection.prepareStatement("""
                    DELETE FROM items
                    WHERE id = ?
                    """)) {
            statement.setLong(1, id);

            statement.execute();
        }
    }


    @Override
    public List<Item> readAllWithPlId(long playerID) throws SQLException
    {
        List<Item> items = new LinkedList<>();

        try (PreparedStatement statement = dbConnection.prepareStatement("""
                SELECT *
                FROM items
                WHERE playerID = ?
            """)) {
            statement.setLong(1, playerID);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                    items.add(new Item(new IDToken(resultSet.getLong("id"), resultSet.getLong("playerId"),
                            resultSet.getLong("resourceId")), resultSet.getLong("count"), resultSet.getLong("level")));

                return items;
            }
        }
    }
}
