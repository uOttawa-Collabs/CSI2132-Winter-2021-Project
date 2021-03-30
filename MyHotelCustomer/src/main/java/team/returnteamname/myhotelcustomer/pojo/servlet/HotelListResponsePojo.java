package team.returnteamname.myhotelcustomer.pojo.servlet;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class HotelListResponsePojo extends ResponsePojo
{
    private List<Data> data;

    public HotelListResponsePojo()
    {
        this.data = new LinkedList<>();
    }

    public HotelListResponsePojo(String code, String message, List<Data> data)
    {
        super(code, message);
        this.data = data;
    }

    public List<Data> getData()
    {
        return data;
    }

    public void setData(List<Data> data)
    {
        this.data = data;
    }

    public static class Data implements Serializable
    {
        private String       brandName;
        private String       hotelName;
        private String       starCategory;
        private String       address;
        private List<String> phoneNumber;
        private String       roomsAvailable;

        public Data()
        {}

        public Data(String brandName, String hotelName, String starCategory, String address,
                    List<String> phoneNumber, String roomsAvailable)
        {
            this.brandName      = brandName;
            this.hotelName      = hotelName;
            this.starCategory   = starCategory;
            this.address        = address;
            this.phoneNumber    = phoneNumber;
            this.roomsAvailable = roomsAvailable;
        }

        public String getBrandName()
        {
            return brandName;
        }

        public void setBrandName(String brandName)
        {
            this.brandName = brandName;
        }

        public String getHotelName()
        {
            return hotelName;
        }

        public void setHotelName(String hotelName)
        {
            this.hotelName = hotelName;
        }

        public String getStarCategory()
        {
            return starCategory;
        }

        public void setStarCategory(String starCategory)
        {
            this.starCategory = starCategory;
        }

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public List<String> getPhoneNumber()
        {
            return phoneNumber;
        }

        public void setPhoneNumber(List<String> phoneNumber)
        {
            this.phoneNumber = phoneNumber;
        }

        public String getRoomsAvailable()
        {
            return roomsAvailable;
        }

        public void setRoomsAvailable(String roomsAvailable)
        {
            this.roomsAvailable = roomsAvailable;
        }
    }
}
