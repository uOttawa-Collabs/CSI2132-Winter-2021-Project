package team.returnteamname.myhotelcustomer.util.source;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileConfigSource implements IConfigSource
{
    private final String filePath;
    private       String configString;

    private boolean isSourceLoaded;

    public FileConfigSource(@NotNull String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    public void loadSource() throws IOException
    {
        configString   = Files.readString(Path.of(filePath));
        isSourceLoaded = true;
    }

    @Override
    public String getConfigString()
    {
        if (isSourceLoaded)
        {
            return configString;
        }
        else
        {
            throw new IllegalStateException("Source is not initialized");
        }
    }

    @Override
    public boolean getIfSourceLoaded()
    {
        return isSourceLoaded;
    }
}
