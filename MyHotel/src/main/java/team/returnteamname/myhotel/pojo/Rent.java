package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.Nullable;
import org.postgresql.util.PGInterval;

import java.math.BigDecimal;
import java.sql.Date;

public class Rent extends AbstractPojo
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

    public Rent(@Nullable Integer customerId, @Nullable String hotelBrandName, @Nullable String hotelName,
                @Nullable Integer roomId, @Nullable Date date, @Nullable String roomType,
                @Nullable Integer totalNumberOccupants, @Nullable BigDecimal billAmount, @Nullable PGInterval duration)
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
    public Date getDate()
    {
        return date;
    }

    public void setDate(@Nullable Date date)
    {
        this.date = date;
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

    @Nullable
    public BigDecimal getBillAmount()
    {
        return billAmount;
    }

    public void setBillAmount(@Nullable BigDecimal billAmount)
    {
        this.billAmount = billAmount;
    }

    @Nullable
    public PGInterval getDuration()
    {
        return duration;
    }

    public void setDuration(@Nullable PGInterval duration)
    {
        this.duration = duration;
    }
}
