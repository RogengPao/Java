package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Update {
    @FXML
    private TextField updateName, updateIC, updateEmail, updatePhone, updateBookingDate, updateStatus, updateEndDate, updatedTotalPrice;
    @FXML
    private Label updateManagedBy;
    @FXML
    private ComboBox<String> updateRoomNumber, updateCheckIn, updateCheckOut;
    @FXML
    private Spinner<Integer> daySpinner;
    @FXML
    private DatePicker updateStartDate;

    public void initialize(String name, String ic, String email, String phone, String bookingDate, String status, String managedBy, String updateRoomNumber1, LocalDate startDate, String checkIn, String checkOut, String endDate) {
        updateName.setText(name);
        updateIC.setText(ic);
        updateEmail.setText(email);
        updatePhone.setText(phone);
        updateBookingDate.setText(bookingDate);
        updateStatus.setText(status);
        updateManagedBy.setText(managedBy);
        updateCheckIn.setValue(checkIn);
        updateCheckOut.setValue(checkOut);

        CheckInDialog checkInDialog = new CheckInDialog();
        checkInDialog.setValuesForFields(updateCheckIn, updateCheckOut, updateRoomNumber);

        updateRoomNumber.setValue(updateRoomNumber1);
        updateStartDate.setValue(startDate);
        updateEndDate.setText(endDate);

        String[] existingDate = updateEndDate.getText().split("-");
        LocalDate existingEndDate = LocalDate.of(Integer.parseInt(existingDate[0]), Integer.parseInt(existingDate[1]), Integer.parseInt(existingDate[2]));
        int totalDays = Math.toIntExact(ChronoUnit.DAYS.between(startDate, existingEndDate));
        System.out.println(totalDays);

        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, totalDays);
        daySpinner.setValueFactory(svf);

        updatedTotalPrice.setText(String.valueOf(totalDays * 350));

        ObjectProperty<Integer> dayValue = svf.valueProperty();
        dayValue.addListener(((observableValue, oldInteger, newInteger) -> {
            int price = 350;
            updatedTotalPrice.setText(String.valueOf(newInteger * price));

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            updateEndDate.setText(String.valueOf(LocalDate.parse(df.format(updateStartDate.getValue())).plusDays(newInteger)));
        }));

        ObjectProperty<LocalDate> startDateValue = updateStartDate.valueProperty();
        startDateValue.addListener((((observableValue, oldLocalDate, newLocalDate) -> {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            updateEndDate.setText(String.valueOf(LocalDate.parse(df.format(updateStartDate.getValue())).plusDays(daySpinner.getValue())));
        })));

        updateEndDate.setDisable(true);
        updatedTotalPrice.setDisable(true);

        if (status.equals("Checked In")) {
            updateRoomNumber.setDisable(true);
            updateStartDate.setDisable(true);
            updateCheckIn.setDisable(true);
        }
    }

    public void processUpdate() throws IOException {
        for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
            if (i.getCustomerIC().equals(updateIC.getText())) {
                i.setRoomNumber(updateRoomNumber.getValue());
                i.setStartDate(String.valueOf(updateStartDate.getValue()));
                i.setEndDate(updateEndDate.getText());
                i.setCheckInTime(updateCheckIn.getValue());
                i.setCheckOutTime(updateCheckOut.getValue());
                i.setTotalDays(String.valueOf(daySpinner.getValue()));
                i.setTotalPrice(updatedTotalPrice.getText());
            }
        }
        HotelBookingData.getInstance().storeHotelBooking();
    }
}
