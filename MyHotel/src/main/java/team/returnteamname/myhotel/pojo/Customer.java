package team.returnteamname.myhotel.pojo;

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
        super();
    }

    public Customer(@Nullable Integer id, @Nullable String fullName, @Nullable String sinNumber,
                    @Nullable String address, @Nullable Date dateOfRegistration)
    {
        super();
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

    public void setId(@Nullable Integer id)
    {
        this.id = id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(@Nullable String fullName)
    {
        this.fullName = fullName;
    }

    public String getSinNumber()
    {
        return sinNumber;
    }

    public void setSinNumber(@Nullable String sinNumber)
    {
        this.sinNumber = sinNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(@Nullable String address)
    {
        this.address = address;
    }

    public Date getDateOfRegistration()
    {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(@Nullable Date dateOfRegistration)
    {
        this.dateOfRegistration = dateOfRegistration;
    }
}
