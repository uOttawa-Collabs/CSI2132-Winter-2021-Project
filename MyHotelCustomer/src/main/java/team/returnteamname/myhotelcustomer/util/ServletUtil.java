package team.returnteamname.myhotelcustomer.util;

import com.google.gson.Gson;
import team.returnteamname.myhotelcustomer.util.container.Pair;
import team.returnteamname.myhotelcustomer.util.container.Triplet;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletUtil
{
    public static String getRequestBody(HttpServletRequest request) throws IOException
    {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public static String getRequestType(String requestBody)
    {
        try
        {
            Map<?, ?> map = new Gson().fromJson(requestBody, Map.class);
            return (String) map.get("query");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static Pair<String, String> getHotelIdentifierFromRequest(String requestBody)
    {
        try
        {
            Map<?, ?> map = new Gson().fromJson(requestBody, Map.class);

            Object content = map.get("content");
            assert content instanceof Map;
            Map<?, ?> contentMap = (Map<?, ?>) content;

            return new Pair<>((String) contentMap.get("brandName"), (String) contentMap.get("hotelName"));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Triplet<String, String, String> getRoomIdentifierFromRequest(String requestBody)
    {
        try
        {
            Map<?, ?> map = new Gson().fromJson(requestBody, Map.class);
            return new Triplet<>((String) map.get("brandName"), (String) map.get("hotelName"), (String) map.get("id"));
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
