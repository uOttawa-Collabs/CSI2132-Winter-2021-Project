package team.returnteamname.myhotelcustomer.util.source;

import java.io.IOException;

public interface IConfigSource
{
    void loadSource() throws IOException;

    boolean getIfSourceLoaded();

    String getConfigString();
}
