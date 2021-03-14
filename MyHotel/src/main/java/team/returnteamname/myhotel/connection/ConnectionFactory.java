package team.returnteamname.myhotel.connection;

import org.jetbrains.annotations.NotNull;
import team.returnteamname.myhotel.config.Config;
import team.returnteamname.myhotel.config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
    private static ConnectionFactory connectionFactory;
    private        Config            config;
    private        Connection        connection;

    private ConnectionFactory() {}

    public static ConnectionFactory getInstance()
    {
        if (connectionFactory == null)
        {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection()
    {
        if (connection != null)
        {
            try
            {
                if (connection.isValid(3) && this.config == ConfigManager.getInstance().getConfig())
                {
                    return connection;
                }
                else
                {
                    connection.close();
                }
            }
            catch (SQLException ignored)
            {
                // Impossible to get here, since isValid() only throws SQLException on a negative argument
            }
        }

        // Invalid, expired, or null connection, creating a new one
        try
        {
            config = ConfigManager.getInstance().getConfig();

            Class.forName(config.getDriver());
            String url = "jdbc:" + config.getDbms() + "://" + config.getServerAddress() + "/" + config.getDatabase();
            connection = DriverManager.getConnection(url, config.getUsername(), config.getPassword());
            return connection;
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
