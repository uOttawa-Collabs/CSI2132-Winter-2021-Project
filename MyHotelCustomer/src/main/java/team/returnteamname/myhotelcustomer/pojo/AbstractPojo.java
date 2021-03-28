package team.returnteamname.myhotelcustomer.pojo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractPojo implements Serializable
{
    private boolean toStringPrintNullValues = true;

    public AbstractPojo()
    {}

    @Override
    public String toString()
    {
        try
        {
            Class<? extends AbstractPojo> clazz     = getClass();
            String                        className = clazz.getSimpleName();
            Method[]                      methods   = clazz.getMethods();

            StringBuilder stringBuilder = new StringBuilder(className).append("(");

            boolean comma = false;
            for (Method method : methods)
            {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.startsWith("getClass"))
                {
                    String fieldName   = methodName.substring(3);
                    Object returnValue = method.invoke(this, (Object[]) null);

                    if (returnValue != null || toStringPrintNullValues)
                    {
                        if (comma)
                            stringBuilder.append(" | ");
                        stringBuilder.append(fieldName).append(" = ").append(returnValue);
                        comma = true;
                    }
                }
            }

            return stringBuilder.append(")").toString();
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            return "<reflection error>";
        }
    }

    public void setToStringPrintNullValues(boolean toStringPrintNullValues)
    {
        this.toStringPrintNullValues = toStringPrintNullValues;
    }
}
