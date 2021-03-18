package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.Nullable;

public class RoomAmenity extends AbstractPojo
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private String  amenity;

    public RoomAmenity()
    {}

    public RoomAmenity(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable Integer roomId,
                       @Nullable String amenity)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.amenity        = amenity;
    }

    @Nullable
    public String getHotelBrandName()
    {
        return hotelBrandName;
    }

    public void setHotelBrandName(@Nullable String hotelBrandName)
    {
        this.hotelBrandName = hotelBrandName;
    }

    @Nullable
    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(@Nullable String hotelName)
    {
        this.hotelName = hotelName;
    }

    @Nullable
    public Integer getRoomId()
    {
        return roomId;
    }

    public void setRoomId(@Nullable Integer roomId)
    {
        this.roomId = roomId;
    }

    @Nullable
    public String getAmenity()
    {
        return amenity;
    }

    public void setAmenity(@Nullable String amenity)
    {
        this.amenity = amenity;
    }
}
