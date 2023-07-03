package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import Rooms_and_Booking.HotelRooms;
import Rooms_and_Booking.HotelRoomsData;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;

public class CheckAvailability {
    @FXML
    private ListView<HotelRooms> availabilityList;
    @FXML
    private DatePicker startDate, endDate;
    @FXML
    private ComboBox<String> roomType;
    @FXML
    private VBox bookingDataList;
    @FXML
    private Label searchWarning;
    @FXML
    private Spinner<Integer> daySpinner;
    public void initialize() {
        ObservableList<String> roomTypes = FXCollections.observableArrayList("Standard", "Suite", "Deluxe");
        roomType.setItems(roomTypes);

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


        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        daySpinner.setValueFactory(svf);

        endDate.setValue(LocalDate.now().plusDays(daySpinner.getValue()));

        ObjectProperty<LocalDate> startDateValue = startDate.valueProperty();
        startDateValue.addListener((((observableValue, oldLocalDate, newLocalDate) -> endDate.setValue(startDate.getValue().plusDays(daySpinner.getValue())))));

        ObjectProperty<Integer> dayValue = svf.valueProperty();
        dayValue.addListener(((observableValue, oldInteger, newInteger) -> endDate.setValue(startDate.getValue().plusDays(daySpinner.getValue()))));

        availabilityList.setItems(HotelRoomsData.getInstance().getHotelRooms());

        availabilityList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldhotelBookings, newhotelBookings) -> {
            bookingDataList.getChildren().clear();

            try {
                HotelBookingData.getInstance().loadHotelBookingsData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            HotelRooms hotelRooms = availabilityList.getSelectionModel().getSelectedItem();

            if (hotelRooms != null) {
                searchWarning.setVisible(false);
                if (hotelRooms.getRoomNumber() != null && hotelRooms.getRoomNumber() != null) {
                    for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
                        if (i.getRoomNumber().equals(hotelRooms.getRoomNumber())) {
                            VBox bookingBox = new VBox();
                            bookingBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
                            bookingBox.setPrefWidth(467.0);

                            Label dateLabel = new Label(i.getStartDate() + " to " + i.getEndDate());
                            dateLabel.setFont(Font.font("Arial Rounded MT Bold", 16.0));

                            Label customerName = new Label("By " + i.getCustomerName());
                            customerName.setFont(Font.font("Arial", 12.0));

                            HBox statusBox = new HBox();
                            statusBox.setAlignment(Pos.CENTER_LEFT);

                            Label statusLabel = new Label("Status:");
                            Label statusValue = new Label(i.getStatus());

                            HBox totalDaysBox = new HBox();
                            totalDaysBox.setAlignment(Pos.CENTER_LEFT);

                            Label totalDays = new Label("Total Days:");
                            Label totalDaysValue = new Label(i.getTotalDays());

                            HBox checkInTimeBox = new HBox();
                            checkInTimeBox.setAlignment(Pos.CENTER_LEFT);

                            Label checkInTime = new Label("Check In Time:");
                            Label checkInTimeValue = new Label(i.getCheckInTime());

                            HBox checkOutTimeBox = new HBox();
                            checkOutTimeBox.setAlignment(Pos.CENTER_LEFT);

                            Label checkOutTime = new Label("Check Out Time:");
                            Label checkOutTimeValue = new Label(i.getCheckOutTime());

                            statusBox.getChildren().addAll(statusLabel, statusValue);
                            totalDaysBox.getChildren().addAll(totalDays, totalDaysValue);
                            checkInTimeBox.getChildren().addAll(checkInTime, checkInTimeValue);
                            checkOutTimeBox.getChildren().addAll(checkOutTime, checkOutTimeValue);

                            bookingBox.getChildren().addAll(dateLabel, customerName, statusBox, totalDaysBox, checkInTimeBox, checkOutTimeBox);
                            bookingBox.setPadding(new Insets(5.0,0,10.0,10.0));
                            bookingBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 0 1 0;");

                            bookingDataList.getChildren().add(bookingBox);
                        }
                    }
                }
            }
        });
    }

    public void searchAvailableRooms() {
        ObservableList<HotelRooms> roomListFiltered = FXCollections.observableArrayList();
        ObservableList<HotelRooms> availableRooms = FXCollections.observableArrayList();


        if (roomType.getValue() != null) {
            for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
                if (roomType.getValue().equals(i.getRoomTYpe())) {
                    roomListFiltered.add(i);
                }
            }
            for (HotelRooms i : roomListFiltered) {
                boolean isClash = false;

                for (HotelBookings x : HotelBookingData.getInstance().getHotelBookings()) {
                    if (i.getRoomNumber().equals(x.getRoomNumber())) {
                        String[] bookingStartDate = x.getStartDate().split("-");
                        LocalDate existingStartDate = LocalDate.of(Integer.parseInt(bookingStartDate[0]), Integer.parseInt(bookingStartDate[1]), Integer.parseInt(bookingStartDate[2]));

                        String[] bookingEndDate = x.getEndDate().split("-");
                        LocalDate existingEndDate = LocalDate.of(Integer.parseInt(bookingEndDate[0]), Integer.parseInt(bookingEndDate[1]), Integer.parseInt(bookingEndDate[2]));

                        boolean doesOverlap = (startDate.getValue().isBefore(existingEndDate) || startDate.getValue().isEqual(existingEndDate))
                                && (endDate.getValue().isAfter(existingStartDate) || endDate.getValue().isEqual(existingStartDate));

                        if (doesOverlap) {
                            isClash = true;
                            break; // No need to check further bookings for this room
                        }
                    }
                }
                if (!isClash) { availableRooms.add(i);}
        }
        availabilityList.setItems(availableRooms);
        } else {
            searchWarning.setVisible(true);
        }
    }
}



