package team.returnteamname.myhotelcustomer.pojo;

import org.jetbrains.annotations.Nullable;

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

    public Customer(@Nullable Integer id, @Nullable String fullName, @Nullable String sinNumber,
                    @Nullable String address, @Nullable Date dateOfRegistration)
    {
        this.id                 = id;
        this.fullName           = fullName;
        this.sinNumber          = sinNumber;
        this.address            = address;
        this.dateOfRegistration = dateOfRegistration;
    }

    @Nullable
    public Integer getId()
    {
        return id;
    }

    public void setId(@Nullable Integer id)
    {
        this.id = id;
    }

    @Nullable
    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(@Nullable String fullName)
    {
        this.fullName = fullName;
    }

    @Nullable
    public String getSinNumber()
    {
        return sinNumber;
    }

    public void setSinNumber(@Nullable String sinNumber)
    {
        this.sinNumber = sinNumber;
    }

    @Nullable
    public String getAddress()
    {
        return address;
    }

    public void setAddress(@Nullable String address)
    {
        this.address = address;
    }

    @Nullable
    public Date getDateOfRegistration()
    {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(@Nullable Date dateOfRegistration)
    {
        this.dateOfRegistration = dateOfRegistration;
    }
}
