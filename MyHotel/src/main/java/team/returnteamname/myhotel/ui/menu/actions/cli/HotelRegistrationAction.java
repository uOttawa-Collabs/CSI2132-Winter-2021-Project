package team.returnteamname.myhotel.ui.menu.actions.cli;

import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.actions.AbstractAction;
import team.returnteamname.myhotel.util.Pair;
import team.returnteamname.myhotel.util.Quadruplet;
import team.returnteamname.myhotel.util.Triplet;
import team.returnteamname.myhotel.pojo.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class HotelRegistrationAction extends AbstractAction
{
    @Override
    public Object run(IUserInterface userInterface)
    {
        try
        {
            BaseDao                 baseDao        = new BaseDao();
            Pair<HotelBrand, Hotel> brandHotelPair = getBrandHotelPairFromUser(userInterface, baseDao);
            if (brandHotelPair == null)
                return null;

            Set<Quadruplet<Room, Set<RoomAmenity>, Set<RoomExtensibility>, Set<RoomView>>> rooms = null;
            String                                                                         input = (String) userInterface
                .eventCallback("readLine", "Add rooms? <Y/n>");
            if (input.isEmpty() || (Character.toUpperCase(input.charAt(0)) != 'N'))
                rooms = getRoomsFromUser(userInterface, brandHotelPair.getValue());


            if (brandHotelPair.getKey() != null)
                baseDao.insert(brandHotelPair.getKey());
            baseDao.insert(brandHotelPair.getValue());
            if (rooms != null)
            {
                for (Quadruplet<Room, Set<RoomAmenity>, Set<RoomExtensibility>, Set<RoomView>> quadruplet : rooms)
                {
                    baseDao.insert(quadruplet.getKey());
                    for (RoomAmenity roomAmenity : quadruplet.getValue())
                        baseDao.insert(roomAmenity);
                    for (RoomExtensibility roomExtensibility : quadruplet.getTheThird())
                        baseDao.insert(roomExtensibility);
                    for (RoomView roomView : quadruplet.getTheFourth())
                        baseDao.insert(roomView);
                }
            }
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed, reason: " + e.getMessage());
        }
        return null;
    }

    private Pair<HotelBrand, Hotel> getBrandHotelPairFromUser(IUserInterface userInterface, BaseDao baseDao)
        throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Triplet<String, String, Boolean> newHotelIdentifier;
        if ((newHotelIdentifier = getNewHotelIdentifierFromUser(userInterface)) == null)
            return null;

        HotelBrand hotelBrand = null;
        if (!newHotelIdentifier.getTheThird())
        {
            // New brand, creating the brand
            hotelBrand = new HotelBrand();
            hotelBrand.setHotelBrandName(newHotelIdentifier.getKey());

            CommonUtil.getPojoInstanceFromUser(userInterface, hotelBrand);
        }

        Hotel hotel = new Hotel();
        hotel.setHotelBrandName(newHotelIdentifier.getKey());
        hotel.setHotelName(newHotelIdentifier.getValue());
        hotel.setNumberOfRooms(0);
        CommonUtil.getPojoInstanceFromUser(userInterface, hotel);

        return new Pair<>(hotelBrand, hotel);
    }

    private Triplet<String, String, Boolean> getNewHotelIdentifierFromUser(IUserInterface userInterface)
        throws SQLException
    {
        String                   query     = "SELECT hotel_brand_name, hotel_name FROM hotel;";
        Map<String, Set<String>> map       = new HashMap<>();
        ResultSet                resultSet = BaseDao.query(query);

        StringBuilder stringBuilder = new StringBuilder();
        while (resultSet.next())
        {
            String hotelBrandName = resultSet.getString(1);
            String hotelName      = resultSet.getString(2);

            if (!map.containsKey(hotelBrandName))
                map.put(hotelBrandName, new HashSet<>());
            map.get(hotelBrandName).add(hotelName);

            stringBuilder.append("hotelBrandName = ").append(hotelBrandName).append(" | ");
            stringBuilder.append("hotelName = ").append(hotelName).append('\n');
        }

        if (!map.isEmpty())
        {
            userInterface.eventCallback("printLine", "Existing hotel list:");
            userInterface.eventCallback("print", stringBuilder);
        }

        String hotelBrandName;
        String hotelName;
        do
        {
            hotelBrandName = (String) userInterface
                .eventCallback("readLine", "Please specify the brand of the new hotel, or type \"q\" to quit");
            hotelBrandName = hotelBrandName.trim();
            if (hotelBrandName.equals("q"))
                return null;

            hotelName = (String) userInterface
                .eventCallback("readLine", "Please specify the name of the new hotel, or type \"q\" to quit");
            hotelName = hotelName.trim();
            if (hotelName.equals("q"))
                return null;
        }
        while (((BiPredicate<String, String>) (first, second) ->
        {
            boolean result = map.containsKey(first) && map.get(first).contains(second);
            if (result)
                userInterface.eventCallback("printLine",
                                            "This combination is already taken by an existing hotel, please try again.");
            return result;
        }).test(hotelBrandName, hotelName));

        return new Triplet<>(hotelBrandName, hotelName, map.containsKey(hotelBrandName));
    }

    private Set<Quadruplet<Room, Set<RoomAmenity>, Set<RoomExtensibility>, Set<RoomView>>> getRoomsFromUser(
        IUserInterface userInterface, Hotel hotel)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Set<Quadruplet<Room, Set<RoomAmenity>, Set<RoomExtensibility>, Set<RoomView>>> set        = new HashSet<>();
        Set<Integer>                                                                   usedRoomId = new HashSet<>();

        do
        {
            Room room = new Room();
            room.setHotelBrandName(hotel.getHotelBrandName());
            room.setHotelName(hotel.getHotelName());

            Set<RoomAmenity>       roomAmenitySet       = new HashSet<>();
            Set<RoomExtensibility> roomExtensibilitySet = new HashSet<>();
            Set<RoomView>          roomViewSet          = new HashSet<>();

            do
            {
                CommonUtil.getPojoInstanceFromUser(userInterface, room);
            }
            while (((BiPredicate<Set<Integer>, Room>) (s, r) ->
            {
                boolean result = s.contains(r.getRoomId());

                if (result)
                {
                    userInterface.eventCallback("printLine", "This room ID is used, please specify another one.");
                    r.setRoomId(null);
                }
                else
                    s.add(r.getRoomId());

                return result;
            }).test(usedRoomId, room));

            for (; ; )
            {
                String input = (String) userInterface.eventCallback("readLine", "Add amenity? <Y/n>");
                if (input.isEmpty() || (Character.toUpperCase(input.charAt(0)) != 'N'))
                {
                    RoomAmenity roomAmenity = new RoomAmenity();
                    roomAmenity.setHotelBrandName(hotel.getHotelBrandName());
                    roomAmenity.setHotelName(hotel.getHotelName());
                    roomAmenity.setRoomId(room.getRoomId());

                    CommonUtil.getPojoInstanceFromUser(userInterface, roomAmenity);
                    roomAmenitySet.add(roomAmenity);
                }
                else
                    break;
            }

            for (; ; )
            {
                String input = (String) userInterface.eventCallback("readLine", "Add extensibility? <Y/n>");
                if (input.isEmpty() || (Character.toUpperCase(input.charAt(0)) != 'N'))
                {
                    RoomExtensibility roomExtensibility = new RoomExtensibility();
                    roomExtensibility.setHotelBrandName(hotel.getHotelBrandName());
                    roomExtensibility.setHotelName(hotel.getHotelName());
                    roomExtensibility.setRoomId(room.getRoomId());

                    CommonUtil.getPojoInstanceFromUser(userInterface, roomExtensibility);
                    roomExtensibilitySet.add(roomExtensibility);
                }
                else
                    break;
            }

            for (; ; )
            {
                String input = (String) userInterface.eventCallback("readLine", "Add view? <Y/n>");
                if (input.isEmpty() || (Character.toUpperCase(input.charAt(0)) != 'N'))
                {
                    RoomView roomView = new RoomView();
                    roomView.setHotelBrandName(hotel.getHotelBrandName());
                    roomView.setHotelName(hotel.getHotelName());
                    roomView.setRoomId(room.getRoomId());

                    CommonUtil.getPojoInstanceFromUser(userInterface, roomView);
                    roomViewSet.add(roomView);
                }
                else
                    break;
            }
            set.add(new Quadruplet<>(room, roomAmenitySet, roomExtensibilitySet, roomViewSet));
        }
        while (((Supplier<Boolean>) () ->
        {
            String input = (String) userInterface.eventCallback("readLine", "Add more rooms? <y/N>");
            return !input.isEmpty() && (Character.toUpperCase(input.charAt(0)) == 'Y');
        }).get());

        return set;
    }
}
