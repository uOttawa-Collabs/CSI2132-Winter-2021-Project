package team.returnteamname.myhotel.ui.menu.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.*;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.actions.AbstractAction;
import team.returnteamname.myhotel.util.Pair;

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

        // Hotel brand
        HotelBrand matcherHotelBrand = new HotelBrand();
        matcherHotelBrand.setHotelBrandName(hotelBrandName);
        HotelBrandEmailAddress matcherHotelBrandEmailAddress = new HotelBrandEmailAddress();
        matcherHotelBrandEmailAddress.setHotelBrandName(hotelBrandName);
        HotelBrandPhoneNumber matcherHotelBrandPhoneNumber = new HotelBrandPhoneNumber();
        matcherHotelBrandPhoneNumber.setHotelBrandName(hotelBrandName);

        HotelBrand              hotelBrand             = (HotelBrand) baseDao.select(matcherHotelBrand).getKey().get(0);
        ArrayList<AbstractPojo> hotelBrandEmailAddress = baseDao.select(matcherHotelBrandEmailAddress).getKey();
        ArrayList<AbstractPojo> hotelBrandPhoneNumber  = baseDao.select(matcherHotelBrandPhoneNumber).getKey();

        // Hotel
        Hotel matcherHotel = new Hotel();
        matcherHotel.setHotelBrandName(hotelBrandName);
        matcherHotel.setHotelName(hotelName);
        HotelPhoneNumber matcherHotelPhoneNumber = new HotelPhoneNumber();
        matcherHotelPhoneNumber.setHotelBrandName(hotelBrandName);
        matcherHotelPhoneNumber.setHotelName(hotelName);

        Hotel                   hotel            = (Hotel) baseDao.select(matcherHotel).getKey().get(0);
        ArrayList<AbstractPojo> hotelPhoneNumber = baseDao.select(matcherHotelPhoneNumber).getKey();

        // Room
        Room matcherRoom = new Room();
        matcherRoom.setHotelBrandName(hotelBrandName);
        matcherRoom.setHotelName(hotelName);

        RoomAmenity matcherRoomAmenity = new RoomAmenity();
        matcherRoomAmenity.setHotelBrandName(hotelBrandName);
        matcherRoomAmenity.setHotelName(hotelName);

        RoomExtensibility matcherRoomExtensibility = new RoomExtensibility();
        matcherRoomExtensibility.setHotelBrandName(hotelBrandName);
        matcherRoomExtensibility.setHotelName(hotelName);

        RoomView matcherRoomView = new RoomView();
        matcherRoomView.setHotelBrandName(hotelBrandName);
        matcherRoomView.setHotelName(hotelName);

        ArrayList<AbstractPojo>   room              = baseDao.select(matcherRoom).getKey();
        Map<Integer, Set<String>> roomAmenity       = new HashMap<>();
        Map<Integer, Set<String>> roomExtensibility = new HashMap<>();
        Map<Integer, Set<String>> roomView          = new HashMap<>();

        for (AbstractPojo pojo : room)
        {
            Room                    r      = (Room) pojo;
            Integer                 roomId = r.getRoomId();
            ArrayList<AbstractPojo> arrayList;

            roomAmenity.computeIfAbsent(roomId, k -> new HashSet<>());
            matcherRoomAmenity.setRoomId(roomId);
            arrayList = baseDao.select(matcherRoomAmenity).getKey();
            for (AbstractPojo p : arrayList)
                roomAmenity.get(roomId).add(((RoomAmenity) p).getAmenity());

            roomExtensibility.computeIfAbsent(roomId, k -> new HashSet<>());
            matcherRoomExtensibility.setRoomId(roomId);
            arrayList = baseDao.select(matcherRoomExtensibility).getKey();
            for (AbstractPojo p : arrayList)
                roomExtensibility.get(roomId).add(((RoomExtensibility) p).getExtensibility());

            roomView.computeIfAbsent(roomId, k -> new HashSet<>());
            matcherRoomView.setRoomId(roomId);
            arrayList = baseDao.select(matcherRoomView).getKey();
            for (AbstractPojo p : arrayList)
                roomView.get(roomId).add(((RoomView) p).getView());
        }

        // Printing
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Brand: ").append(hotelBrandName).append('\n');
        stringBuilder.append("\tMain office location: ").append(hotelBrand.getMainOfficeLocation()).append('\n');
        stringBuilder.append("\tAddress: ").append(hotelBrand.getPhysicalAddress()).append('\n');

        stringBuilder.append("\tEmail address(es):");
        for (AbstractPojo pojo : hotelBrandEmailAddress)
            stringBuilder.append(' ').append(((HotelBrandEmailAddress) pojo).getEmailAddress());
        stringBuilder.append('\n');

        stringBuilder.append("\tMain phone number: ").append(hotelBrand.getTotalPhoneNumber()).append('\n');

        stringBuilder.append("\tPhone number(s):");
        for (AbstractPojo pojo : hotelBrandPhoneNumber)
            stringBuilder.append(' ').append(((HotelBrandPhoneNumber) pojo).getPhoneNumber());
        stringBuilder.append("\n\n");


        stringBuilder.append("Hotel: ").append(hotelName).append('\n');
        stringBuilder.append("\tStar category: ").append(hotel.getStarCategory()).append(" star(s)\n");
        stringBuilder.append("\tNumber of rooms: ").append(hotel.getNumberOfRooms()).append('\n');
        stringBuilder.append("\tAddress: ").append(hotel.getAddress()).append('\n');
        stringBuilder.append("\tEmail address: ").append(hotel.getEmailAddress()).append('\n');
        stringBuilder.append("\tPhone number(s):");

        for (AbstractPojo pojo : hotelPhoneNumber)
            stringBuilder.append(' ').append(((HotelPhoneNumber) pojo).getPhoneNumber());
        stringBuilder.append("\n\n");


        stringBuilder.append("Rooms:\n");
        for (AbstractPojo pojo : room)
        {
            Room r = (Room) pojo;
            stringBuilder.append("\tID: ").append(r.getRoomId()).append('\n');
            stringBuilder.append("\tPrice: $").append(r.getPrice()).append('\n');

            stringBuilder.append("\tAmenity(-ies):");
            for (String amenity : roomAmenity.get(r.getRoomId()))
                stringBuilder.append(" +").append(amenity);
            stringBuilder.append('\n');

            stringBuilder.append("\tCapacity: ").append(r.getRoomCapacity()).append('\n');

            stringBuilder.append("\tView(s):");
            for (String view : roomView.get(r.getRoomId()))
                stringBuilder.append(" +").append(view);
            stringBuilder.append('\n');

            stringBuilder.append("\tExtensibility(-ies):");
            for (String extensibility : roomExtensibility.get(r.getRoomId()))
                stringBuilder.append(" +").append(extensibility);
            stringBuilder.append("\n\n");
        }

        userInterface.eventCallback("print", stringBuilder);
    }
}
