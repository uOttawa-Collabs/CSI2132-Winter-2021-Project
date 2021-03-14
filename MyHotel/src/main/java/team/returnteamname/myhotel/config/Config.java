package team.returnteamname.myhotel.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Config implements Serializable
{
    private final String dbms;
    private final String driver;

    private final String serverAddress;
    private final String database;
    private final String username;
    private final String password;

    public Config(@NotNull String dbms, @NotNull String driver, @NotNull String serverAddress, @NotNull String database,
                  @NotNull String username,
                  @NotNull String password)
    {
        this.dbms          = dbms;
        this.driver        = driver;
        this.serverAddress = serverAddress;
        this.database      = database;
        this.username      = username;
        this.password      = password;
    }

    public String getDbms()
    {
        return dbms;
    }

    public String getDriver()
    {
        return driver;
    }

    public String getServerAddress()
    {
        return serverAddress;
    }

    public String getDatabase()
    {
        return database;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String toString()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
