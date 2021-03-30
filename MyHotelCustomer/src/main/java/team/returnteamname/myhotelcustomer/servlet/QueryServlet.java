package team.returnteamname.myhotelcustomer.servlet;

import com.google.gson.Gson;
import org.postgresql.util.PGInterval;
import team.returnteamname.myhotelcustomer.dao.BaseDao;
import team.returnteamname.myhotelcustomer.pojo.db.Book;
import team.returnteamname.myhotelcustomer.pojo.db.HotelPhoneNumber;
import team.returnteamname.myhotelcustomer.pojo.db.Rent;
import team.returnteamname.myhotelcustomer.pojo.servlet.HotelListResponsePojo;
import team.returnteamname.myhotelcustomer.pojo.servlet.RoomListResponsePojo;
import team.returnteamname.myhotelcustomer.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QueryServlet extends AbstractPostOnlyServlet
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

            Object loggedInAs = request.getSession().getAttribute("loggedInAs");
            // TODO: May verify database here
            if (loggedInAs != null)
            {
                switch (getRequestType(request))
                {
                    case "hotel":
                    {
                        HotelListResponsePojo responsePojo = processHotelQuery();
                        if (responsePojo != null)
                            accept(response, responsePojo);
                        else
                            reject(response, "500", "Exceptional result");
                        break;
                    }
                    case "room":
                    {
                        Pair<String, String> identifier = getHotelIdentifierFromRequest(request);
                        if (identifier == null || identifier.getKey() == null || identifier.getValue() == null)
                            reject(response, "400", "Bad request");
                        else
                        {
                            RoomListResponsePojo responsePojo = processRoomQuery(identifier);
                            if (responsePojo != null)
                                accept(response, responsePojo);
                            else
                                reject(response, "500", "No such hotel");
                        }
                        break;
                    }
                    default:
                        reject(response, "400", "Bad request");
                        break;
                }
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

    public String getRequestType(HttpServletRequest request)
    {
        try
        {
            Map<?, ?> map = new Gson().fromJson(request.getReader(), Map.class);
            return (String) map.get("query");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public Pair<String, String> getHotelIdentifierFromRequest(HttpServletRequest request)
    {
        return null;
    }

    public HotelListResponsePojo processHotelQuery()
        throws SQLException
    {
        HotelListResponsePojo      hotelListResponse = new HotelListResponsePojo();
        HotelListResponsePojo.Data hotelListData;
        HotelPhoneNumber           hotelPhoneNumber;
        Book                       bookedRoom;
        Rent                       rentRoom;

        List<HotelPhoneNumber> phoneNumberList = new ArrayList<>();
        List<Book>             bookedRooms     = new ArrayList<>();
        List<Rent>             rentRooms       = new ArrayList<>();

        String hotelQuery      = "SELECT * FROM hotel";
        String hotelPhoneQuery = "SELECT * FROM hotel_phone_number";
        String bookQuery       = "SELECT * FROM book";
        String rentQuery       = "SELECT * FROM rent";

        ResultSet rsHotel      = BaseDao.query(hotelQuery);
        ResultSet rsHotelPhone = BaseDao.query(hotelPhoneQuery);
        ResultSet rsBook       = BaseDao.query(bookQuery);
        ResultSet rsRent       = BaseDao.query(rentQuery);

        while (rsHotelPhone.next())
        {
            hotelPhoneNumber = new HotelPhoneNumber(rsHotelPhone.getString("hotel_brand_name"),
                                                    rsHotelPhone.getString("hotel_name"),
                                                    rsHotelPhone.getString("phone_number"));
            phoneNumberList.add(hotelPhoneNumber);
        }

        while (rsBook.next())
        {
            bookedRoom = new Book(rsBook.getInt("customer_id"), rsBook.getString("hotel_brand_name"),
                                  rsBook.getString("hotel_name"), rsBook.getInt("room_id"),
                                  rsBook.getDate("check_in_date"), rsBook.getString("room_type"),
                                  rsBook.getInt("total_number_occupants"));
            bookedRooms.add(bookedRoom);
        }

        while (rsRent.next())
        {
            rentRoom = new Rent(rsRent.getInt("customer_id"), rsRent.getString("hotel_brand_name"),
                                rsRent.getString("hotel_name"), rsRent.getInt("room_id"),
                                rsRent.getDate("check_in_date"), rsRent.getString("room_type"),
                                rsRent.getInt("total_number_occupants"), rsRent.getInt("check_in_employee_id"),
                                rsRent.getBigDecimal("bill_amount"), (PGInterval) rsRent.getObject("duration"));
            rentRooms.add(rentRoom);
        }

        while (rsHotel.next())
        {
            String hotelBrandName = rsHotel.getString("hotel_brand_name");
            String hotelName      = rsHotel.getString("hotel_name");
            String starCategory   = String.valueOf(rsHotel.getInt("star_category"));
            String address        = rsHotel.getString("address");
            int    numOfRooms     = rsHotel.getInt("number_of_rooms");
            List<String> phoneNumbers    = new LinkedList<>();
            String roomsAvailable;

            for (HotelPhoneNumber eachHotel : phoneNumberList)
            {
                String eachHotelName = eachHotel.getHotelName();

                assert eachHotelName != null;
                if (eachHotelName.equals(hotelName))
                {
                    phoneNumbers.add(eachHotel.getPhoneNumber());
                }
            }

            for (Book eachBookRoom : bookedRooms)
            {
                String eachHotelName = eachBookRoom.getHotelName();

                assert eachHotelName != null;
                if (eachHotelName.equals(hotelName))
                {
                    numOfRooms--;
                }
            }

            for (Rent eachRentRoom : rentRooms)
            {
                String eachHotelName = eachRentRoom.getHotelName();

                assert eachHotelName != null;
                if (eachHotelName.equals(hotelName))
                {
                    numOfRooms--;
                }
            }

            roomsAvailable = String.valueOf(numOfRooms);

            hotelListData = new HotelListResponsePojo.Data(hotelBrandName, hotelName, starCategory, address,
                                                           phoneNumbers, roomsAvailable);
            hotelListResponse.getData().add(hotelListData);
        }

        return hotelListResponse;
    }

    public RoomListResponsePojo processRoomQuery(Pair<String, String> hotelIdentifier)
    {
        return null;
    }
}
