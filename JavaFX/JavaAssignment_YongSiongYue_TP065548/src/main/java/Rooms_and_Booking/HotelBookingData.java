package Rooms_and_Booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HotelBookingData {
    private static HotelBookingData instance = new HotelBookingData();

    private static String filename = "src/main/resources/TextFile-Datas/booking-data.txt";
    ObservableList<HotelBookings> hotelBookingsObservableList;

    public static HotelBookingData getInstance() {
        return instance;
    }

    public ObservableList<HotelBookings> getHotelBookings() {
        return hotelBookingsObservableList;
    }

    public void addHotelBooking(HotelBookings hotelBookingsItem) {
        hotelBookingsObservableList.add(hotelBookingsItem);
    }

    public void loadHotelBookingsData() throws IOException {
        hotelBookingsObservableList = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader reader = Files.newBufferedReader(path);
        String input;
        try {
            while ((input = reader.readLine()) != null) {
                String[] BookingData = input.split(",");

                String roomNumber = BookingData[0];
                String customerName = BookingData[1];
                String customerIC = BookingData[2];
                String customerGmail = BookingData[3];
                String customerPhone = BookingData[4];

                String bookingDate = BookingData[5];
                String startDate = BookingData[6];
                String endDate = BookingData[7];

                String checkInTime = BookingData[8];
                String checkOutTime = BookingData[9];

                String totalDays = BookingData[10];
                String totalPrice = BookingData[11];

                String status = BookingData[12];
                String managedBy = BookingData[13];

                HotelBookings hotelBookings = new HotelBookings(roomNumber, customerName, customerIC, customerGmail, customerPhone, bookingDate, startDate, endDate, checkInTime, checkOutTime, totalDays, totalPrice, status, managedBy);
                hotelBookingsObservableList.add(hotelBookings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeHotelBooking() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter writer = Files.newBufferedWriter(path);

        if (hotelBookingsObservableList != null) {
            for (HotelBookings hotelBookings : hotelBookingsObservableList) {
                writer.write(hotelBookings.getRoomNumber() + "," + hotelBookings.getCustomerName() + "," + hotelBookings.getCustomerIC() + "," + hotelBookings.getCustomerEmail()
                        + "," + hotelBookings.getCustomerPhone() + "," + hotelBookings.getBookingDate() + "," + hotelBookings.getStartDate() + "," + hotelBookings.getEndDate() + "," +
                        hotelBookings.getCheckInTime() + "," + hotelBookings.getCheckOutTime() + "," + hotelBookings.getTotalDays() + "," + hotelBookings.getTotalPrice() + "," + hotelBookings.getStatus() + "," +
                        hotelBookings.getManagedBy());
                writer.newLine();
            }
        }
        writer.close();
    }
}
