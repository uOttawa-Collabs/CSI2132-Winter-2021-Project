package team.returnteamname.myhotel.util;

import team.returnteamname.myhotel.ui.IUserInterface;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class Util
{
    public static String camelCaseToUnderscoreLowerCase(String string)
    {
        StringBuilder stringBuilder = new StringBuilder();
        final int     length        = string.length();

        for (int i = 0; i < length; ++i)
        {
            switch (Boolean.toString(Character.isUpperCase(string.charAt(i))))
            {
                case "true":
                    if (i != 0)
                        stringBuilder.append("_");
                    stringBuilder.append(Character.toLowerCase(string.charAt(i)));
                    break;
                case "false":
                    stringBuilder.append(string.charAt(i));
                default:
                    break;
            }
        }

        return stringBuilder.toString();
    }

    public static String underscoreLowerCaseToCamelCase(String string)
    {
        StringBuilder stringBuilder = new StringBuilder();
        final int     length        = string.length();

        for (int i = 0; i < length; ++i)
        {
            char c = string.charAt(i);
            if (c == '_' && i < length - 1)
                c = Character.toUpperCase(string.charAt(++i));

            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public static void assertString(String string, String target)
    {
        assertString(string, target, "");
    }

    public static void assertString(String string, String target, String extraMessage)
    {
        if (!string.equals(target))
            throw new AssertionError(
                "Assertion failed: \"" + string + "\", expected \"" + target + "\", message: " + extraMessage);
    }

    public static String formatResultSet(ResultSet resultSet) throws SQLException
    {
        StringBuilder stringBuilder = new StringBuilder();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int               columnCount       = resultSetMetaData.getColumnCount();

        while (resultSet.next())
        {
            for (int i = 1; i <= columnCount; ++i)
            {
                if (i > 1)
                    stringBuilder.append(" | ");
                stringBuilder.append(resultSetMetaData.getColumnName(i)).append(" = ").append(resultSet.getString(i));
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
