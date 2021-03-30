package team.returnteamname.ehotel.pojo;


public class RoomView extends AbstractPojo
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private String  view;

    public RoomView()
    {}

    public RoomView(String hotelBrandName, String hotelName, Integer roomId,
                    String view)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.view           = view;
    }

    public String getHotelBrandName()
    {
        return hotelBrandName;
    }

    public void setHotelBrandName(String hotelBrandName)
    {
        this.hotelBrandName = hotelBrandName;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public Integer getRoomId()
    {
        return roomId;
    }

    public void setRoomId(Integer roomId)
    {
        this.roomId = roomId;
    }
    
    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }
}
