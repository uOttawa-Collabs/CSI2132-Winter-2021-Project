package team.returnteamname.myhotel.ui.menu.actions.cli;

import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.actions.AbstractAction;

public class QuitAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        userInterface.eventCallback("Quit", null);
        return null;
    }
}
