package team.returnteamname.myhotel.pojo;

public class RoomExtensibility
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private String  extensibility;

    public RoomExtensibility()
    {}

    public RoomExtensibility(String hotelBrandName, String hotelName, Integer roomId, String extensibility)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.extensibility  = extensibility;
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

    public String getExtensibility()
    {
        return extensibility;
    }

    public void setExtensibility(String extensibility)
    {
        this.extensibility = extensibility;
    }
}
