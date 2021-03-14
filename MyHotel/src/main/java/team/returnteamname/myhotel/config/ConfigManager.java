package team.returnteamname.myhotel.config;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ConfigManager
{
    private static ConfigManager configManager;

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
        if (configManager == null)
        {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    public void load(
        @NotNull
            String configString)
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
