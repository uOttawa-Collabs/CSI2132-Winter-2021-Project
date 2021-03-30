package team.returnteamname.myhotelcustomer.pojo.db;

import org.jetbrains.annotations.Nullable;
import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

public class HotelBrand extends AbstractPojo
{
    private String hotelBrandName;
    private String mainOfficeLocation;
    private String physicalAddress;
    private String totalPhoneNumber;

    public HotelBrand()
    {}

    public HotelBrand(@Nullable String hotelBrandName, @Nullable String mainOfficeLocation,
                      @Nullable String physicalAddress, @Nullable String totalPhoneNumber)
    {
        this.hotelBrandName     = hotelBrandName;
        this.mainOfficeLocation = mainOfficeLocation;
        this.physicalAddress    = physicalAddress;
        this.totalPhoneNumber   = totalPhoneNumber;
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
    public String getMainOfficeLocation()
    {
        return mainOfficeLocation;
    }

    public void setMainOfficeLocation(@Nullable String mainOfficeLocation)
    {
        this.mainOfficeLocation = mainOfficeLocation;
    }

    @Nullable
    public String getPhysicalAddress()
    {
        return physicalAddress;
    }

    public void setPhysicalAddress(@Nullable String physicalAddress)
    {
        this.physicalAddress = physicalAddress;
    }

    @Nullable
    public String getTotalPhoneNumber()
    {
        return totalPhoneNumber;
    }

    public void setTotalPhoneNumber(@Nullable String totalPhoneNumber)
    {
        this.totalPhoneNumber = totalPhoneNumber;
    }
}
