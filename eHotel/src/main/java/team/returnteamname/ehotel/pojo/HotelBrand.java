package team.returnteamname.ehotel.pojo;


public class HotelBrand extends AbstractPojo
{
    private String hotelBrandName;
    private String mainOfficeLocation;
    private String physicalAddress;
    private String totalPhoneNumber;

    public HotelBrand()
    {}

    public HotelBrand(String hotelBrandName, String mainOfficeLocation,
                      String physicalAddress, String totalPhoneNumber)
    {
        this.hotelBrandName     = hotelBrandName;
        this.mainOfficeLocation = mainOfficeLocation;
        this.physicalAddress    = physicalAddress;
        this.totalPhoneNumber   = totalPhoneNumber;
    }

    public String getHotelBrandName()
    {
        return hotelBrandName;
    }

    public void setHotelBrandName(String hotelBrandName)
    {
        this.hotelBrandName = hotelBrandName;
    }

    public String getMainOfficeLocation()
    {
        return mainOfficeLocation;
    }

    public void setMainOfficeLocation(String mainOfficeLocation)
    {
        this.mainOfficeLocation = mainOfficeLocation;
    }

    public String getPhysicalAddress()
    {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress)
    {
        this.physicalAddress = physicalAddress;
    }

    public String getTotalPhoneNumber()
    {
        return totalPhoneNumber;
    }

    public void setTotalPhoneNumber(String totalPhoneNumber)
    {
        this.totalPhoneNumber = totalPhoneNumber;
    }
}
