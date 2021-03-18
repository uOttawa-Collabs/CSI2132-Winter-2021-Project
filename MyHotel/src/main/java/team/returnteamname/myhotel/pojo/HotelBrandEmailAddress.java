package team.returnteamname.myhotel.pojo;

public class HotelBrandEmailAddress
{
    private String hotelBrandName;
    private String emailAddress;

    public HotelBrandEmailAddress()
    {}

    public HotelBrandEmailAddress(String hotelBrandName, String emailAddress)
    {
        this.hotelBrandName = hotelBrandName;
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

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
