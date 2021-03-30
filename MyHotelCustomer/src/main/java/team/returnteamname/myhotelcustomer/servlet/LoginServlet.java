package team.returnteamname.myhotelcustomer.servlet;

import com.google.gson.Gson;
import team.returnteamname.myhotelcustomer.dao.BaseDao;
import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;
import team.returnteamname.myhotelcustomer.pojo.db.Customer;
import team.returnteamname.myhotelcustomer.pojo.servlet.AuthenticationPojo;
import team.returnteamname.myhotelcustomer.pojo.servlet.ResponsePojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginServlet extends AbstractPostOnlyServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            try
            {
                if (!(request.getHeader("Content-Type").contains("application/json")))
                {
                    throw new IllegalArgumentException();
                }
            }
            catch (NullPointerException | IllegalArgumentException e)
            {
                response.sendRedirect("/");
                return;
            }

            Gson gson = new Gson();
            BufferedReader bufferedReader = request.getReader();

            BaseDao baseDao = new BaseDao();
            AuthenticationPojo authenticationPojo = gson.fromJson(bufferedReader, AuthenticationPojo.class);

            Customer customer = new Customer();
            customer.setFullName(authenticationPojo.getUsername());
            ArrayList<AbstractPojo> arrayList = baseDao.select(customer).getKey();
            if (arrayList.size() == 1)
            {
                customer = (Customer) arrayList.get(0);
                accept(request, response, customer.getId());
            }
            else
            {
                try
                {
                    customer.setFullName(null);
                    customer.setId(Integer.parseInt(authenticationPojo.getUsername()));
                }
                catch (NumberFormatException e)
                {
                    reject(response, "403", "Username does not match any record");
                    return;
                }

                arrayList = baseDao.select(customer).getKey();
                if (arrayList.size() == 1)
                    accept(request, response, customer.getId());
                else
                    reject(response, "403", "UID does not match any record");
            }

        }
        catch (Exception e)
        {
            onUnhandledException(response, e);
            e.printStackTrace();
        }
    }

    private void accept(HttpServletRequest request, HttpServletResponse response, Integer id) throws IOException
    {
        request.getSession().setAttribute("loggedInAs", id);
        request.getSession().setMaxInactiveInterval(30 * 60);

        super.accept(response, null);
    }
}
