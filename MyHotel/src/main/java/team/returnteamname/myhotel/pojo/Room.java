package team.returnteamname.myhotel.pojo;

import java.math.BigDecimal;

public class Room
{
    private String hotelBrandName;
    private String     hotelName;
    private Integer    roomId;
    private BigDecimal price;
    private String     roomCapacity;

    public Room(String hotelBrandName, String hotelName, Integer roomId, BigDecimal price, String roomCapacity)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.roomId         = roomId;
        this.price          = price;
        this.roomCapacity   = roomCapacity;
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

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getRoomCapacity()
    {
        return roomCapacity;
    }

    public void setRoomCapacity(String roomCapacity)
    {
        this.roomCapacity = roomCapacity;
    }
}
