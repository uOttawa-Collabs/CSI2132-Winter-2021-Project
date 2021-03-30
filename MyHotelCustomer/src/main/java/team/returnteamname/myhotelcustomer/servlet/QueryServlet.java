package team.returnteamname.myhotelcustomer.servlet;

import org.postgresql.util.PGInterval;
import team.returnteamname.myhotelcustomer.dao.BaseDao;
import team.returnteamname.myhotelcustomer.pojo.db.Book;
import team.returnteamname.myhotelcustomer.pojo.db.HotelPhoneNumber;
import team.returnteamname.myhotelcustomer.pojo.db.Rent;
import team.returnteamname.myhotelcustomer.pojo.servlet.HotelListResponsePojo;
import team.returnteamname.myhotelcustomer.pojo.servlet.RoomListResponsePojo;
import team.returnteamname.myhotelcustomer.util.ServletUtil;
import team.returnteamname.myhotelcustomer.util.container.Pair;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

            Object loggedInAs  = request.getSession().getAttribute("loggedInAs");
            String requestBody = ServletUtil.getRequestBody(request);

            // TODO: May verify database here
            if (loggedInAs != null)
            {
                switch (ServletUtil.getRequestType(requestBody))
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
                        Pair<String, String> identifier = ServletUtil.getHotelIdentifierFromRequest(requestBody);
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

    private HotelListResponsePojo processHotelQuery()
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
            String       hotelBrandName = rsHotel.getString("hotel_brand_name");
            String       hotelName      = rsHotel.getString("hotel_name");
            String       starCategory   = String.valueOf(rsHotel.getInt("star_category"));
            String       address        = rsHotel.getString("address");
            int          numOfRooms     = rsHotel.getInt("number_of_rooms");
            List<String> phoneNumbers   = new LinkedList<>();
            String       roomsAvailable;

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

    private RoomListResponsePojo processRoomQuery(Pair<String, String> hotelIdentifier) throws SQLException
    {
        String hotelBrandName = hotelIdentifier.getKey();
        String hotelName      = hotelIdentifier.getValue();

        RoomListResponsePojo      roomListResponse = new RoomListResponsePojo();
        RoomListResponsePojo.Data roomListData;

        List<Integer> availableRoomId = new ArrayList<>();
        List<Integer> bookedRoomId    = new ArrayList<>();
        List<Integer> rentRoomId      = new ArrayList<>();

        String roomQuery = "SELECT room_id FROM room WHERE hotel_brand_name = '" + hotelBrandName
                           + "' AND hotel_name = '" + hotelName + "'";
        String bookQuery = "SELECT room_id FROM book WHERE hotel_brand_name = '" + hotelBrandName
                           + "' AND hotel_name = '" + hotelName + "'";
        String rentQuery = "SELECT room_id FROM rent WHERE hotel_brand_name = '" + hotelBrandName
                           + "' AND hotel_name = '" + hotelName + "'";

        ResultSet rsRoom = BaseDao.query(roomQuery);
        ResultSet rsBook = BaseDao.query(bookQuery);
        ResultSet rsRent = BaseDao.query(rentQuery);

        while (rsRoom.next())
        {
            Integer roomId = rsRoom.getInt("room_id");
            availableRoomId.add(roomId);
        }

        while (rsBook.next())
        {
            Integer roomId = rsBook.getInt("room_id");
            bookedRoomId.add(roomId);
        }

        while (rsRent.next())
        {
            Integer roomId = rsRent.getInt("room_id");
            rentRoomId.add(roomId);
        }

        for (Integer eachRoomId : availableRoomId)
        {
            for (Integer eachBookedRoomId : bookedRoomId)
            {
                if (eachRoomId.equals(eachBookedRoomId))
                {
                    availableRoomId.remove(eachRoomId);
                }
            }
        }

        for (Integer eachRoomId : availableRoomId)
        {
            for (Integer eachRentRoomId : rentRoomId)
            {
                if (eachRoomId.equals(eachRentRoomId))
                {
                    availableRoomId.remove(eachRoomId);
                }
            }
        }

        for (Integer eachAvailableRoomId : availableRoomId)
        {
            String availableRoom = "SELECT * FROM room WHERE hotel_brand_name = '" + hotelBrandName
                                   + "' AND hotel_name = '" + hotelName + "' AND room_id = '"
                                   + eachAvailableRoomId + "'";
            String roomAmenity = "SELECT amenity FROM room_amenity WHERE hotel_brand_name = '" + hotelBrandName
                                 + "' AND hotel_name = '" + hotelName + "' AND room_id = '"
                                 + eachAvailableRoomId + "'";
            String roomExtensibility = "SELECT extensibility FROM room_extensibility WHERE hotel_brand_name = '" + hotelBrandName
                                       + "' AND hotel_name = '" + hotelName + "' AND room_id = '"
                                       + eachAvailableRoomId + "'";
            String roomView = "SELECT view FROM room_view WHERE hotel_brand_name = '" + hotelBrandName
                              + "' AND hotel_name = '" + hotelName + "' AND room_id = '"
                              + eachAvailableRoomId + "'";
            String roomType = "SELECT room_type FROM book WHERE hotel_brand_name = '" + hotelBrandName
                              + "' AND hotel_name = '" + hotelName + "' AND room_id = '"
                              + eachAvailableRoomId + "'";

            ResultSet rsAvailableRoom     = BaseDao.query(availableRoom);
            ResultSet rsRoomAmenity       = BaseDao.query(roomAmenity);
            ResultSet rsRoomExtensibility = BaseDao.query(roomExtensibility);
            ResultSet rsRoomView          = BaseDao.query(roomView);
            ResultSet rsRoomType          = BaseDao.query(roomType);

            String roomId   = String.valueOf(eachAvailableRoomId);
            String price    = "";
            String type     = "";
            String capacity = "";

            List<String> amenityList       = new LinkedList<>();
            List<String> extensibilityList = new LinkedList<>();
            List<String> viewList          = new LinkedList<>();

            while (rsAvailableRoom.next())
            {
                BigDecimal roomPrice = rsAvailableRoom.getBigDecimal("price");
                price    = roomPrice.toString();
                capacity = rsAvailableRoom.getString("room_capacity");
            }

            while (rsRoomAmenity.next())
            {
                String eachRoomAmenity = rsRoomAmenity.getString("amenity");
                amenityList.add(eachRoomAmenity);
            }

            while (rsRoomExtensibility.next())
            {
                String eachRoomExtensibility = rsRoomExtensibility.getString("extensibility");
                extensibilityList.add(eachRoomExtensibility);
            }

            while (rsRoomView.next())
            {
                String eachRoomView = rsRoomView.getString("view");
                viewList.add(eachRoomView);
            }

            while (rsRoomType.next())
            {
                type = rsRoomType.getString("room_type");
            }

            roomListData = new RoomListResponsePojo.Data(roomId, price, type, capacity, amenityList, extensibilityList,
                                                         viewList);

            roomListResponse.getData().add(roomListData);
        }

        return roomListResponse;
    }
}
