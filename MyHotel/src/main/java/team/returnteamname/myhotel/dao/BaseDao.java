package team.returnteamname.myhotel.dao;

import org.jetbrains.annotations.NotNull;
import team.returnteamname.myhotel.connection.ConnectionFactory;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.util.Pair;
import team.returnteamname.myhotel.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseDao
{
    public BaseDao()
    {}

    public static ResultSet query(@NotNull String query) throws SQLException
    {
        return ConnectionFactory.getInstance().getConnection().createStatement().executeQuery(query);
    }

    public static ResultSet query(@NotNull PreparedStatement preparedStatement) throws SQLException
    {
        return preparedStatement.executeQuery();
    }

    private static Map<String, Object> getAttributeMap(AbstractPojo pojo)
        throws InvocationTargetException, IllegalAccessException
    {
        Class<?> clazz   = pojo.getClass();
        Method[] methods = clazz.getMethods();

        Map<String, Object> attributeMap = new HashMap<>();
        for (Method method : methods)
        {
            String methodName = method.getName();

            // Detect if the method is a getter of a data field of the table
            if (methodName.startsWith("get") && !methodName.startsWith("getClass"))
            {
                String fieldName   = methodName.substring(3);
                Object returnValue = method.invoke(pojo, (Object[]) null);

                attributeMap
                    .put(Util.camelCaseToUnderscoreLowerCase(fieldName), returnValue);
            }
        }

        return attributeMap;
    }

    public int insert(@NotNull AbstractPojo pojo) throws SQLException
    {
        try
        {
            String sql = "INSERT INTO <tableName>(<attributeList>) VALUES (<valuePlaceholderList>);";

            Class<?> clazz     = pojo.getClass();
            String   className = clazz.getSimpleName();

            StringBuilder        attributeNameBuilder    = new StringBuilder();
            StringBuilder        valuePlaceholderBuilder = new StringBuilder();
            Map<String, Object>  attributeMap            = getAttributeMap(pojo);
            Map<String, Integer> attributeOrderMap       = new HashMap<>();
            int                  i                       = 1;
            for (String attributeName : attributeMap.keySet())
            {
                if (attributeMap.get(attributeName) != null)
                {
                    if (attributeNameBuilder.length() != 0)
                        attributeNameBuilder.append(", ");
                    attributeNameBuilder.append(attributeName);
                    attributeOrderMap.put(attributeName, i++);

                    if (valuePlaceholderBuilder.length() != 0)
                        valuePlaceholderBuilder.append(", ");
                    valuePlaceholderBuilder.append("?");
                }
            }

            if (attributeOrderMap.size() == 0)
                return 0;

            sql = sql
                .replace("<tableName>", Util.camelCaseToUnderscoreLowerCase(className))
                .replace("<attributeList>", attributeNameBuilder.toString())
                .replace("<valuePlaceholderList>", valuePlaceholderBuilder.toString());

            Connection        connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement statement  = connection.prepareStatement(sql);
            for (String attributeName : attributeMap.keySet())
            {
                Object o = attributeMap.get(attributeName);
                if (o != null)
                    statement.setObject(attributeOrderMap.get(attributeName), o);
            }

            return statement.executeUpdate();
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int delete(@NotNull AbstractPojo matcher) throws SQLException
    {
        try
        {
            String sql = "DELETE FROM <tableName> WHERE <conditionList>;";

            Class<?> clazz     = matcher.getClass();
            String   className = clazz.getSimpleName();

            StringBuilder        conditionBuilder  = new StringBuilder();
            Map<String, Object>  attributeMap      = getAttributeMap(matcher);
            Map<String, Integer> attributeOrderMap = new HashMap<>();
            int                  i                 = 1;
            for (String attributeName : attributeMap.keySet())
            {
                if (attributeMap.get(attributeName) != null)
                {
                    if (conditionBuilder.length() != 0)
                        conditionBuilder.append(" AND ");
                    conditionBuilder.append(attributeName).append(" = ?");
                    attributeOrderMap.put(attributeName, i++);
                }
            }
            if (conditionBuilder.length() == 0)
                conditionBuilder.append("1 = 1");

            sql = sql
                .replace("<tableName>", Util.camelCaseToUnderscoreLowerCase(className))
                .replace("<conditionList>", conditionBuilder.toString());

            Connection        connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement statement  = connection.prepareStatement(sql);
            for (String attributeName : attributeMap.keySet())
            {
                Object o = attributeMap.get(attributeName);
                if (o != null)
                    statement.setObject(attributeOrderMap.get(attributeName), o);
            }

            return statement.executeUpdate();
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Pair<ArrayList<AbstractPojo>, ResultSet> select(@NotNull AbstractPojo matcher) throws SQLException
    {
        try
        {
            String sql = "SELECT * FROM <tableName> WHERE <conditionList>;";

            Class<?> clazz     = matcher.getClass();
            String   className = clazz.getSimpleName();

            StringBuilder        conditionBuilder  = new StringBuilder();
            Map<String, Object>  attributeMap      = getAttributeMap(matcher);
            Map<String, Integer> attributeOrderMap = new HashMap<>();
            int                  i                 = 1;
            for (String attributeName : attributeMap.keySet())
            {
                if (attributeMap.get(attributeName) != null)
                {
                    if (conditionBuilder.length() != 0)
                        conditionBuilder.append(" AND ");
                    conditionBuilder.append(attributeName).append(" = ?");
                    attributeOrderMap.put(attributeName, i++);
                }
            }
            if (conditionBuilder.length() == 0)
                conditionBuilder.append("1 = 1");

            sql = sql
                .replace("<tableName>", Util.camelCaseToUnderscoreLowerCase(className))
                .replace("<conditionList>", conditionBuilder.toString());

            Connection        connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement statement  = connection.prepareStatement(sql);
            for (String attributeName : attributeMap.keySet())
            {
                Object o = attributeMap.get(attributeName);
                if (o != null)
                    statement.setObject(attributeOrderMap.get(attributeName), o);
            }

            ResultSet               resultSet = statement.executeQuery();
            ArrayList<AbstractPojo> arrayList = new ArrayList<>();

            while (resultSet.next())
            {
                AbstractPojo o = (AbstractPojo) clazz.getDeclaredConstructor().newInstance();
                for (String attributeName : attributeMap.keySet())
                {
                    Field field = clazz.getDeclaredField(Util.underscoreLowerCaseToCamelCase(attributeName));
                    field.setAccessible(true);

                    field.set(o, resultSet.getObject(attributeName));
                }
                arrayList.add(o);
            }

            return new Pair<>(arrayList, resultSet);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int update(@NotNull AbstractPojo matcher, @NotNull AbstractPojo carrier) throws SQLException
    {
        try
        {
            String sql = "UPDATE <tableName> SET <newValueList> WHERE <conditionList>;";

            Class<?> carrierClass = carrier.getClass();
            Class<?> matcherClass = matcher.getClass();

            if (carrierClass != matcherClass)
                throw new IllegalArgumentException("Matcher and carrier must be of the same POJO type");

            String className = matcherClass.getSimpleName();

            StringBuilder newValueBuilder  = new StringBuilder();
            StringBuilder conditionBuilder = new StringBuilder();

            Map<String, Object>  carrierAttributeMap      = getAttributeMap(carrier);
            Map<String, Integer> carrierAttributeOrderMap = new HashMap<>();

            Map<String, Object>  matcherAttributeMap      = getAttributeMap(matcher);
            Map<String, Integer> matcherAttributeOrderMap = new HashMap<>();

            int i = 1;
            for (String attributeName : carrierAttributeMap.keySet())
            {
                if (carrierAttributeMap.get(attributeName) != null)
                {
                    if (newValueBuilder.length() != 0)
                        newValueBuilder.append(", ");
                    newValueBuilder.append(attributeName).append(" = ?");
                    carrierAttributeOrderMap.put(attributeName, i++);
                }
            }
            if (newValueBuilder.length() == 0)
                return 0;  // No need to continue

            for (String attributeName : matcherAttributeMap.keySet())
            {
                if (matcherAttributeMap.get(attributeName) != null)
                {
                    if (conditionBuilder.length() != 0)
                        conditionBuilder.append(" AND ");
                    conditionBuilder.append(attributeName).append(" = ?");
                    matcherAttributeOrderMap.put(attributeName, i++);
                }
            }
            if (conditionBuilder.length() == 0)
                conditionBuilder.append("1 = 1");

            sql = sql
                .replace("<tableName>", Util.camelCaseToUnderscoreLowerCase(className))
                .replace("<newValueList>", newValueBuilder.toString())
                .replace("<conditionList>", conditionBuilder.toString());

            Connection        connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement statement  = connection.prepareStatement(sql);
            for (String attributeName : carrierAttributeMap.keySet())
            {
                Object o = carrierAttributeMap.get(attributeName);
                if (o != null)
                    statement.setObject(carrierAttributeOrderMap.get(attributeName), o);
            }

            for (String attributeName : matcherAttributeMap.keySet())
            {
                Object o = matcherAttributeMap.get(attributeName);
                if (o != null)
                    statement.setObject(matcherAttributeOrderMap.get(attributeName), o);
            }

            return statement.executeUpdate();
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }
}
