package team.returnteamname.myhotel.service;

import org.jetbrains.annotations.NotNull;
import team.returnteamname.myhotel.connection.ConnectionFactory;
import team.returnteamname.myhotel.pojo.Customer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManagementService
{
    private static final ConnectionFactory connectionFactory  = ConnectionFactory.getInstance();
    private static final String            registerSQL        = "INSERT INTO customer VALUES (?, ?, ?, ?, ?);";
    private static final String            getCustomerByIDSQL = "SELECT * FROM customer WHERE id = ?;";

    public static void register(@NotNull Customer customer) throws SQLException
    {
        PreparedStatement statement = connectionFactory.getConnection().prepareStatement(registerSQL);

        statement.setInt(1, customer.getId());
        statement.setString(2, customer.getFullName());
        statement.setString(3, customer.getSinNumber());
        statement.setString(4, customer.getAddress());
        statement.setDate(5, customer.getDateOfRegistration());

        statement.execute();
    }

    public static Customer getCustomerByID(int id) throws SQLException
    {
        PreparedStatement statement = connectionFactory.getConnection().prepareStatement(getCustomerByIDSQL);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
        {
            return new Customer(id,
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getDate(5));
        }
        else
        {
            return null;
        }
    }
}
