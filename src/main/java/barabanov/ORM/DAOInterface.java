package barabanov.ORM;

import java.sql.SQLException;


public interface DAOInterface<T>
{

    void create(T val) throws SQLException;

    T readById(long id) throws SQLException;

    void update(T val) throws SQLException;

    void delete(long id) throws SQLException;
}
