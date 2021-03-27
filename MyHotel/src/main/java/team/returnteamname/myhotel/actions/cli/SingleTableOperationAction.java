package team.returnteamname.myhotel.actions.cli;

import org.postgresql.util.PGInterval;
import team.returnteamname.myhotel.config.IConfigConstant;
import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;
import team.returnteamname.myhotel.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.function.Supplier;

public class SingleTableOperationAction extends AbstractAction
{
    private static final String[] operationList = { "Insert", "Delete", "Query", "Update" };

    @Override
    public Object run(IUserInterface userInterface)
    {
        for (; ; )
        {
            printPojoList(userInterface);
            Class<?> pojoClass = getPojoClass(userInterface);

            if (pojoClass == null)
                return null;

            do
            {
                operatePojo(userInterface, pojoClass);
            }
            while (((Supplier<Boolean>) () ->
            {
                String input = (String) userInterface.eventCallback("readLine", "More operations? <y/N>");
                return !input.isEmpty() && (Character.toUpperCase(input.charAt(0)) == 'Y');
            }).get());
        }
    }

    private void printPojoList(IUserInterface userInterface)
    {
        userInterface.eventCallback("printLine", "Available forms:");
        for (String string : IConfigConstant.POJO_LIST)
            userInterface.eventCallback("printLine", string);
    }

    private Class<?> getPojoClass(IUserInterface userInterface)
    {
        for (; ; )
        {
            try
            {
                String input = (String) userInterface
                    .eventCallback("readLine", "Please choose one from the list, or type \"Quit\" to quit");

                if (input.equals("Quit"))
                    return null;

                return Class.forName(IConfigConstant.ROOT_PACKAGE_NAME + ".pojo." + input);
            }
            catch (ClassNotFoundException e)
            {
                userInterface.eventCallback("printLine", "Not a valid choice. Try again.");
            }
        }
    }

    private void operatePojo(IUserInterface userInterface, Class<?> pojoClass)
    {
        userInterface.eventCallback("printLine", "List of operations:");

        for (String operation : operationList)
            userInterface.eventCallback("printLine", operation);

        for (; ; )
        {
            switch ((String) userInterface.eventCallback("readLine", "Please select an operation"))
            {
                case "Insert":
                    doInsert(userInterface, pojoClass);
                    return;
                case "Delete":
                    doDelete(userInterface, pojoClass);
                    return;
                case "Query":
                    doQuery(userInterface, pojoClass);
                    return;
                case "Update":
                    doUpdate(userInterface, pojoClass);
                    return;
                default:
                    userInterface.eventCallback("printLine", "Invalid operation, please try again.");
                    break;
            }
        }
    }

    private Object parseClassInstanceFromString(Class<?> clazz, String string)
    {
        try
        {
            if (clazz.equals(Integer.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return Integer.parseInt(string);
            }
            else if (clazz.equals(String.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return string;
            }
            else if (clazz.equals(Date.class))
            {
                if (string.isEmpty())
                    return null;
                else if (string.equals("Current"))
                    return new Date(new java.util.Date().getTime());
                else
                    return new Date(Long.parseLong(string));
            }
            else if (clazz.equals(BigDecimal.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return new BigDecimal(string);
            }
            else if (clazz.equals(PGInterval.class))
            {
                if (string.isEmpty())
                    return null;
                else
                    return new PGInterval(string);
            }
            else
            {
                throw new UnsupportedOperationException(
                    "The specified class (" + clazz.getName() + ") is currently not supported");
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    private AbstractPojo getPojoInstanceFromUser(IUserInterface userInterface, Class<?> pojoClass)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        AbstractPojo pojo   = (AbstractPojo) pojoClass.getDeclaredConstructor().newInstance();
        Field[]      fields = pojoClass.getDeclaredFields();

        for (Field field : fields)
        {
            String   fieldName = field.getName();
            Class<?> fieldType = field.getType();

            Object instance;
            for (; ; )
            {
                try
                {
                    String hint;
                    if (fieldType.equals(Date.class))
                        hint = fieldName + "(" + fieldType
                            .getName() + ", type \"Current\" to fill in the current date)";
                    else
                        hint = fieldName + "(" + fieldType.getName() + ")";

                    instance = parseClassInstanceFromString(fieldType,
                                                            ((String) userInterface.eventCallback("readLine", hint))
                                                                .trim());
                    break;
                }
                catch (RuntimeException e)
                {
                    userInterface.eventCallback("printLine", "Invalid input, please try again.");
                    userInterface.eventCallback("printLine", "Reason: " + e);
                }
            }
            field.setAccessible(true);
            field.set(pojo, instance);
        }

        return pojo;
    }

    private void doInsert(IUserInterface userInterface, Class<?> pojoClass)
    {
        try
        {
            userInterface.eventCallback("printLine", "Please populate the object that is to be inserted.");
            AbstractPojo pojo         = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao      dao          = new BaseDao();
            int          rowsAffected = dao.insert(pojo);
            userInterface.eventCallback("printLine", "Rows affected: " + rowsAffected);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed.");
            userInterface.eventCallback("printLine", "Reason: " + e);
        }
    }

    private void doDelete(IUserInterface userInterface, Class<?> pojoClass)
    {
        try
        {
            userInterface.eventCallback("printLine", "Please populate the matcher.");
            AbstractPojo matcher      = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao      dao          = new BaseDao();
            int          rowsAffected = dao.delete(matcher);
            userInterface.eventCallback("printLine", "Rows affected: " + rowsAffected);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed.");
            userInterface.eventCallback("printLine", "Reason: " + e);
        }
    }

    private void doQuery(IUserInterface userInterface, Class<?> pojoClass)
    {
        try
        {
            userInterface.eventCallback("printLine", "Please populate the matcher.");
            AbstractPojo                             matcher = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao                                  dao     = new BaseDao();
            Pair<ArrayList<AbstractPojo>, ResultSet> pair    = dao.select(matcher);

            userInterface.eventCallback("printLine", "Results:");

            for (AbstractPojo pojo : pair.getKey())
                userInterface.eventCallback("printLine", pojo);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed.");
            userInterface.eventCallback("printLine", "Reason: " + e);
        }
    }

    private void doUpdate(IUserInterface userInterface, Class<?> pojoClass)
    {
        try
        {
            userInterface.eventCallback("printLine", "Please populate the matcher.");
            AbstractPojo matcher = getPojoInstanceFromUser(userInterface, pojoClass);
            userInterface.eventCallback("printLine", "Please populate the carrier.");
            AbstractPojo carrier      = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao      dao          = new BaseDao();
            int          rowsAffected = dao.update(matcher, carrier);
            userInterface.eventCallback("printLine", "Rows affected: " + rowsAffected);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed.");
            userInterface.eventCallback("printLine", "Reason: " + e);
        }
    }
}
