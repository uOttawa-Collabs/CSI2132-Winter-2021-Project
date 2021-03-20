package team.returnteamname.myhotel;

import org.postgresql.util.PGInterval;
import team.returnteamname.myhotel.config.ConfigManager;
import team.returnteamname.myhotel.util.source.FileConfigSource;
import team.returnteamname.myhotel.util.source.IConfigSource;
import team.returnteamname.myhotel.ui.CommandLineUI;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.Menu;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Main
{
    private final static String configFilePath = "config.json";
    private final static String menuFilePath = "cliMenu.json";

    public static void main(String[] args)
    {
        try
        {
            initialize();
            IUserInterface userInterface = new CommandLineUI(System.in, System.out);
            userInterface.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void initialize()
        throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException
    {
        // Load config
        IConfigSource configSource = new FileConfigSource(configFilePath);
        configSource.loadSource();
        ConfigManager.getInstance().load(configSource.getConfigString());

        // Load menu
        IConfigSource menuSource = new FileConfigSource(menuFilePath);
        menuSource.loadSource();
        Menu.loadMenu(menuSource.getConfigString());
    }
}
