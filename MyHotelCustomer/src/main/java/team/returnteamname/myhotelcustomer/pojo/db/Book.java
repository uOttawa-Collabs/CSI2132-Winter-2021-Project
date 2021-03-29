package team.returnteamname.myhotelcustomer.pojo.db;

import org.jetbrains.annotations.Nullable;
import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

import java.sql.Date;

public class Book extends AbstractPojo
{
    private Integer customerId;
    private String  hotelBrandName;
    private String  hotelName;
    private Integer roomId;
    private Date    checkInDate;
    private String  roomType;
    private Integer totalNumberOccupants;

    public Book()
    {}

    public Book(@Nullable Integer customerId, @Nullable String hotelBrandName, @Nullable String hotelName,
                @Nullable Integer roomId, @Nullable Date checkInDate,
                @Nullable String roomType, @Nullable Integer totalNumberOccupants)
    {
        this.customerId           = customerId;
        this.hotelBrandName       = hotelBrandName;
        this.hotelName            = hotelName;
        this.roomId               = roomId;
        this.checkInDate          = checkInDate;
        this.roomType             = roomType;
        this.totalNumberOccupants = totalNumberOccupants;
    }

    @Nullable
    public Integer getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(@Nullable Integer customerId)
    {
        this.customerId = customerId;
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
    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(@Nullable Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    @Nullable
    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(@Nullable String roomType)
    {
        this.roomType = roomType;
    }

    @Nullable
    public Integer getTotalNumberOccupants()
    {
        return totalNumberOccupants;
    }

    public void setTotalNumberOccupants(@Nullable Integer totalNumberOccupants)
    {
        this.totalNumberOccupants = totalNumberOccupants;
    }
}
