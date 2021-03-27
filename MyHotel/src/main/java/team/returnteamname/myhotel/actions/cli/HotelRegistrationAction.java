package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;
import team.returnteamname.myhotel.util.Pair;

public class HotelRegistrationAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        try
        {

        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed, reason: " + e.getMessage());
        }
        return null;
    }
}
