package barabanov.ORM;

import barabanov.entity.Progress;

import java.sql.SQLException;
import java.util.List;


public interface DAOProgress extends DAOInterface<Progress>
{

    List<Progress> readAllWithPlId(long playerID) throws SQLException;
}
