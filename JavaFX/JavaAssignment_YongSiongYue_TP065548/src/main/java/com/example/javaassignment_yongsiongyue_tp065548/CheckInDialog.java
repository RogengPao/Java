package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckInDialog {
    @FXML
    public TextField managedBy, customerName, customerIC, customerEmail, customerPhone,  bookingDate, endDate, totalPrice;
    @FXML
    public DatePicker startDate;
    @FXML
    public ComboBox<String> checkInTime, checkOutTime, roomNumber;
    @FXML
    public Spinner<Integer> daySpinner;
    @FXML
    public Label warningName, warningIC, warningEmail, warningPhone, checkOutTimeWarning, checkInTimeWarning, roomNumberWarning;

    public void initialize() {
        Session session = Session.getInstance();
        String staffName = (String) session.get("username");

        managedBy.setText(staffName);
        bookingDate.setText(String.valueOf(LocalDate.now()));

        startDate.setValue(LocalDate.now());
        startDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });



        setValuesForFields(checkInTime, checkOutTime, roomNumber);
        totalPrice.setText("350");

        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        daySpinner.setValueFactory(svf);

        ObjectProperty<LocalDate> startDateValue = startDate.valueProperty();
        startDateValue.addListener((((observableValue, oldLocalDate, newLocalDate) -> {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            endDate.setText(String.valueOf(LocalDate.parse(df.format(startDate.getValue())).plusDays(daySpinner.getValue())));
        })));

        ObjectProperty<Integer> dayValue = svf.valueProperty();
        dayValue.addListener(((observableValue, oldInteger, newInteger) -> {
            int price = 350;
            totalPrice.setText(String.valueOf(newInteger * price));

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            endDate.setText(String.valueOf(LocalDate.parse(df.format(startDate.getValue())).plusDays(newInteger)));
        }));

        daySpinner.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                keyEvent.consume();
            }
        });
        startDate.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                keyEvent.consume();
            }
        });

        endDate.setText(String.valueOf(startDate.getValue().plusDays(1)));

        roomNumberWarning.setVisible(false);
        warningName.setVisible(false);
        warningEmail.setVisible(false);
        warningIC.setVisible(false);
        warningPhone.setVisible(false);
        checkOutTimeWarning.setVisible(false);
        checkInTimeWarning.setVisible(false);
    }

    public boolean validateData() {
        boolean i = false;



        if (!customerName.getText().matches("^[a-zA-Z]{2,}$")) {
            warningName.setVisible(true);
            i = true;
        } else if (customerName.getText().length() >= 2) {
            boolean nameTaken = false;
            for (HotelBookings x : HotelBookingData.getInstance().getHotelBookings()) {
                if (x.getCustomerName().equals(customerName.getText())){
                    nameTaken = true;
                    break;
                }
            }
            if (nameTaken) {
                warningName.setText("Name is already taken");
                warningName.setVisible(true);
            } else {
                warningName.setVisible(false);
            }
        } else {
            warningName.setVisible(false);
        }
        if (!customerIC.getText().matches("\\d{12}$")) {
            warningIC.setVisible(true);
            i = true;
        } else {
            warningIC.setVisible(false);
        }
        if (!customerEmail.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            warningEmail.setVisible(true);
            i = true;
        } else {
            warningEmail.setVisible(false);
        }
        if (!customerPhone.getText().matches("^[0-9]{10}$") || customerPhone.getText().isEmpty() ) {
            warningPhone.setVisible(true);
            i = true;
        } else {
            warningPhone.setVisible(false);
        }
        if (checkInTime.getValue() == null) {
            checkInTimeWarning.setVisible(true);
            i = true;
        } else {
            checkInTimeWarning.setVisible(false);
        }
        if (checkOutTime.getValue() == null) {
            checkOutTimeWarning.setVisible(true);
            i = true;
        } else {
            checkOutTimeWarning.setVisible(false);
        }
        if (roomNumber.getValue() == null) {
            roomNumberWarning.setVisible(true);
            i = true;
        } else {
            roomNumberWarning.setVisible(false);
        }
        return i;
    }

    public void processData(Boolean checkedIn) {
        String status;
        if (checkedIn) {
            status = "Checked In";
        } else {
            status = "Not Checked In";
        }
        HotelBookings hotelBookings2 = new HotelBookings(roomNumber.getValue(), customerName.getText(), customerIC.getText(), customerEmail.getText(), customerPhone.getText(), bookingDate.getText(), String.valueOf(startDate.getValue()), endDate.getText(), checkInTime.getValue(), checkOutTime.getValue(), String.valueOf(daySpinner.getValue()), totalPrice.getText(), status, managedBy.getText());
        HotelBookingData.getInstance().addHotelBooking(hotelBookings2);
    }

    public void setValuesForFields(ComboBox<String> comboBox1, ComboBox<String> comboBox2, ComboBox<String> roomNumber) {
        ObservableList<String> timeList = FXCollections.observableArrayList();
        int i = 0;
        while (i != 24) {
            timeList.add(i + ":00");
            i++;
        }
        comboBox1.setItems(timeList);
        comboBox2.setItems(timeList);

        ObservableList<String> RoomList = FXCollections.observableArrayList();
        int z = 1;
        while (z <= 20) {
            if (z <= 10) {
                RoomList.add("A-0" + z);
            } else {
                RoomList.add("A-" + z);
            }
            z++;
        }
        roomNumber.setItems(RoomList);
    }

    public boolean checkDateValidation(boolean checkedIn) {
        boolean isClash = false;
        if (!checkedIn) {
            for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
                if (i.getRoomNumber().equals(roomNumber.getValue())) {
                    String[] bookingStartDate = i.getStartDate().split("-");
                    LocalDate existingStartDate = LocalDate.of(Integer.parseInt(bookingStartDate[0]), Integer.parseInt(bookingStartDate[1]), Integer.parseInt(bookingStartDate[2]));

                    String[] bookingEndDate = i.getEndDate().split("-");
                    LocalDate existingEndDate = LocalDate.of(Integer.parseInt(bookingEndDate[0]), Integer.parseInt(bookingEndDate[1]), Integer.parseInt(bookingEndDate[2]));

                    String[] newBookingEndDate = endDate.getText().split("-");
                    LocalDate newBookingEnDateValue = LocalDate.of(Integer.parseInt(newBookingEndDate[0]), Integer.parseInt(newBookingEndDate[1]), Integer.parseInt(newBookingEndDate[2]));
                    boolean doesOverlap = (startDate.getValue().isBefore(existingEndDate) || startDate.getValue().isEqual(existingEndDate))
                            && (newBookingEnDateValue.isAfter(existingStartDate) || newBookingEnDateValue.isEqual(existingStartDate));

                    if (doesOverlap) {
                        isClash = true;
                        break;
                    }
                }
            }
            if (isClash) {
                Alert warningDialog = new Alert(Alert.AlertType.WARNING);
                warningDialog.setTitle("Warning");
                warningDialog.setHeaderText("Date Already Taken");
                warningDialog.setContentText("The selected date is already taken by another booking. Please select another one.");

                warningDialog.showAndWait();
            }
        }
        return !isClash;
    }


}

