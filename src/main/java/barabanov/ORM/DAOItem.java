package barabanov.ORM;

import barabanov.entity.Item;

import java.sql.SQLException;
import java.util.List;


public interface DAOItem extends DAOInterface<Item>
{

    List<Item> readAllWithPlId(long playerID) throws SQLException;
}
