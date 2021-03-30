package team.returnteamname.myhotelcustomer.servlet;

import team.returnteamname.myhotelcustomer.dao.BaseDao;
import team.returnteamname.myhotelcustomer.pojo.db.Book;
import team.returnteamname.myhotelcustomer.util.ServletUtil;
import team.returnteamname.myhotelcustomer.util.container.Triplet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;

public class BookServlet extends AbstractPostOnlyServlet
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

            Object loggedInAs  = request.getSession().getAttribute("loggedInAs");
            String requestBody = ServletUtil.getRequestBody(request);

            // TODO: May verify database here
            if (loggedInAs != null)
            {
                Triplet<String, String, String> identifier = ServletUtil.getRoomIdentifierFromRequest(requestBody);
                if (identifier == null
                    || identifier.getKey() == null
                    || identifier.getValue() == null
                    || identifier.getTheThird() == null)
                    reject(response, "400", "Bad request");
                else
                    if (processRoomBooking(Integer.parseInt(loggedInAs.toString()), identifier))
                        accept(response, null);
            }
            else
                reject(response, "403", "Not logged in");
        }
        catch (Exception e)
        {
            onUnhandledException(response, e);
            e.printStackTrace();
        }
    }

    private boolean processRoomBooking(Integer uid, Triplet<String, String, String> identifier)
        throws SQLException
    {
        BaseDao baseDao = new BaseDao();

        Book book = new Book();
        book.setCustomerId(uid);
        book.setHotelBrandName(identifier.getKey());
        book.setHotelName(identifier.getValue());
        book.setRoomId(Integer.parseInt(identifier.getTheThird()));
        book.setRoomType("");
        book.setCheckInDate(new Date(new java.util.Date().getTime()));

        return baseDao.insert(book) == 1;
    }
}
