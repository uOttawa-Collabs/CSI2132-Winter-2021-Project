package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.Nullable;

public class HotelPhoneNumber extends AbstractPojo
{
    private String hotelBrandName;
    private String hotelName;
    private String phoneNumber;

    public HotelPhoneNumber()
    {}

    public HotelPhoneNumber(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable String phoneNumber)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
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
    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(@Nullable String hotelName)
    {
        this.hotelName = hotelName;
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
