package team.returnteamname.myhotel.ui.menu;

import team.returnteamname.myhotel.ui.IUserInterface;

public abstract class AbstractAction
{
    private String name;

    public AbstractAction()
    {
        this.name = getClass().getSimpleName();
    }

    public AbstractAction(String name)
    {
        this.name = name;
    }

    public abstract Object run(IUserInterface userInterface);

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
