package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.Nullable;

public class RoomView extends AbstractPojo
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private String  view;

    public RoomView()
    {}

    public RoomView(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable Integer roomId,
                    @Nullable String view)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.view           = view;
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
    public String getView()
    {
        return view;
    }

    public void setView(@Nullable String view)
    {
        this.view = view;
    }
}
