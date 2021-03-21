package team.returnteamname.myhotel;

import team.returnteamname.myhotel.config.ConfigManager;
import team.returnteamname.myhotel.config.IConfigConstant;
import team.returnteamname.myhotel.ui.CommandLineUI;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.Menu;
import team.returnteamname.myhotel.util.source.FileConfigSource;
import team.returnteamname.myhotel.util.source.IConfigSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main
{
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
        IConfigSource configSource = new FileConfigSource(IConfigConstant.CONFIG_FILE_PATH);
        configSource.loadSource();
        ConfigManager.getInstance().load(configSource.getConfigString());

        // Load menu
        IConfigSource menuSource = new FileConfigSource(IConfigConstant.MENU_FILE_PATH);
        menuSource.loadSource();
        Menu.loadMenu(menuSource.getConfigString());
    }
}
