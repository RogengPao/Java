package Rooms_and_Booking;

public class HotelBookings {
    private String roomNumber;
    private String customerName;
    private String customerEmail;
    private String customerIC;
    private String customerPhone;
    private String checkInTime;
    private String checkOutTime;
    private String totalPrice;
    private String ManagedBy;
    private String endDate;
    private String bookingDate;
    private String  startDate;
    private String totalDays;
    private String status;

    public HotelBookings() {
    }

    public HotelBookings(String roomNumber, String customerName, String customerIC, String customerEmail, String customerPhone, String bookingDate, String startDate, String endDate, String checkInTime, String checkOutTime, String totalDays, String totalPrice,String status, String managedBy) {
        this.roomNumber = roomNumber;
        this.customerName = customerName;
        this.customerIC = customerIC;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;

        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;

        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.totalPrice = totalPrice;
        this.ManagedBy = managedBy;
        this.status = status;

        this.totalDays = totalDays;
    }

    @Override
    public String toString() {
        return roomNumber + ", " + customerName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerIC() {
        return customerIC;
    }

    public void setCustomerIC(String customerIC) {
        this.customerIC = customerIC;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getManagedBy() {
        return ManagedBy;
    }

    public void setManagedBy(String managedBy) {
        ManagedBy = managedBy;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
