package Pojo;

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

    public Employee(Integer id, String fullName, String sinNumber,
                    String address, BigDecimal salary, String role)
    {
        this.id        = id;
        this.fullName  = fullName;
        this.sinNumber = sinNumber;
        this.address   = address;
        this.salary    = salary;
        this.role      = role;
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

    public BigDecimal getSalary()
    {
        return salary;
    }

    public void setSalary(BigDecimal salary)
    {
        this.salary = salary;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}
