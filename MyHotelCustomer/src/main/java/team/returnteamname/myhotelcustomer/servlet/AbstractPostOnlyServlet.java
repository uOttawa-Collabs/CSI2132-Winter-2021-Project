package team.returnteamname.myhotelcustomer.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AbstractPostOnlyServlet extends HttpServlet
{
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
