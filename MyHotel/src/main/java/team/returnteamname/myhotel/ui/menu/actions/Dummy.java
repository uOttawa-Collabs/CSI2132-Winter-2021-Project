package team.returnteamname.myhotel.ui.menu.actions;

import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;

public class Dummy extends AbstractAction
{
    public Dummy()
    {
        super();
    }

    public Dummy(String name)
    {
        super(name);
    }

    @Override
    public Object run(IUserInterface userInterface)
    {
        String input = (String) userInterface.eventCallback("inputLine", "What's you name");
        userInterface.eventCallback("printLine", "Hello " + input + ", I am a dummy action");
        return null;
    }
}
