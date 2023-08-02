package barabanov.ui;

import barabanov.service.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager
{
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";

    private final Connection CONNECTION;
    private static final DBManager MANAGER = new DBManager();


    private DBManager()
    {
        if (MANAGER != null)
            throw new RuntimeException("This class must only be instantiated once.");
        else {
            loadDriver();
            try {
                CONNECTION = DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                        PropertiesUtil.get(USERNAME_KEY),
                        PropertiesUtil.get(PASSWORD_KEY));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Connection getConn() { return CONNECTION; }

    public static DBManager getManager() { return MANAGER; }

    // To change this, you need to have the connection managed by Tomcat itself.
    @Override
    protected void finalize() throws Throwable { MANAGER.CONNECTION.close(); }


    // Before java 1.8 there was a problem with the drivers that we connected via add. libraries and they needed to be loaded.
    // Using Class.forName we load our class into metaspace (in memory in JVM)
    private static void loadDriver()
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("no JDBC driver");
        }
    }
}
