package team.returnteamname.myhotel.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.*;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;
import team.returnteamname.myhotel.util.Pair;
import team.returnteamname.myhotel.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HotelInformationAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        try
        {
            Pair<String, String> hotel = CommonUtil.getHotelFromUser(userInterface);

            if (hotel == null)
                return null;

            printAbstractOfHotel(userInterface, hotel.getKey(), hotel.getValue());
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed, reason: " + e.getMessage());
        }
        return null;
    }

    private void printAbstractOfHotel(IUserInterface userInterface, String hotelBrandName, String hotelName)
        throws SQLException
    {
        BaseDao baseDao = new BaseDao();

        HotelBrand matcherHotelBrand = new HotelBrand();
        matcherHotelBrand.setHotelBrandName(hotelBrandName);
        HotelBrandEmailAddress matcherHotelBrandEmailAddress = new HotelBrandEmailAddress();
        matcherHotelBrandEmailAddress.setHotelBrandName(hotelBrandName);
        HotelBrandPhoneNumber matcherHotelBrandPhoneNumber = new HotelBrandPhoneNumber();
        matcherHotelBrandPhoneNumber.setHotelBrandName(hotelBrandName);

        // Hotel brand
        // Primary key, unique result
        HotelBrand hotelBrand = (HotelBrand) baseDao.select(matcherHotelBrand).getKey().get(0);
        ArrayList<AbstractPojo> hotelBrandEmailAddress = baseDao.select(matcherHotelBrandEmailAddress).getKey();
        ArrayList<AbstractPojo> hotelBrandPhoneNumber = baseDao.select(matcherHotelBrandPhoneNumber).getKey();

        // Hotel
        Hotel matcherHotel = new Hotel();
        matcherHotel.setHotelBrandName(hotelBrandName);
        matcherHotel.setHotelName(hotelName);
        HotelPhoneNumber matcherHotelPhoneNumber = new HotelPhoneNumber();
        matcherHotelPhoneNumber.setHotelBrandName(hotelBrandName);
        matcherHotelPhoneNumber.setHotelName(hotelName);

        Hotel hotel = (Hotel) baseDao.select(matcherHotel).getKey().get(0);
        ArrayList<AbstractPojo> hotelPhoneNumber = baseDao.select(matcherHotelPhoneNumber).getKey();

        // Room
        Room matcherRoom = new Room();
        matcherRoom.setHotelBrandName(hotelBrandName);
        matcherRoom.setHotelName(hotelName);

        RoomAmenity matcherRoomAmenity = new RoomAmenity();
        matcherRoomAmenity.setHotelBrandName(hotelBrandName);
        matcherRoomAmenity.setHotelName(hotelName);

        ArrayList<AbstractPojo> room = baseDao.select(matcherRoom).getKey();
        Map<String, Set<String>> roomAmenity = new HashMap<>();


    }
}
