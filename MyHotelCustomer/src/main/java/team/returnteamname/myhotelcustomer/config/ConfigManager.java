package team.returnteamname.myhotelcustomer.config;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ConfigManager
{
    private static ConfigManager instance;

    private final Gson gson;

    private Config  config;
    private boolean isConfigLoaded;

    // Singleton
    private ConfigManager()
    {
        gson = new Gson();
    }

    public static ConfigManager getInstance()
    {
        if (instance == null)
        {
            instance = new ConfigManager();
        }
        return instance;
    }

    public void load(@NotNull String configString)
    {
        config         = gson.fromJson(configString, Config.class);
        isConfigLoaded = true;
    }

    public Config getConfig()
    {
        if (isConfigLoaded)
        {
            return config;
        }
        else
        {
            throw new IllegalStateException("Config manager is not loaded");
        }
    }
}
