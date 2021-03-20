package team.returnteamname.myhotel.ui.menu;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import team.returnteamname.myhotel.util.Util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Menu
{
    private static final String rootPackageName = "team.returnteamname.myhotel";
    private static       Menu   rootMenu;

    private final ArrayList<AbstractAction> actions;
    private final ArrayList<Menu>           children;
    private       String             name;
    private       Menu               parent;

    public Menu()
    {
        this.children = new ArrayList<>();
        this.actions  = new ArrayList<>();
    }

    public Menu(ArrayList<AbstractAction> actions, String name, Menu parent,
                ArrayList<Menu> children)
    {
        this.actions  = actions;
        this.name     = name;
        this.parent   = parent;
        this.children = children;
    }

    public static void loadMenu(String source)
        throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        rootMenu        = new Menu();
        JsonReader jsonReader = new JsonReader(new StringReader(source));
        loadMenuRecursion(rootMenu, jsonReader);
        rootMenu = rootMenu.getChildren().get(0);
        rootMenu.parent = null;
    }

    private static void loadMenuRecursion(Menu currentMenu, JsonReader jsonReader)
        throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException
    {
        while (jsonReader.hasNext() && !jsonReader.peek().name().equals("END_DOCUMENT"))
        {
            jsonReader.beginObject();

            Util.assertString(jsonReader.nextName(), "name");
            String name = jsonReader.nextString();

            Util.assertString(jsonReader.nextName(), "type");
            String type = jsonReader.nextString();

            switch (type)
            {
                case "menu":
                    Menu menu = new Menu();
                    menu.name = name;
                    menu.parent = currentMenu;

                    Util.assertString(jsonReader.nextName(), "content");
                    Util.assertString(jsonReader.peek().name(), "BEGIN_ARRAY",
                                      "The value of \"content\" must be an array when type is \"menu\"");
                    jsonReader.beginArray();
                    loadMenuRecursion(menu, jsonReader);
                    currentMenu.children.add(menu);
                    jsonReader.endArray();
                    break;
                case "action":
                    Util.assertString(jsonReader.nextName(), "content");
                    Util.assertString(jsonReader.peek().name(), "STRING",
                                      "The value of \"content\" must be a string when type is \"action\"");

                    String className = jsonReader.nextString();
                    if (className.isEmpty())
                        break;
                    if (className.startsWith("."))
                        className = rootPackageName + className;

                    Class<?> clazz = Class.forName(className);
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    if (instance instanceof AbstractAction)
                    {
                        AbstractAction action = (AbstractAction) instance;
                        Field          field  = AbstractAction.class.getDeclaredField("name");
                        field.setAccessible(true);
                        field.set(action, name);
                        currentMenu.actions.add(action);
                    }
                    else
                        throw new IllegalArgumentException(className + " does not implement " + "IAction");
                    break;
                default:
                    throw new JsonParseException("Unknown key " + type + " in current context");
            }
            jsonReader.endObject();
        }
    }

    public static Menu getRootMenu()
    {
        return rootMenu;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<Menu> getChildren()
    {
        return children;
    }

    public Menu getParent()
    {
        return parent;
    }

    public ArrayList<AbstractAction> getActions()
    {
        return actions;
    }

    public String getPath()
    {
        Menu menu = this;
        StringBuilder path = new StringBuilder();
        getPathRecursion(menu, path);
        return path.toString();
    }

    private void getPathRecursion(Menu menu, StringBuilder path)
    {
        if (menu != null)
        {
            getPathRecursion(menu.getParent(), path);
            path.append(menu.name).append("/");
        }
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
