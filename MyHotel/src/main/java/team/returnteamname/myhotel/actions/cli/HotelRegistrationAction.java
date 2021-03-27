package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;

public class HotelRegistrationAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        userInterface.eventCallback("printLine", "");


        return null;
    }
}
