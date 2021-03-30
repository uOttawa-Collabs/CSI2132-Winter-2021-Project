package Pojo;

public class Employment extends AbstractPojo
{
    private String hotelBrandName;
    private String hotelName;
    private int employeeId;

    public Employment()
    {}

    public Employment(String hotelBrandName, String hotelName, int employeeId)
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

    public int getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(int employeeId)
    {
        this.employeeId = employeeId;
    }
}
