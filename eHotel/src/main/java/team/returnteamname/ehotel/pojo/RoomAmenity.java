package team.returnteamname.ehotel.pojo;


public class RoomAmenity extends AbstractPojo
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private String  amenity;

    public RoomAmenity()
    {}

    public RoomAmenity(String hotelBrandName, String hotelName, Integer roomId,
                       String amenity)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.amenity        = amenity;
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

    public String getAmenity()
    {
        return amenity;
    }

    public void setAmenity(String amenity)
    {
        this.amenity = amenity;
    }
}
