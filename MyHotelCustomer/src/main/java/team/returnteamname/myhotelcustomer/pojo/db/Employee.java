package team.returnteamname.myhotelcustomer.pojo.db;

import org.jetbrains.annotations.Nullable;
import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

import java.math.BigDecimal;

public class Employee extends AbstractPojo
{
    private Integer    id;
    private String     fullName;
    private String     sinNumber;
    private String     address;
    private BigDecimal salary;
    private String     role;

    public Employee()
    {}

    public Employee(@Nullable Integer id, @Nullable String fullName, @Nullable String sinNumber,
                    @Nullable String address, @Nullable BigDecimal salary, @Nullable String role)
    {
        this.id        = id;
        this.fullName  = fullName;
        this.sinNumber = sinNumber;
        this.address   = address;
        this.salary    = salary;
        this.role      = role;
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
    public BigDecimal getSalary()
    {
        return salary;
    }

    public void setSalary(@Nullable BigDecimal salary)
    {
        this.salary = salary;
    }

    @Nullable
    public String getRole()
    {
        return role;
    }

    public void setRole(@Nullable String role)
    {
        this.role = role;
    }
}
