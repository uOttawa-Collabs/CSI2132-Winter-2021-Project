package team.returnteamname.myhotelcustomer.pojo.servlet;

import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

public class AuthenticationPojo extends AbstractPojo
{
    private String username;

    public AuthenticationPojo()
    {}

    public AuthenticationPojo(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
