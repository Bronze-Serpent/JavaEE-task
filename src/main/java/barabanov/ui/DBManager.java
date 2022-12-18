package barabanov.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager
{
    private final Connection conn;
    private static final DBManager manager = new DBManager();

    private DBManager()
    {
        if (manager != null)
            throw new RuntimeException("This class must only be instantiated once.");
        else {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("no JDBC driver");
            }
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java_task_1", "postgres", "87690");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Connection getConn() { return conn; }

    public static DBManager getManager() { return manager; }

    // TODO: Well, how to change it?
    @Override
    protected void finalize() throws Throwable { manager.conn.close(); }
}
