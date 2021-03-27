package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

public class CommonUtil
{
    public static Pair<String, String> getHotelFromUser(IUserInterface userInterface) throws SQLException
    {
        String                   query     = "SELECT hotel_brand_name, hotel_name FROM hotel;";
        Map<String, Set<String>> map       = new HashMap<>();
        ResultSet                resultSet = BaseDao.query(query);

        StringBuilder stringBuilder = new StringBuilder();
        while (resultSet.next())
        {
            String hotelBrandName = resultSet.getString(1);
            String hotelName      = resultSet.getString(2);

            if (!map.containsKey(hotelBrandName))
                map.put(hotelBrandName, new HashSet<>());
            map.get(hotelBrandName).add(hotelName);

            stringBuilder.append("hotelBrandName = ").append(hotelBrandName).append(" | ");
            stringBuilder.append("hotelName = ").append(hotelName).append('\n');
        }

        if (map.isEmpty())
            throw new IllegalStateException("no available hotel found");
        else
        {
            userInterface.eventCallback("printLine", "Hotel list:");
            userInterface.eventCallback("print", stringBuilder);

            String hotelBrandName;
            String hotelName;
            do
            {
                hotelBrandName = (String) userInterface
                    .eventCallback("readLine", "Please enter the brand name of the hotel, or type \"q\" to quit");
                hotelBrandName = hotelBrandName.trim();
                if (hotelBrandName.equals("q"))
                    return null;

                hotelName = (String) userInterface
                    .eventCallback("readLine", "Please enter the name of the hotel, or type \"q\" to quit");
                hotelName = hotelName.trim();
                if (hotelName.equals("q"))
                    return null;
            }
            while (((BiPredicate<String, String>) (first, second) ->
            {
                boolean result = !(map.containsKey(first) && map.get(first).contains(second));
                if (result)
                    userInterface.eventCallback("printLine", "Invalid combination, please try again.");
                return result;
            }).test(hotelBrandName, hotelName));

            return new Pair<>(hotelBrandName, hotelName);
        }
    }
}
