package team.returnteamname.myhotel.ui.menu.actions;

import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;

public class QuitAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        userInterface.eventCallback("Quit", null);
        return null;
    }
}
