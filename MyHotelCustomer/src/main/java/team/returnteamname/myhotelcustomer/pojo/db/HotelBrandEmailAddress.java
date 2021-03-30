package team.returnteamname.myhotelcustomer.pojo.db;

import org.jetbrains.annotations.Nullable;
import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

public class HotelBrandEmailAddress extends AbstractPojo
{
    private String hotelBrandName;
    private String emailAddress;

    public HotelBrandEmailAddress()
    {}

    public HotelBrandEmailAddress(@Nullable String hotelBrandName, @Nullable String emailAddress)
    {
        this.hotelBrandName = hotelBrandName;
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
    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(@Nullable String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
