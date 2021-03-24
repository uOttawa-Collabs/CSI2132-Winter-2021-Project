package team.returnteamname.myhotel.ui.menu.actions;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
                if (query.startsWith("SELECT"))
                    printResultSet(userInterface, BaseDao.query(query));
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

    private void printResultSet(IUserInterface userInterface, ResultSet resultSet) throws SQLException
    {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int               columnCount       = resultSetMetaData.getColumnCount();
        while (resultSet.next())
        {
            for (int i = 1; i <= columnCount; ++i)
            {
                if (i > 1)
                    userInterface.eventCallback("print", " | ");
                userInterface
                    .eventCallback("print", resultSetMetaData.getColumnName(i) + " " + resultSet.getString(i));
            }
            userInterface.eventCallback("printLine", "");
        }
    }
}
