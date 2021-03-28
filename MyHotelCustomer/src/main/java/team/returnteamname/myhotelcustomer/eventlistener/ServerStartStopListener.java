package team.returnteamname.myhotelcustomer.eventlistener;

import org.apache.commons.lang3.SystemUtils;
import team.returnteamname.myhotelcustomer.config.ConfigManager;
import team.returnteamname.myhotelcustomer.util.source.FileConfigSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;

public class ServerStartStopListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        try
        {
            FileConfigSource fileConfigSource = new FileConfigSource(
                SystemUtils.getUserHome()
                           .getAbsolutePath() + File.separator + ".2132" + File.separator + "config.json");
            fileConfigSource.loadSource();
            ConfigManager.getInstance().load(fileConfigSource.getConfigString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
