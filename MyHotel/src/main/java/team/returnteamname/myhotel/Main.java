package team.returnteamname.myhotel;

import team.returnteamname.myhotel.config.ConfigManager;
import team.returnteamname.myhotel.config.source.FileConfigSource;
import team.returnteamname.myhotel.config.source.IConfigSource;

import java.io.IOException;

public class Main
{
    private final static String            configFilePath = "config.json";

    public static void main(String[] args)
    {
        try
        {
            initialize();
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

        ConfigManager.getInstance().load(configSource.getConfigString());
    }
}
