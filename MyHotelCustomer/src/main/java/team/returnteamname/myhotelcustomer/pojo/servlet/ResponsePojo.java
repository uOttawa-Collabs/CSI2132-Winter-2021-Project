package team.returnteamname.myhotelcustomer.pojo.servlet;

import team.returnteamname.myhotelcustomer.pojo.AbstractPojo;

public class ResponsePojo extends AbstractPojo
{
    private String code;
    private String message;

    public ResponsePojo()
    {}

    public ResponsePojo(String code, String message)
    {
        this.code    = code;
        this.message = message;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
