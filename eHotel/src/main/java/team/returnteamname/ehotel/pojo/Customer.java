package team.returnteamname.ehotel.pojo;


import java.sql.Date;

public class Customer extends AbstractPojo
{
    private Integer id;
    private String  fullName;
    private String  sinNumber;
    private String  address;
    private Date    dateOfRegistration;

    public Customer()
    {
    }

    public Customer(Integer id, String fullName, String sinNumber,
                    String address, Date dateOfRegistration)
    {
        this.id                 = id;
        this.fullName           = fullName;
        this.sinNumber          = sinNumber;
        this.address            = address;
        this.dateOfRegistration = dateOfRegistration;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getSinNumber()
    {
        return sinNumber;
    }

    public void setSinNumber(String sinNumber)
    {
        this.sinNumber = sinNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getDateOfRegistration()
    {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration)
    {
        this.dateOfRegistration = dateOfRegistration;
    }
}
