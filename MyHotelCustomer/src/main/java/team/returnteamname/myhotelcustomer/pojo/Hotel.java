package team.returnteamname.myhotelcustomer.pojo;

import org.jetbrains.annotations.Nullable;

public class Hotel extends AbstractPojo
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer starCategory;
    private Integer numberOfRooms;
    private String  address;
    private String  emailAddress;

    public Hotel()
    {}

    public Hotel(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable Integer starCategory,
                 @Nullable Integer numberOfRooms, @Nullable String address, @Nullable String emailAddress)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.starCategory   = starCategory;
        this.numberOfRooms  = numberOfRooms;
        this.address        = address;
        this.emailAddress   = emailAddress;
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
    public Integer getStarCategory()
    {
        return starCategory;
    }

    public void setStarCategory(@Nullable Integer starCategory)
    {
        this.starCategory = starCategory;
    }

    @Nullable
    public Integer getNumberOfRooms()
    {
        return numberOfRooms;
    }

    public void setNumberOfRooms(@Nullable Integer numberOfRooms)
    {
        this.numberOfRooms = numberOfRooms;
    }

    @Nullable
    public String getAddress()
    {
        return address;
    }

    public void setAddress(@Nullable String address)
    {
        this.address = address;
    }

    @Nullable
    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(@Nullable String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
