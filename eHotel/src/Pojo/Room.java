package Pojo;

import java.math.BigDecimal;

public class Room extends AbstractPojo
{
    private String     hotelBrandName;
    private String     hotelName;
    private int        roomId;
    private BigDecimal price;
    private String     roomCapacity;
    
    public Room()
    {}
    
    public Room(String hotelBrandName, String hotelName, int roomId,
                BigDecimal price, String roomCapacity)
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

    public int getRoomId()
    {
        return roomId;
    }

    public void setRoomId(int roomId)
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
