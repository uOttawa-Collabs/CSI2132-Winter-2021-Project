package team.returnteamname.myhotelcustomer.pojo;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class Room extends AbstractPojo
{
    private String     hotelBrandName;
    private String     hotelName;
    private Integer    roomId;
    private BigDecimal price;
    private String     roomCapacity;

    public Room()
    {}

    public Room(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable Integer roomId,
                @Nullable BigDecimal price, @Nullable String roomCapacity)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.price          = price;
        this.roomCapacity   = roomCapacity;
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
    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(@Nullable BigDecimal price)
    {
        this.price = price;
    }

    @Nullable
    public String getRoomCapacity()
    {
        return roomCapacity;
    }

    public void setRoomCapacity(@Nullable String roomCapacity)
    {
        this.roomCapacity = roomCapacity;
    }
}
