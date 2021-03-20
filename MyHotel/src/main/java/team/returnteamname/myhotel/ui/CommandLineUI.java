package team.returnteamname.myhotel.ui;

import org.jetbrains.annotations.NotNull;
import team.returnteamname.myhotel.ui.menu.Menu;

import java.io.*;

public class CommandLineUI implements IUserInterface
{
    private InputStream  inputStream;
    private OutputStream outputStream;

    private BufferedReader bufferedReader;
    private PrintStream    printStream;

    private boolean continueLoop;

    public CommandLineUI(@NotNull InputStream inputStream, @NotNull OutputStream outputStream)
    {
        setInputStream(inputStream);
        setOutputStream(outputStream);
        continueLoop = true;
    }

    @Override
    public void run()
    {
        try
        {
            showWelcomeMessage();
            menuLoop();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object eventCallback(String event, Object parameter)
    {
        try
        {
            Object object = null;

            switch (event)
            {
                case "Quit":
                    continueLoop = false;
                    break;
                case "printLine":
                    printStream.println(parameter);
                    break;
                case "inputLine":
                    printStream.print(parameter + ": ");
                    object = bufferedReader.readLine();
                    break;
                default:
                    throw new IllegalArgumentException("No such event: " + event);
            }

            return object;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void showWelcomeMessage() throws IOException
    {
        printStream.println("Welcome to MyHotel - hotel database management system\n");
    }

    private void menuLoop()
    {
        Menu currentMenu = Menu.getRootMenu();
        while (continueLoop)
        {
            boolean isRoot = currentMenu.getParent() == null;
            int childrenCount = currentMenu.getChildren().size();
            int actionCount = currentMenu.getActions().size();

            printStream.println("Current location: " + currentMenu.getPath());

            int i = 0;
            if (!isRoot)
            {
                printStream.println((i) + ": Parent menu");
            }
            ++i;

            for (int j = 0; j < childrenCount; ++j, ++i)
            {
                printStream.println(i + ": " + currentMenu.getChildren().get(j) + "/");
            }

            for (int j = 0; j < actionCount; ++j, ++i)
            {
                printStream.println(i + ": " + currentMenu.getActions().get(j));
            }

            int choice;
            printStream.println("Please choose an operation: ");
            for (;;)
            {
                try
                {
                    choice = Integer.parseInt(bufferedReader.readLine().trim());
                    if ((choice == 0 && !isRoot) || (choice > 0 && choice < i))
                        break;
                    else
                        throw new NumberFormatException();
                }
                catch (NumberFormatException e)
                {
                    printStream.println("Please enter a valid number.");
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }

            if (choice == 0)
            {
                currentMenu = currentMenu.getParent();
            }
            else if (choice <= childrenCount)
            {
                currentMenu = currentMenu.getChildren().get(--choice);
            }
            else
            {
                choice -= childrenCount;
                currentMenu.getActions().get(--choice).run(this);
            }
        }
    }

    @NotNull
    public InputStream getInputStream()
    {
        return inputStream;
    }

    public void setInputStream(@NotNull InputStream inputStream)
    {
        this.inputStream = inputStream;
        bufferedReader   = new BufferedReader(new InputStreamReader(inputStream));
    }

    @NotNull
    public OutputStream getOutputStream()
    {
        return outputStream;
    }

    public void setOutputStream(@NotNull OutputStream outputStream)
    {
        this.outputStream = outputStream;
        printStream       = new PrintStream(outputStream);
    }
}
