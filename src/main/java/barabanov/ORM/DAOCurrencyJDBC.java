package barabanov.ORM;

import barabanov.entity.Currency;
import barabanov.entity.IDToken;
import barabanov.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DAOCurrencyJDBC implements DAOCurrency
{

    @Override
    public void create(Currency val) throws SQLException
    {
        try (PreparedStatement statement = DBConnection.getConn().prepareStatement("""
                    INSERT INTO currencies (id, playerId, resourceID, name, count)
                    VALUES (?, ?, ?, ?, ?)
                    """)) {
            statement.setLong(1, val.getToken().getId());
            statement.setLong(2, val.getToken().getPlayerId());
            statement.setLong(3, val.getToken().getResourceId());
            statement.setString(4, val.getName());
            statement.setLong(5, val.getCount());

            statement.execute();
        }
    }


    @Override
    public Currency readById(long id) throws SQLException
    {
        try (PreparedStatement statement = DBConnection.getConn().prepareStatement("""
                SELECT *
                FROM currencies
                WHERE id = ?
            """)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return new Currency(new IDToken(resultSet.getLong("id"), resultSet.getLong("playerId"),
                        resultSet.getLong("resourceId")), resultSet.getString("name"), resultSet.getLong("count"));
            }
        }
    }


    @Override
    public void update(Currency val) throws SQLException
    {
        try (PreparedStatement statement = DBConnection.getConn().prepareStatement("""
                    UPDATE currencies
                    SET playerId = ?, resourceID = ?, name = ?, count = ?
                    WHERE id = ?
                    """)) {
            statement.setLong(1, val.getToken().getPlayerId());
            statement.setLong(2, val.getToken().getResourceId());
            statement.setString(3, val.getName());
            statement.setLong(4, val.getCount());
            statement.setLong(5, val.getToken().getId());

            statement.execute();
        }
    }


    @Override
    public void delete(Currency val) throws SQLException {
        try (PreparedStatement statement = DBConnection.getConn().prepareStatement("""
                    DELETE FROM currencies
                    WHERE id = ?
                    """)) {
            statement.setLong(1, val.getToken().getId());

            statement.execute();
        }
    }


    @Override
    public List<Currency> readAllWithPlId(long playerID) throws SQLException
    {
        List<Currency>  currencies = new LinkedList<>();

        try (PreparedStatement statement = DBConnection.getConn().prepareStatement("""
                SELECT *
                FROM currencies
                WHERE playerID = ?
            """)) {
            statement.setLong(1, playerID);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                    currencies.add(new Currency(new IDToken(resultSet.getLong("id"), resultSet.getLong("playerId"),
                            resultSet.getLong("resourceId")), resultSet.getString("name"), resultSet.getLong("count")));

                return currencies;
            }
        }
    }
}
