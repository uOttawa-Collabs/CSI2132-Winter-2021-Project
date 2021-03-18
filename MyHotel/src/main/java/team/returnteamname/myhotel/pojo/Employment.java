package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.Nullable;

public class Employment extends AbstractPojo
{
    private String hotelBrandName;
    private String hotelName;
    private String employeeId;

    public Employment()
    {}

    public Employment(@Nullable String hotelBrandName, @Nullable String hotelName, @Nullable String employeeId)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.employeeId     = employeeId;
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
    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(@Nullable String employeeId)
    {
        this.employeeId = employeeId;
    }
}
