package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;
import team.returnteamname.myhotel.util.Util;

import java.sql.SQLException;
import java.util.function.Supplier;

public class ExecuteSqlAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        do
        {
            String query = (String) userInterface.eventCallback("readLine", "Input an SQL query");
            query = query.trim();

            try
            {
                if (query.toUpperCase().startsWith("SELECT"))
                    userInterface.eventCallback("print", Util.formatResultSet(BaseDao.query(query)));
                else
                {
                    int affectedRowsCount = BaseDao.update(query);
                    userInterface
                        .eventCallback("printLine", "Query completed, " + affectedRowsCount + " row(s) affected.");
                }
            }
            catch (SQLException e)
            {
                userInterface.eventCallback("printLine", "SQL query failed, reason: " + e.getMessage());
            }
        }
        while (((Supplier<Boolean>) () ->
        {
            String input = (String) userInterface.eventCallback("readLine", "More operations? <y/N>");
            return !input.isEmpty() && (Character.toUpperCase(input.charAt(0)) == 'Y');
        }).get());

        return null;
    }
}
