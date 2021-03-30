package team.returnteamname.ehotel.config.source;

import java.io.IOException;

public interface IConfigSource
{
    void loadSource() throws IOException;

    boolean getIfSourceLoaded();

    String getConfigString();
}
