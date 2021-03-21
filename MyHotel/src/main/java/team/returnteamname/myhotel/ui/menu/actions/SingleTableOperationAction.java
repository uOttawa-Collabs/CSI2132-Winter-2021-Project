package team.returnteamname.myhotel.ui.menu.actions;

import org.postgresql.util.PGInterval;
import team.returnteamname.myhotel.config.IConfigConstant;
import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.AbstractAction;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;

public class SingleTableOperationAction extends AbstractAction
{
    private static final String[] operationList = { "Insert", "Delete", "Query", "Update", "Quit" };

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
            while (Character.toUpperCase(
                ((String) userInterface.eventCallback("readLine", "More operations? <Y/n>")).charAt(0)) == 'Y');
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
                    .eventCallback("readLine", "Please choose one from the list, or type \"Quit\" to quit: ");

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
                case "Cancel":
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
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException
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
            AbstractPojo pojo        = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao      dao         = new BaseDao();
            int          rowAffected = dao.insert(pojo);
            userInterface.eventCallback("printLine", "Row affected: " + rowAffected);
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
            AbstractPojo matcher     = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao      dao         = new BaseDao();
            int          rowAffected = dao.delete(matcher);
            userInterface.eventCallback("printLine", "Row affected: " + rowAffected);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed.");
            userInterface.eventCallback("printLine", "Reason: " + e);
        }
    }

    private void doQuery(IUserInterface userInterface, Class<?> pojoClass)
    {

    }

    private void doUpdate(IUserInterface userInterface, Class<?> pojoClass)
    {
        try
        {
            userInterface.eventCallback("printLine", "Please populate the matcher.");
            AbstractPojo matcher = getPojoInstanceFromUser(userInterface, pojoClass);
            userInterface.eventCallback("printLine", "Please populate the carrier.");
            AbstractPojo carrier     = getPojoInstanceFromUser(userInterface, pojoClass);
            BaseDao      dao         = new BaseDao();
            int          rowAffected = dao.update(matcher, carrier);
            userInterface.eventCallback("printLine", "Row affected: " + rowAffected);
        }
        catch (Exception e)
        {
            userInterface.eventCallback("printLine", "Operation failed.");
            userInterface.eventCallback("printLine", "Reason: " + e);
        }
    }
}
