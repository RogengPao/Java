package Rooms_and_Booking;

public class HotelRooms {
    private String roomNumber;
    private String roomType;
    private String maxOccupancy;
    private String Price;
    private String Availability;
    private String bedType;

    public HotelRooms(String roomNumber, String roomTYpe, String maxOccupancy, String price, String availability, String bedType) {
        this.roomNumber = roomNumber;
        this.roomType = roomTYpe;
        this.maxOccupancy = maxOccupancy;
        Price = price;
        Availability = availability;
        this.bedType = bedType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTYpe() {
        return roomType;
    }

    public void setRoomTYpe(String roomTYpe) {
        this.roomType = roomTYpe;
    }

    public String getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(String maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    @Override
    public String toString() {
        return roomNumber + " " + roomType;
    }


}
