package team.returnteamname.myhotel.pojo;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;

public class Customer
{
    private final int    id;
    private final String fullName;
    private final String sinNumber;
    private final String address;
    private final Date   dateOfRegistration;

    public Customer(int id, @NotNull String fullName, @NotNull String sinNumber, @NotNull String address,
                    @NotNull Date dateOfRegistration)
    {
        this.id                 = id;
        this.fullName           = fullName;
        this.sinNumber          = sinNumber;
        this.address            = address;
        this.dateOfRegistration = dateOfRegistration;
    }

    public int getId()
    {
        return id;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getSinNumber()
    {
        return sinNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public Date getDateOfRegistration()
    {
        return dateOfRegistration;
    }
}
