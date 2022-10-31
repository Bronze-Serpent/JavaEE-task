package barabanov.ORM;

import barabanov.entity.Currency;

import java.sql.SQLException;
import java.util.List;


public interface DAOCurrency extends DAOInterface<Currency>
{

    List<Currency> readAllWithPlId(long playerID) throws SQLException;
}
