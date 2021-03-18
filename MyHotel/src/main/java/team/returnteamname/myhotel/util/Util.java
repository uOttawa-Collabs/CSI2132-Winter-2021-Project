package team.returnteamname.myhotel.util;

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
}
