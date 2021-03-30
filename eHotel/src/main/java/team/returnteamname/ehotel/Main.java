package team.returnteamname.ehotel;

import org.postgresql.util.PGInterval;
import team.returnteamname.ehotel.config.Config;
import team.returnteamname.ehotel.config.ConfigManager;
import team.returnteamname.ehotel.config.IConfigConstant;
import team.returnteamname.ehotel.config.source.FileConfigSource;
import team.returnteamname.ehotel.pojo.Book;
import team.returnteamname.ehotel.pojo.Employment;
import team.returnteamname.ehotel.pojo.Rent;
import team.returnteamname.ehotel.pojo.Room;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private       Statement  stmt;
    private       Connection c;

    public Main(String driver, String url, String user, String password)
    {
        try
        {
            Class.forName(driver);
            c    = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement();
            System.out.println("Success to connect database!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);

        FileConfigSource source = new FileConfigSource(IConfigConstant.CONFIG_FILE_PATH);
        source.loadSource();
        ConfigManager.getInstance().load(source.getConfigString());
        Config config = ConfigManager.getInstance().getConfig();

        String url = "jdbc:" + config.getDbms() + "://" + config.getServerAddress() + "/"
                     + config.getDatabase() + "?ApplicationName=" + IConfigConstant.ROOT_PACKAGE_NAME;
        Main employeeService = new Main(config.getDriver(), url, config.getUsername(), config.getPassword());

        boolean serviceYNCheck     = true;
        boolean EmployeeIdCheck    = false;
        boolean serviceChooseCheck = false;
        String  hotelBrandName     = "";
        String  hotelName          = "";
        String  employeeId         = "";
        int     employeeID         = 0;

        System.out.println(
            "-----------------------------------------Employee Service-----------------------------------------");

        while (!EmployeeIdCheck)
        {
            System.out.print("Please enter your employee ID: ");

            employeeId = scan.nextLine();
            employeeID = Integer.parseInt(employeeId);
            Employment employment = employeeService.accessEmployment(employeeID);

            if (employment != null)
            {
                hotelBrandName = employment.getHotelBrandName();
                hotelName      = employment.getHotelName();

                System.out.println(
                    "Employee " + employeeId + " only has permission to access and operate service in " + hotelName + " (" + hotelBrandName + ")");
                System.out.println();
                EmployeeIdCheck = true;
            }

            else
            {
                System.out.println("Your employee ID is invalid. ");
            }
        }

        while (serviceYNCheck)
        {
            while (!serviceChooseCheck)
            {
                System.out.println("1.Check-in / 2.Check-out / 3.Access avaliable rooms / 4.Access booked rooms");
                System.out.print("Please select which service you want to do? (1/2/3/4) ");

                String num = scan.nextLine();

                System.out.println();

                switch (num)
                {
                    case "1":
                    {
                        System.out.print("Please enter the customer ID: ");

                        String customerId = scan.nextLine();
                        int    customerID = Integer.parseInt(customerId);
                        Book   b          = employeeService.accessBook(customerID);

                        //Check-in with booking
                        if (b != null)
                        {
                            System.out.print("How long you want to rent? (Please enter a number) ");
                            String day  = scan.nextLine();
                            int    days = Integer.parseInt(day);

                            boolean checkIn = employeeService.checkInWithBooking(customerID, b, days, employeeID);

                            if (checkIn)
                            {
                                System.out.println("Check-in successfully!");
                            }

                            serviceChooseCheck = true;
                        }

                        //Check-in without booking
                        else
                        {
                            System.out.println(
                                "Customer " + customerID + " doesn't have booking history, please enter more information to finish check-in service.");

                            List<Room> availableRooms;
                            availableRooms = employeeService.accessAvailableRoom(employeeID);

                            String numOfRooms = String.valueOf(availableRooms.size());
                            System.out.println(
                                "There are/is " + numOfRooms + " room(s) are available in " + hotelName + " (" + hotelBrandName + ")");

                            for (int i = 0; i < availableRooms.size(); i++)
                            {
                                Room room = availableRooms.get(i);

                                String roomId = String.valueOf(room.getRoomId());
                                String price  = (room.getPrice()).toString();

                                System.out
                                    .println("Room ID: " + roomId + "; Price: " + price + "; Room capacity: " + room
                                        .getRoomCapacity());
                            }

                            System.out.print("Please select which room you prefer to live (Enter a room ID): ");
                            String roomId = scan.nextLine();
                            int    roomID = Integer.parseInt(roomId);

                            String roomType = "Non-smoking";
                            System.out.print("How long you want to rent? (Please enter a number) ");
                            String day  = scan.nextLine();
                            int    days = Integer.parseInt(day);

                            System.out.print("How many occupants? (Please enter a number) ");
                            String totalNumberOccupant  = scan.nextLine();
                            int    totalNumberOccupants = Integer.parseInt(totalNumberOccupant);

                            java.util.Date utilDate = new java.util.Date();
                            Date           date     = new Date(utilDate.getTime());

                            boolean checkIn = employeeService
                                .checkInWithoutBooking(customerID, roomID, employeeID, date, roomType,
                                                       totalNumberOccupants,
                                                       days);

                            if (checkIn)
                            {
                                System.out.println("Check-in successfully!");
                            }

                            serviceChooseCheck = true;
                        }
                        break;
                    }
                    case "2":
                    {
                        System.out.print("Please enter the customer ID: ");

                        String  customerId = scan.nextLine();
                        int     customerID = Integer.parseInt(customerId);
                        Rent    r          = employeeService.accessRent(customerID);
                        boolean checkout   = employeeService.checkOut(customerID, r, employeeID);

                        if (checkout)
                        {
                            System.out.println("Check-out successfully!");
                        }

                        serviceChooseCheck = true;
                        break;
                    }
                    case "3":
                    {
                        List<Room> availableRooms;
                        availableRooms = employeeService.accessAvailableRoom(employeeID);

                        String numOfRooms = String.valueOf(availableRooms.size());
                        System.out.println(
                            "There are " + numOfRooms + " rooms are available in " + hotelName + " (" + hotelBrandName + ")");

                        for (int i = 0; i < availableRooms.size(); i++)
                        {
                            Room room = availableRooms.get(i);

                            String roomId = String.valueOf(room.getRoomId());
                            String price  = (room.getPrice()).toString();

                            System.out.println(
                                "Room ID: " + roomId + "; Price: " + price + "; Room capacity: " + room
                                    .getRoomCapacity());

                            serviceChooseCheck = true;
                        }
                        break;
                    }
                    case "4":
                    {
                        List<Book> bookedRooms;
                        bookedRooms = employeeService.accessBookedRoom(employeeID);

                        String numOfRooms = String.valueOf(bookedRooms.size());
                        System.out.println(
                            "There are " + numOfRooms + " rooms are booked in " + hotelName + " (" + hotelBrandName + ")");

                        for (int i = 0; i < bookedRooms.size(); i++)
                        {
                            Book bookRoom = bookedRooms.get(i);

                            String           customerId           = String.valueOf(bookRoom.getCustomerId());
                            String           roomId               = String.valueOf(bookRoom.getRoomId());
                            SimpleDateFormat f                    = new SimpleDateFormat("yyyy-MM-dd");
                            String           checkInDate          = f.format(bookRoom.getCheckInDate());
                            String           totalNumberOccupants = String.valueOf(bookRoom.getTotalNumberOccupants());


                            System.out.println(
                                "Room ID: " + roomId + "; Customer ID: " + customerId + "; Check-in Date： " + checkInDate
                                + "; Room Type: " + bookRoom
                                    .getRoomType() + "; Total number of occupants: " + totalNumberOccupants);

                        }

                        serviceChooseCheck = true;
                        break;
                    }
                    default:
                        System.out.println("The number is invalid.");
                        break;
                }
            }

            System.out.print("Do you want to start another service? (Y/N) ");
            String answer = scan.nextLine();

            if (answer.equals("N") || answer.equals("n"))
            {
                serviceYNCheck = false;
                System.out.println("Have a nice day.");
            }

            else
            {
                serviceYNCheck     = true;
                serviceChooseCheck = false;
            }

        }
    }

    public Connection getConnection()
    {
        return c;
    }

    /* Query 'Employment' table to get hotel_brand_name and hotel_name */
    public Employment accessEmployment(int employeeId)
    {
        Employment employment = new Employment();

        try
        {
            String    queryEmployment = "SELECT * FROM employment WHERE employee_id= '" + employeeId + "'";
            ResultSet rsEmployment    = stmt.executeQuery(queryEmployment);

            if (rsEmployment == null)
            {
                return null;
            }

            else
            {
                while (rsEmployment.next())
                {
                    employment = new Employment(rsEmployment.getString("hotel_brand_name"),
                                                rsEmployment.getString("hotel_name"), employeeId);
                }


                if (employment.getEmployeeId() == 0)
                {
                    employment = null;
                }
            }
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        return employment;
    }

    /* Access available rooms for the employee's employment hotel */
    public List<Room> accessAvailableRoom(int employeeId)
    {
        List<Room> totalRooms  = new ArrayList<Room>();
        List<Room> bookedRooms = new ArrayList<Room>();

        try
        {
            // Query 'Employment' table to get hotel_brand_name and hotel_name
            Employment employment     = accessEmployment(employeeId);
            String     hotelBrandName = employment.getHotelBrandName();
            String     hotelName      = employment.getHotelName();

            // According to hotel_brand_name and hotel_name, Query 'Room' table to get room_id
            String queryRoom = "SELECT * FROM room WHERE hotel_brand_name= '" + hotelBrandName
                               + "' AND hotel_name = '" + hotelName + "'";
            ResultSet rsRoom = stmt.executeQuery(queryRoom);

            Room totalRoom;

            while (rsRoom.next())
            {
                int        roomId       = rsRoom.getInt("room_id");
                BigDecimal price        = rsRoom.getBigDecimal("price");
                String     roomCapacity = rsRoom.getString("room_capacity");

                totalRoom = new Room(hotelBrandName, hotelName, roomId, price, roomCapacity);
                totalRooms.add(totalRoom);
            }

            // According to hotel_brand_name and hotel_name, Query 'Book' table to get room_id
            String queryBook = "SELECT room_id FROM book WHERE hotel_brand_name='" + hotelBrandName
                               + "' AND hotel_name = '" + hotelName + "'";
            ResultSet rsBook = stmt.executeQuery(queryBook);

            while (rsBook.next())
            {
                int        roomId           = rsBook.getInt("room_id");
                BigDecimal bookPrice        = new BigDecimal("0.00");
                String     bookRoomCapacity = "";

                for (int i = 0; i < totalRooms.size(); i++)
                {
                    Room room;
                    room = totalRooms.get(i);

                    if (roomId == room.getRoomId())
                    {
                        bookPrice        = room.getPrice();
                        bookRoomCapacity = room.getRoomCapacity();
                    }

                    Room bookedRoom = new Room(hotelBrandName, hotelName, roomId, bookPrice, bookRoomCapacity);
                    bookedRooms.add(bookedRoom);
                }
            }


            // Find same roomIds between 'Room' table and 'Book' table
            // and remove them from totalRooms
            for (int i = 0; i < bookedRooms.size(); i++)
            {
                Room bookedRoom2   = bookedRooms.get(i);
                int  bookedRoomId2 = bookedRoom2.getRoomId();

                for (int j = 0; j < totalRooms.size(); j++)
                {
                    Room totalRoom2   = totalRooms.get(j);
                    int  totalRoomId2 = totalRoom2.getRoomId();

                    if (bookedRoomId2 == totalRoomId2)
                    {
                        totalRooms.remove(j);
                    }
                }
            }
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        return totalRooms;
    }

    /* Access booked rooms information for the employee's employment hotel */
    public List<Book> accessBookedRoom(int employeeId)
    {
        List<Book> bookedRooms = new ArrayList<>();

        try
        {
            // Query 'Employment' table to get hotel_brand_name and hotel_name
            Employment employment     = accessEmployment(employeeId);
            String     hotelBrandName = employment.getHotelBrandName();
            String     hotelName      = employment.getHotelName();

            // According to hotel_brand_name and hotel_name, Query 'Book' table to get booked information
            String queryBook = "SELECT * FROM book WHERE hotel_brand_name='" + hotelBrandName
                               + "' AND hotel_name = '" + hotelName + "'";
            ResultSet rsBook = stmt.executeQuery(queryBook);

            while (rsBook.next())
            {
                Book customerBook = new Book(rsBook.getInt("customer_id"), rsBook.getString("hotel_brand_name"),
                                             rsBook.getString("hotel_name"), rsBook.getInt("room_id"),
                                             rsBook.getDate("check_in_date"), rsBook.getString("room_type"),
                                             rsBook.getInt("total_number_occupants"));
                bookedRooms.add(customerBook);
            }
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        return bookedRooms;
    }

    /* Query specific row with CustomerId from 'Book' table */
    public Book accessBook(int CustomerId)
    {
        Book customerBook = new Book();

        try
        {
            String    query = "SELECT * FROM book WHERE customer_id= '" + CustomerId + "'";
            ResultSet rs    = stmt.executeQuery(query);

            if (rs == null)
            {
                return null;
            }
            else
            {
                while (rs.next())
                {
                    customerBook = new Book(rs.getInt("customer_id"), rs.getString("hotel_brand_name"),
                                            rs.getString("hotel_name"), rs.getInt("room_id"),
                                            rs.getDate("check_in_date"), rs.getString("room_type"),
                                            rs.getInt("total_number_occupants"));
                }

                if (customerBook.getCustomerId() == 0)
                {
                    customerBook = null;
                }
            }
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return customerBook;
    }

    /* Delete a specific row with CustomerId from 'Book' table */
    public boolean deleteBook(int CustomerId)
    {
        try
        {
            String delete = "DELETE FROM book WHERE customer_id= '" + CustomerId + "'";
            stmt.executeUpdate(delete);
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /* Query specific row with CustomerId from 'Rent' table */
    public Rent accessRent(int CustomerId)
    {
        Rent customerRent = new Rent();
        try
        {
            String    query = "SELECT * FROM rent WHERE customer_id= '" + CustomerId + "'";
            ResultSet rs    = stmt.executeQuery(query);

            while (rs.next())
            {
                customerRent = new Rent(rs.getInt("customer_id"), rs.getString("hotel_brand_name"),
                                        rs.getString("hotel_name"), rs.getInt("room_id"),
                                        rs.getInt("check_in_employee_id"), rs.getDate("check_in_date"),
                                        rs.getString("room_type"), rs.getInt("total_number_occupants"),
                                        rs.getBigDecimal("bill_amount"), (PGInterval) rs.getObject("duration"));
            }
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        return customerRent;
    }

    /* Insert data to 'Rent' table when the customer check-in with booking */
    public boolean insertRentWithBooking(Book b, int day, int checkInEmployeeId)
    {
        try
        {
            //Get contents from 'Book' table
            int    customerId           = b.getCustomerId();
            String hotelBrandName       = b.getHotelBrandName();
            String hotelName            = b.getHotelName();
            int    roomId               = b.getRoomId();
            Date   checkInDate          = b.getCheckInDate();
            String roomType             = b.getRoomType();
            int    totalNumberOccupants = b.getTotalNumberOccupants();

            //Get room price from 'Room' table
            String roomQuery = "SELECT price FROM room WHERE hotel_brand_name = '"
                               + hotelBrandName + "'" + " AND hotel_name = '"
                               + hotelName + "'" + " AND room_id = '" + roomId + "'";
            ResultSet rs = stmt.executeQuery(roomQuery);
            rs.next();
            BigDecimal price = rs.getBigDecimal("price");

            PGInterval duration = new PGInterval();
            duration.setDays(day);

            //Calculate total payment
            BigDecimal days       = new BigDecimal(day);
            BigDecimal billAmount = price.multiply(days);

            //Insert row to 'Rent' table
            String insert = "INSERT INTO rent(customer_id, hotel_brand_name, "
                            + "hotel_name, room_id, check_in_employee_id, "
                            + "check_in_date, room_type, total_number_occupants, "
                            + "bill_amount, duration)" + "VALUES(" + customerId + ", '"
                            + hotelBrandName + "', '" + hotelName + "', " + roomId
                            + ", " + checkInEmployeeId + ", '" + checkInDate
                            + "', '" + roomType + "', " + totalNumberOccupants + ", "
                            + billAmount + ", '" + duration + "')";

            stmt.executeUpdate(insert);
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /* Insert data to 'Rent' table when the customer check-in without booking */
    public boolean checkInWithoutBooking(int customerId, int roomId, int checkInEmployeeId, Date checkInDate,
                                         String roomType, int totalNumberOccupants, int day)
    {
        try
        {
            // Get hotal_brand_name and hotel_name depends on check_in_employee_id
            Employment employment     = accessEmployment(checkInEmployeeId);
            String     hotelBrandName = employment.getHotelBrandName();
            String     hotelName      = employment.getHotelName();

            // Get price and room_capacity depends on hotel_brand_name, hotel_name and room_id
            String queryRoom = "SELECT price, room_capacity FROM room WHERE hotel_brand_name = '"
                               + hotelBrandName + "' AND hotel_name = '" + hotelName
                               + "' AND room_id = " + roomId;
            ResultSet rsRoom = stmt.executeQuery(queryRoom);

            BigDecimal price;

            rsRoom.next();
            price = rsRoom.getBigDecimal("price");

            PGInterval duration = new PGInterval();
            duration.setDays(day);

            //Calculate total payment
            BigDecimal days       = new BigDecimal(day);
            BigDecimal billAmount = price.multiply(days);

            //Insert row to 'Rent' table
            String insert = "INSERT INTO rent(customer_id, hotel_brand_name, "
                            + "hotel_name, room_id, check_in_employee_id, "
                            + "check_in_date, room_type, total_number_occupants, "
                            + "bill_amount, duration)" + "VALUES(" + customerId + ", '"
                            + hotelBrandName + "', '" + hotelName + "', " + roomId
                            + ", " + checkInEmployeeId + ", '" + checkInDate
                            + "', '" + roomType + "', " + totalNumberOccupants + ", "
                            + billAmount + ", '" + duration + "')";

            stmt.executeUpdate(insert);
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /* Delete a row with a specific CustomerId from 'Rent' table */
    public boolean deleteRent(int customerId)
    {
        try
        {
            String delete = "DELETE FROM rent WHERE customer_id= '" + customerId + "'";
            stmt.executeUpdate(delete);
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /* Insert data to 'Rent_History' table when the customer check-out */
    public boolean insertRentHistory(Rent r, int checkOutEmployeeId)
    {
        try
        {
            //Get contents from 'Rent' table
            int        customerId           = r.getCustomerId();
            String     hotelBrandName       = r.getHotelBrandName();
            String     hotelName            = r.getHotelName();
            int        roomId               = r.getRoomId();
            int        checkInEmployeeId    = r.getCheckInEmployeeId();
            Date       checkInDate          = r.getCheckInDate();
            String     roomType             = r.getRoomType();
            int        totalNumberOccupants = r.getTotalNumberOccupants();
            BigDecimal billAmount           = r.getBillAmount();
            PGInterval duration             = r.getDuration();

            //Calculate check out date
            int  days         = duration.getDays();
            Date checkOutDate = this.addDays(checkInDate, days);

            //Insert row to 'Rent_History' table
            String insert = "INSERT INTO rent_history(customer_id, hotel_brand_name, "
                            + "hotel_name, room_id, check_in_employee_id, "
                            + "check_in_date, room_type, total_number_occupants, "
                            + "bill_amount, duration, check_out_employee_id, "
                            + "check_out_date)" + "VALUES(" + customerId + ", '"
                            + hotelBrandName + "', '" + hotelName + "', "
                            + roomId + ", " + checkInEmployeeId + ", '" + checkInDate
                            + "', '" + roomType + "', " + totalNumberOccupants + ", "
                            + billAmount + ", '" + duration + "'," + checkOutEmployeeId
                            + ", '" + checkOutDate + "')";

            stmt.executeUpdate(insert);
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /* Method to calculate customer check_out_date */
    public Date addDays(Date date, int days)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

    /* Check-in Operation: Transform 'Book' info to 'Rent' table */
    public boolean checkInWithBooking(int customerId, Book b, int day, int checkInEmployeeId)
    {
        boolean deleteB = deleteBook(customerId);
        boolean insertR = insertRentWithBooking(b, day, checkInEmployeeId);

        return deleteB && insertR;
    }

    /* Check-out Operation: Transform 'Rent' info to 'Rent_History' table */
    public boolean checkOut(int customerId, Rent r, int checkOutEmployeeId)
    {
        boolean deleteR  = deleteRent(customerId);
        boolean insertRH = insertRentHistory(r, checkOutEmployeeId);

        return deleteR && insertRH;
    }
}