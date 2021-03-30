package Pojo;

import java.math.BigDecimal;
import java.sql.Date;

import org.postgresql.util.PGInterval;

public class RentHistory extends AbstractPojo{
	private int    	   customerId;
    private String     hotelBrandName;
    private String     hotelName;
    private int    	   roomId;
    private int 	   checkInEmployeeId;
    private Date       checkInDate;
    private String     roomType;
    private int    	   totalNumberOccupants;
    private BigDecimal billAmount;
    private PGInterval duration;
    private int 	   checkOutEmployeeId;
    private Date       checkOutDate;
    
    public RentHistory()
    {}

    public RentHistory(int customerId, String hotelBrandName, String hotelName,
                	   int roomId, int checkInEmployeeId, Date checkInDate, 
                	   String roomType, int totalNumberOccupants, BigDecimal billAmount, 
                	   PGInterval duration, int checkOutEmployeeId, Date checkOutDate)
    {
        this.customerId           = customerId;
        this.hotelBrandName       = hotelBrandName;
        this.hotelName            = hotelName;
        this.roomId               = roomId;
        this.checkInEmployeeId	  = checkInEmployeeId;
        this.checkInDate          = checkInDate;
        this.roomType             = roomType;
        this.totalNumberOccupants = totalNumberOccupants;
        this.billAmount           = billAmount;
        this.duration             = duration;
        this.checkOutEmployeeId   = checkOutEmployeeId;
        this.checkOutDate		  = checkOutDate;
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
    
    public int getCheckInEmployeeId()
    {
    	return checkInEmployeeId;
    }
    
    public void setCheckInRmployeeId(int checkInEmployeeId)
    {
    	this.checkInEmployeeId = checkInEmployeeId;
    }

    public Date getDate()
    {
        return checkInDate;
    }

    public void setDate(Date checkInDate)
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
