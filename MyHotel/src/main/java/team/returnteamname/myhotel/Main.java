package team.returnteamname.myhotel;

import team.returnteamname.myhotel.config.ConfigManager;
import team.returnteamname.myhotel.config.source.FileConfigSource;
import team.returnteamname.myhotel.config.source.IConfigSource;
import team.returnteamname.myhotel.connection.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;

public class Main
{
    private final static String            configFilePath = "config.json";
    private static       ConfigManager     configManager;
    private static       ConnectionFactory connectionFactory;

    public static void main(String[] args)
    {
        try
        {
            initialize();
            Connection connection = connectionFactory.getConnection(configManager.getConfig());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void initialize() throws IOException
    {
        IConfigSource configSource = new FileConfigSource(configFilePath);
        configSource.loadSource();

        configManager = ConfigManager.getInstance();
        configManager.load(configSource.getConfigString());

        connectionFactory = ConnectionFactory.getInstance();
    }
}
