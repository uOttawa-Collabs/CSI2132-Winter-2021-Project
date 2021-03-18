package team.returnteamname.myhotel.pojo;

import org.postgresql.util.PGInterval;

import java.math.BigDecimal;
import java.sql.Date;

public class Rent
{
    private Integer    customerId;
    private String     hotelBrandName;
    private String     hotelName;
    private Integer    roomId;
    private Date       date;
    private String     roomType;
    private Integer    totalNumberOccupants;
    private BigDecimal billAmount;
    private PGInterval duration;

    public Rent()
    {}

    public Rent(Integer customerId, String hotelBrandName, String hotelName, Integer roomId, Date date,
                String roomType, Integer totalNumberOccupants, BigDecimal billAmount,
                PGInterval duration)
    {
        this.customerId           = customerId;
        this.hotelBrandName       = hotelBrandName;
        this.hotelName            = hotelName;
        this.roomId               = roomId;
        this.date                 = date;
        this.roomType             = roomType;
        this.totalNumberOccupants = totalNumberOccupants;
        this.billAmount           = billAmount;
        this.duration             = duration;
    }

    public Integer getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Integer customerId)
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

    public Integer getRoomId()
    {
        return roomId;
    }

    public void setRoomId(Integer roomId)
    {
        this.roomId = roomId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public Integer getTotalNumberOccupants()
    {
        return totalNumberOccupants;
    }

    public void setTotalNumberOccupants(Integer totalNumberOccupants)
    {
        this.totalNumberOccupants = totalNumberOccupants;
    }

    public BigDecimal getBillAmount()
    {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount)
    {
        this.billAmount = billAmount;
    }

    public PGInterval getDuration()
    {
        return duration;
    }

    public void setDuration(PGInterval duration)
    {
        this.duration = duration;
    }
}
