package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.Nullable;

public class RoomExtensibility extends AbstractPojo
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private String  extensibility;

    public RoomExtensibility()
    {}

    public RoomExtensibility(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable Integer roomId,
                             @Nullable String extensibility)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.extensibility  = extensibility;
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
    public String getExtensibility()
    {
        return extensibility;
    }

    public void setExtensibility(@Nullable String extensibility)
    {
        this.extensibility = extensibility;
    }
}
