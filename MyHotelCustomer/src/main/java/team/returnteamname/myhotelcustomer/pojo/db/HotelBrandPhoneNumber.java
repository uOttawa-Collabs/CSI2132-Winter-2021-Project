package team.returnteamname.myhotelcustomer.pojo.db;

import org.jetbrains.annotations.Nullable;
import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

public class HotelBrandPhoneNumber extends AbstractPojo
{
    private String hotelBrandName;
    private String phoneNumber;

    public HotelBrandPhoneNumber()
    {}

    public HotelBrandPhoneNumber(@Nullable String hotelBrandName, @Nullable String phoneNumber)
    {
        this.hotelBrandName = hotelBrandName;
        this.phoneNumber    = phoneNumber;
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
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
