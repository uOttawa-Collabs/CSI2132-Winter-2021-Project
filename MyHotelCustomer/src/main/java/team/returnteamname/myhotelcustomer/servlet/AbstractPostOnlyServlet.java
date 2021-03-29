package team.returnteamname.myhotelcustomer.servlet;

import com.google.gson.Gson;
import team.returnteamname.myhotelcustomer.pojo.servlet.ResponsePojo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AbstractPostOnlyServlet extends HttpServlet
{
    public static void onUnhandledException(HttpServletResponse response, Exception exception)
    {
        try
        {
            Gson gson = new Gson();

            response.setStatus(200);
            response.setContentType("application/json; charset=utf-8");

            ResponsePojo responsePojo = new ResponsePojo();
            responsePojo.setCode("500");
            responsePojo.setMessage(
                exception.getClass().getCanonicalName() + "(" + (exception.getMessage() == null ? "null" : exception
                    .getMessage()) + ")");

            response.getWriter().write(gson.toJson(responsePojo));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.sendRedirect("/");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
