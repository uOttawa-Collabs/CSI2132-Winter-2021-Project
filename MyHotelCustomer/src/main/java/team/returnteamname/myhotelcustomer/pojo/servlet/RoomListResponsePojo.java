package team.returnteamname.myhotelcustomer.pojo.servlet;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class RoomListResponsePojo extends ResponsePojo
{
    private List<Data> data;

    public RoomListResponsePojo()
    {
        this.data = new LinkedList<>();
    }

    public RoomListResponsePojo(String code, String message, List<Data> data)
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

    public void addData(Data data)
    {
        this.data.add(data);
    }

    public static class Data implements Serializable
    {
        private String       id;
        private String       price;
        private String       type;
        private String       capacity;
        private List<String> amenity;
        private List<String> extensibility;
        private List<String> view;

        public Data()
        {
            this.amenity       = new LinkedList<>();
            this.extensibility = new LinkedList<>();
            this.view          = new LinkedList<>();
        }

        public Data(String id, String price, String type, String capacity, List<String> amenity,
                    List<String> extensibility, List<String> view)
        {
            this.id            = id;
            this.price         = price;
            this.type          = type;
            this.capacity      = capacity;
            this.amenity       = amenity;
            this.extensibility = extensibility;
            this.view          = view;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getPrice()
        {
            return price;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getCapacity()
        {
            return capacity;
        }

        public void setCapacity(String capacity)
        {
            this.capacity = capacity;
        }

        public List<String> getAmenity()
        {
            return amenity;
        }

        public void setAmenity(List<String> amenity)
        {
            this.amenity = amenity;
        }

        public List<String> getExtensibility()
        {
            return extensibility;
        }

        public void setExtensibility(List<String> extensibility)
        {
            this.extensibility = extensibility;
        }

        public List<String> getView()
        {
            return view;
        }

        public void setView(List<String> view)
        {
            this.view = view;
        }
    }
}
