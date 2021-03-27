package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.pojo.Employment;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class EmploymentWithdrawalAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        try
        {
            BaseDao baseDao = new BaseDao();

            int id = getEmployeeIdFromUser(userInterface);
            baseDao.delete(new Employment(null, null, id));
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed, reason: " + e.getMessage());
        }

        return null;
    }

    private int getEmployeeIdFromUser(IUserInterface userInterface) throws SQLException
    {
        String       query     = "SELECT id, full_name, salary, hotel_brand_name, hotel_name FROM employment JOIN employee ON employment.employee_id = employee.id;";
        ResultSet    resultSet = BaseDao.query(query);
        Set<Integer> set       = new HashSet<>();

        StringBuilder stringBuilder = new StringBuilder();
        while (resultSet.next())
        {
            set.add(resultSet.getInt(1));
            stringBuilder.append("id = ").append(resultSet.getInt(1)).append(" | ");
            stringBuilder.append("fullName = ").append(resultSet.getString(2)).append(" | ");
            stringBuilder.append("salary = ").append(resultSet.getBigDecimal(3)).append(" | ");
            stringBuilder.append("hotelBrandName = ").append(resultSet.getString(4)).append(" | ");
            stringBuilder.append("hotelName = ").append(resultSet.getString(5)).append('\n');
        }

        if (set.isEmpty())
            throw new IllegalStateException("no assigned employee found");
        else
        {
            userInterface.eventCallback("printLine", "Assigned employee list: ");
            userInterface.eventCallback("print", stringBuilder);

            int id = -1;
            do
            {
                try
                {
                    String input = (String) userInterface.eventCallback("readLine", "Please enter the ID of an employee, or type \"q\" to quit");
                    input = input.trim();
                    if (input.equals("q"))
                        return -1;
                    id = Integer.parseInt(input);
                }
                catch (NumberFormatException ignored) {}
            }
            while (((Predicate<Integer>) (i) ->
            {
                boolean result = !set.contains(i);
                if (result)
                    userInterface.eventCallback("printLine", "Invalid employee ID, please try again.");
                return result;
            }).test(id));

            return id;
        }
    }
}
