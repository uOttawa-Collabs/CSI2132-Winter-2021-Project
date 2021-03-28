package team.returnteamname.myhotel.actions.cli;

import org.postgresql.util.PGInterval;
import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
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

    public static AbstractPojo getPojoInstanceFromUser(IUserInterface userInterface, Class<?> pojoClass)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return getPojoInstanceFromUser(userInterface, pojoClass, null);
    }

    public static AbstractPojo getPojoInstanceFromUser(IUserInterface userInterface, AbstractPojo shadow)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return getPojoInstanceFromUser(userInterface, null, shadow);
    }

    private static AbstractPojo getPojoInstanceFromUser(IUserInterface userInterface, Class<?> pojoClass, AbstractPojo shadow)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        AbstractPojo pojo;
        if (shadow == null)
            pojo = (AbstractPojo) pojoClass.getDeclaredConstructor().newInstance();
        else
            pojo = shadow;

        Field[]      fields = pojo.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            String   fieldName = field.getName();
            Class<?> fieldType = field.getType();

            field.setAccessible(true);

            if (field.get(pojo) != null)
                continue;

            Object instance;
            for (; ; )
            {
                try
                {
                    String hint;
                    if (fieldType.equals(Date.class))
                        hint = fieldName + "(" + fieldType
                            .getName() + ", type \"Current\" to fill in the current date)";
                    else
                        hint = fieldName + "(" + fieldType.getName() + ")";

                    instance = parseClassInstanceFromString(fieldType,
                                                            ((String) userInterface.eventCallback("readLine", hint))
                                                                .trim());
                    break;
                }
                catch (RuntimeException e)
                {
                    userInterface.eventCallback("printLine", "Invalid input, please try again.");
                    userInterface.eventCallback("printLine", "Reason: " + e);
                }
            }
            field.set(pojo, instance);
        }

        return pojo;
    }

    private static Object parseClassInstanceFromString(Class<?> clazz, String string)
    {
        try
        {
            if (clazz.equals(Integer.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return Integer.parseInt(string);
            }
            else if (clazz.equals(String.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return string;
            }
            else if (clazz.equals(Date.class))
            {
                if (string.isEmpty())
                    return null;
                else if (string.equals("Current"))
                    return new Date(new java.util.Date().getTime());
                else
                    return new Date(Long.parseLong(string));
            }
            else if (clazz.equals(BigDecimal.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return new BigDecimal(string);
            }
            else if (clazz.equals(PGInterval.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return new PGInterval(string);
            }
            else    // TODO: You may add more cases here to handle more types
            {
                throw new UnsupportedOperationException(
                    "The specified class (" + clazz.getName() + ") is currently not supported");
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
