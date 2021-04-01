package team.returnteamname.myhotel.ui.menu.actions.cli;

import team.returnteamname.myhotel.config.IConfigConstant;
import team.returnteamname.myhotel.dao.BaseDao;
import team.returnteamname.myhotel.pojo.AbstractPojo;
import team.returnteamname.myhotel.ui.IUserInterface;
import team.returnteamname.myhotel.ui.menu.actions.AbstractAction;
import team.returnteamname.myhotel.util.Pair;

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
            Class<?> pojoClass = getPojoClassFromUser(userInterface);

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
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Available forms:\n");
        for (String string : IConfigConstant.POJO_LIST)
            stringBuilder.append(string).append('\n');

        userInterface.eventCallback("print", stringBuilder);
    }

    private Class<?> getPojoClassFromUser(IUserInterface userInterface)
    {
        for (; ; )
        {
            try
            {
                String input = (String) userInterface
                    .eventCallback("readLine", "Please choose one from the list, or type \"q\" to quit");

                if (input.equals("q"))
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

    private void doInsert(IUserInterface userInterface, Class<?> pojoClass)
    {
        try
        {
            userInterface.eventCallback("printLine", "Please populate the object that is to be inserted.");
            AbstractPojo pojo         = CommonUtil.getPojoInstanceFromUser(userInterface, pojoClass);
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
            AbstractPojo matcher      = CommonUtil.getPojoInstanceFromUser(userInterface, pojoClass);
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
            AbstractPojo                             matcher = CommonUtil
                .getPojoInstanceFromUser(userInterface, pojoClass);
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
            AbstractPojo matcher = CommonUtil.getPojoInstanceFromUser(userInterface, pojoClass);
            userInterface.eventCallback("printLine", "Please populate the carrier.");
            AbstractPojo carrier      = CommonUtil.getPojoInstanceFromUser(userInterface, pojoClass);
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
