package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.pojo.Employee;
import team.returnteamname.myhotel.pojo.Employment;
import team.returnteamname.myhotel.pojo.Hotel;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;
import team.returnteamname.myhotel.util.Pair;
import team.returnteamname.myhotel.util.Util;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmploymentAssignmentAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        try
        {
            BaseDao baseDao = new BaseDao();

            int                  id    = getEmployeeIdFromUser(userInterface);
            Pair<String, String> hotel = getHotelFromUser(userInterface, baseDao);

            Employment employment = new Employment(hotel.getKey(), hotel.getValue(), id);
            baseDao.insert(employment);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed, reason: " + e.getMessage());
        }

        return null;
    }

    private int getEmployeeIdFromUser(IUserInterface userInterface) throws SQLException
    {
        String                 query     = "SELECT id, full_name, salary, role FROM employee WHERE id NOT IN (SELECT employee_id FROM employment);";
        Map<Integer, Employee> map       = new HashMap<>();
        ResultSet              resultSet = BaseDao.query(query);

        while (resultSet.next())
        {
            Employee employee = new Employee();
            employee.setToStringPrintNullValues(false);

            employee.setId(resultSet.getInt(1));
            employee.setFullName(resultSet.getString(2));
            employee.setSalary(resultSet.getBigDecimal(3));
            employee.setRole(resultSet.getString(4));

            map.put(employee.getId(), employee);
        }

        if (map.isEmpty())
            throw new IllegalStateException("no free employee found");
        else
        {
            userInterface.eventCallback("printLine", "Free employee list:");
            StringBuilder stringBuilder = new StringBuilder();

            for (Object object : ((Map<?, ?>) map).values())
                stringBuilder.append(object).append('\n');

            userInterface.eventCallback("print", stringBuilder.toString());

            int id = -1;
            do
            {
                try
                {
                    id = Integer.parseInt(
                        (String) userInterface.eventCallback("readLine", "Please enter the ID of an employee"));
                }
                catch (NumberFormatException ignored) {}
            }
            while (((Predicate<Integer>) (i) ->
            {
                boolean result = !map.containsKey(i);
                if (result)
                    userInterface.eventCallback("printLine", "Invalid employee ID, please try again.");
                return result;
            }).test(id));

            return id;
        }
    }

    private Pair<String, String> getHotelFromUser(IUserInterface userInterface, BaseDao baseDao) throws SQLException
    {
        ArrayList<AbstractPojo> hotels = baseDao.select(new Hotel()).getKey();

        if (hotels.isEmpty())
            throw new IllegalStateException("no available hotel found");
        else
        {
            userInterface.eventCallback("printLine", "Available hotel lists: ");
            for (AbstractPojo pojo : hotels)
            {
                Hotel hotel = (Hotel) pojo;
                hotel.setToStringPrintNullValues(false);
                hotel.setAddress(null);
                hotel.setEmailAddress(null);
                hotel.setNumberOfRooms(null);
                hotel.setStarCategory(null);

                userInterface.eventCallback("printLine", hotel);
            }

            String hotelBrandName = null;
            String hotelName = null;
            do
            {
                hotelBrandName = (String) userInterface.eventCallback("readLine", "Please specify a hotel brand name");
                hotelName = (String) userInterface.eventCallback("readLine", "Please specify a hotel name");
            }
            while (((BiPredicate<String, String>) (f, s) ->
            {
                for (AbstractPojo pojo : hotels)
                {
                    Hotel hotel = (Hotel) pojo;
                    assert hotel.getHotelBrandName() != null;
                    assert hotel.getHotelName() != null;

                    boolean result = !(hotel.getHotelBrandName().equals(f) && hotel.getHotelName().equals(s));

                    if (result)
                        userInterface.eventCallback("printLine", "Invalid combination, please try again.");
                    return result;
                }
                return false;
            }).test(hotelBrandName, hotelName));

            return new Pair<>(hotelBrandName, hotelName);
        }
    }
}
