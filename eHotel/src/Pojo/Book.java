package Pojo;

import java.sql.Date;

public class Book extends AbstractPojo
{
    private int 	customerId;
    private String  hotelBrandName;
    private String  hotelName;
    private int 	roomId;
    private Date    checkInDate;
    private String  roomType;
    private int 	totalNumberOccupants;

    public Book()
    {}

    public Book(int customerId,  String hotelBrandName,  String hotelName,
                int roomId,  Date checkInDate,
                String roomType,  int totalNumberOccupants)
    {
        this.customerId           = customerId;
        this.hotelBrandName       = hotelBrandName;
        this.hotelName            = hotelName;
        this.roomId               = roomId;
        this.checkInDate          = checkInDate;
        this.roomType             = roomType;
        this.totalNumberOccupants = totalNumberOccupants;
    }

    
    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
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

    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }
    
    public int getTotalNumberOccupants()
    {
        return totalNumberOccupants;
    }

    public void setTotalNumberOccupants(int totalNumberOccupants)
    {
        this.totalNumberOccupants = totalNumberOccupants;
    }
}
