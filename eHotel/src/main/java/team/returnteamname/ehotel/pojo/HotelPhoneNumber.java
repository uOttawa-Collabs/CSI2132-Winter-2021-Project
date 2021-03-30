package team.returnteamname.ehotel.pojo;

public class HotelPhoneNumber extends AbstractPojo
{
    private String hotelBrandName;
    private String hotelName;
    private String phoneNumber;

    public HotelPhoneNumber()
    {}

    public HotelPhoneNumber(String hotelBrandName, String hotelName, String phoneNumber)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.phoneNumber    = phoneNumber;
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

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
