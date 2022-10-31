package barabanov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection
{

    private static Connection conn;


    private DBConnection()
    {
        throw new RuntimeException();
    }


    public static void createConnection(String url, String user, String password) throws SQLException
    {
        if (conn == null)
            conn = DriverManager.getConnection(url, user, password);
        else
            throw new RuntimeException("Connection already established");
    }


    public static Connection getConn() throws SQLException { return conn; }


    public static void closeConnection() throws SQLException { conn.close(); }
}
