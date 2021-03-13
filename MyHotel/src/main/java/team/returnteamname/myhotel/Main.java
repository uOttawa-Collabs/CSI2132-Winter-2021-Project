package team.returnteamname.myhotel;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            System.out.println("Hello world");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
