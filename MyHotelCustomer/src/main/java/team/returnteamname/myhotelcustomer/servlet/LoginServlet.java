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

            try
            {
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
                        reject(response);
                        return;
                    }

                    arrayList = baseDao.select(customer).getKey();
                    if (arrayList.size() == 1)
                        accept(request, response, customer.getId());
                    else
                        reject(response);
                }
            }
            catch (Exception e)
            {
                onUnhandledException(response, e);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void accept(HttpServletRequest request, HttpServletResponse response, Integer id) throws IOException
    {
        Gson gson = new Gson();

        response.setStatus(200);
        response.setContentType("application/json; charset=utf-8");

        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.setCode("0");
        responsePojo.setMessage("Success");

        // TODO: Set session
        //request.getSession().setAttribute();
    }

    private void reject(HttpServletResponse response) throws IOException
    {
        Gson gson = new Gson();

        response.setStatus(200);
        response.setContentType("application/json; charset=utf-8");

        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.setCode("403");
        responsePojo.setMessage("Username or UID does not match any record");

        response.getWriter().write(gson.toJson(responsePojo));
    }
}
