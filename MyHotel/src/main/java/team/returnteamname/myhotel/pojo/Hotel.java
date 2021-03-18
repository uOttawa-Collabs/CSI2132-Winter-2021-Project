package team.returnteamname.myhotel.pojo;

public class Hotel
{
    private String  hotelBrandName;
    private String  hotelName;
    private Integer starCategory;
    private Integer numberOfRooms;
    private String  address;
    private String  emailAddress;

    public Hotel()
    {}

    public Hotel(String hotelBrandName, String hotelName, Integer starCategory, Integer numberOfRooms,
                 String address, String emailAddress)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.starCategory   = starCategory;
        this.numberOfRooms  = numberOfRooms;
        this.address        = address;
        this.emailAddress   = emailAddress;
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

    public Integer getStarCategory()
    {
        return starCategory;
    }

    public void setStarCategory(Integer starCategory)
    {
        this.starCategory = starCategory;
    }

    public Integer getNumberOfRooms()
    {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms)
    {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
