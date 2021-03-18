package team.returnteamname.myhotel.pojo;

public class Employment
{
    private String hotelBrandName;
    private String hotelName;
    private String employeeId;

    public Employment()
    {}

    public Employment(String hotelBrandName, String hotelName, String employeeId)
    {
        this.hotelBrandName = hotelBrandName;
        this.hotelName      = hotelName;
        this.employeeId     = employeeId;
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

    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }
}
