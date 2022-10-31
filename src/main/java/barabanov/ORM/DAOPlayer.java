package barabanov.ORM;

import barabanov.entity.Player;

import java.sql.SQLException;
import java.util.List;


public interface DAOPlayer extends DAOInterface<Player>
{

    List<Player> readAll() throws SQLException;
}
