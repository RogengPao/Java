package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import Rooms_and_Booking.HotelRooms;
import Rooms_and_Booking.HotelRoomsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class ManageBookings {
    @FXML
    public ListView<HotelBookings> upcomingCheckedOut, upcomingCheckedIn;
    @FXML
    public Button addBooking;
    @FXML
    public ComboBox<String> roomNumber, checkInTime, checkOutTime;
    @FXML
    public TextField managedBy, customerName, customerIC, customerEmail, customerPhone, bookingDate, endDate, status, price, searchBar;
    @FXML
    public DatePicker startDate;
    @FXML
    public Button checkInBookings, deleteBookings, checkOutBookings, updateBookings;
    @FXML
    public Label warning;

    public void loadListViewData() {
//        Clear List
        upcomingCheckedOut.getItems().clear();
        upcomingCheckedIn.getItems().clear();
//        Set Selection Mode
        upcomingCheckedIn.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upcomingCheckedOut.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

//        Display Item in ListView
        upcomingCheckedOut.setItems(HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Checked In")));
        upcomingCheckedIn.setItems(HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Not Checked In")));

//        Display Item details
        displayBookingDetails(upcomingCheckedIn);
        displayBookingDetails(upcomingCheckedOut);
    }


    public void initialize() {
        CheckInDialog checkInDialog = new CheckInDialog();
        checkInDialog.setValuesForFields(checkInTime, checkOutTime, roomNumber);
        loadListViewData();

        searchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String searchQuery = searchBar.getText();
                boolean invalid = true;
                for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
                    if (i.getCustomerName().equals(searchQuery)) {
                        managedBy.setText(i.getManagedBy());
                        roomNumber.setValue(i.getRoomNumber());
                        customerName.setText(i.getCustomerName());
                        customerIC.setText(i.getCustomerIC());
                        customerEmail.setText(i.getCustomerEmail());
                        customerPhone.setText(i.getCustomerPhone());

                        bookingDate.setText(i.getBookingDate());

                        String[] bookingStartDate = i.getStartDate().split("-");
                        LocalDate selectedDate = LocalDate.of(Integer.parseInt(bookingStartDate[0]), Integer.parseInt(bookingStartDate[1]), Integer.parseInt(bookingStartDate[2]));
                        startDate.setValue(selectedDate);

                        endDate.setText(i.getEndDate());
                        checkInTime.setValue(i.getCheckInTime());
                        checkOutTime.setValue(i.getCheckOutTime());

                        status.setText(i.getStatus());
                        price.setText(i.getTotalPrice());
                        invalid = false;
                        warning.setVisible(invalid);
                    }
                }
                warning.setVisible(invalid);
            }
        });

        upcomingCheckedIn.setCellFactory(new Callback<ListView<HotelBookings>, ListCell<HotelBookings>>() {
            @Override
            public ListCell<HotelBookings> call(ListView<HotelBookings> param) {
                ListCell<HotelBookings> cell = new ListCell<HotelBookings>() {
                    @Override
                    protected void updateItem(HotelBookings item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toString());

                            String[] bookingStartDate = item.getStartDate().split("-");
                            LocalDate selectedDate = LocalDate.of(Integer.parseInt(bookingStartDate[0]), Integer.parseInt(bookingStartDate[1]), Integer.parseInt(bookingStartDate[2]));

                            if (selectedDate.isEqual(LocalDate.now())) {
                                setTextFill(Color.RED);
                            } else if (LocalDate.now().isEqual(selectedDate.minusDays(1))) {
                                setTextFill(Color.ORANGE);
                            }
                        }
                    }
                };
                return cell;
            }
        });

        upcomingCheckedOut.setCellFactory(new Callback<ListView<HotelBookings>, ListCell<HotelBookings>>() {
            @Override
            public ListCell<HotelBookings> call(ListView<HotelBookings> param) {
                ListCell<HotelBookings> cell = new ListCell<HotelBookings>() {
                    @Override
                    protected void updateItem(HotelBookings item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toString());

                            String[] bookingEndDate = item.getEndDate().split("-");
                            LocalDate selectedDate = LocalDate.of(Integer.parseInt(bookingEndDate[0]), Integer.parseInt(bookingEndDate[1]), Integer.parseInt(bookingEndDate[2]));

                            if (selectedDate.isEqual(LocalDate.now())) {
                                setTextFill(Color.RED);
                            } else if (LocalDate.now().isEqual(selectedDate.minusDays(1))) {
                                setTextFill(Color.ORANGE);
                            }
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void addBooking() throws IOException {
        ManageRooms manageRooms = new ManageRooms();
        manageRooms.checkIn("Add Booking",false, false, false);
    }

    public void displayBookingDetails(ListView<HotelBookings> list) {
        list.getSelectionModel().selectedItemProperty().addListener((observableValue, oldhotelBookings, newhotelBookings) -> {
            warning.setVisible(false);
            HotelBookings hotelBookingsItem = list.getSelectionModel().getSelectedItem();
            if (oldhotelBookings == newhotelBookings) {
                System.out.println("Same");
            }
            if (hotelBookingsItem != null) {
                managedBy.setText(hotelBookingsItem.getManagedBy());
                roomNumber.setValue(hotelBookingsItem.getRoomNumber());
                customerName.setText(hotelBookingsItem.getCustomerName());
                customerIC.setText(hotelBookingsItem.getCustomerIC());
                customerEmail.setText(hotelBookingsItem.getCustomerEmail());
                customerPhone.setText(hotelBookingsItem.getCustomerPhone());

                bookingDate.setText(hotelBookingsItem.getBookingDate());

                String[] bookingStartDate = hotelBookingsItem.getStartDate().split("-");
                LocalDate selectedDate = LocalDate.of(Integer.parseInt(bookingStartDate[0]), Integer.parseInt(bookingStartDate[1]), Integer.parseInt(bookingStartDate[2]));
                startDate.setValue(selectedDate);

                endDate.setText(hotelBookingsItem.getEndDate());
                checkInTime.setValue(hotelBookingsItem.getCheckInTime());
                checkOutTime.setValue(hotelBookingsItem.getCheckOutTime());

                status.setText(hotelBookingsItem.getStatus());
                price.setText(hotelBookingsItem.getTotalPrice());


                if (hotelBookingsItem.getStatus().equals("Checked In")) {
                    checkInBookings.setDisable(true);
                    deleteBookings.setDisable(true);
                    checkOutBookings.setDisable(false);
                    updateBookings.setDisable(false);
                } else {
                    checkInBookings.setDisable(false);
                    deleteBookings.setDisable(false);
                    checkOutBookings.setDisable(true);
                    updateBookings.setDisable(false);
                }
            }
        });

    }

    public void bookingCheckIn() throws IOException {
        boolean availability = true;
        for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
            if ((i.getRoomNumber().equals(roomNumber.getValue())) && (i.getAvailability().equals("Unavailable"))) {
                availability = false;
                break;
            }
        }
        if (!availability) {
            Alert warningDialog = new Alert(Alert.AlertType.WARNING);
            warningDialog.setTitle("Check In Unavailable");
            warningDialog.setContentText("Room is currently unavailable. Please check out the previous customer first!");
            warningDialog.showAndWait();
        }

        if (availability) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirm Check In?");
            confirmationDialog.setContentText("Confirm Checked In?");

            ButtonType confirmResult = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);
            if (confirmResult == ButtonType.OK) {
                for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
                    if (i.getRoomNumber().equals(roomNumber.getValue())) {
                        i.setAvailability("Unavailable");
                    }
                }
                for (HotelBookings x : HotelBookingData.getInstance().getHotelBookings()) {
                    if (x.getCustomerName().equals(customerName.getText()) && x.getStatus().equals("Not Checked In")) {
                        x.setStatus("Checked In");
                        ObservableList<HotelBookings> filteredCheckedOut = FXCollections.observableArrayList(
                                HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Checked In")));
                        ObservableList<HotelBookings> filteredCheckedIn = FXCollections.observableArrayList(
                                HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Not Checked In")));

                        upcomingCheckedOut.setItems(filteredCheckedOut);
                        upcomingCheckedIn.setItems(filteredCheckedIn);
                    }
                }

            }
        }
    }

    public void bookingCheckOut() throws IOException {
        String roomType = null;
        for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
            if (i.getRoomNumber().equals(roomNumber.getValue())) {
                roomType = i.getRoomTYpe();
            }
        }
        ManageRooms manageRooms = new ManageRooms();
        manageRooms.loadReceipt(roomNumber.getValue(), roomType, false);
        ObservableList<HotelBookings> filteredCheckedOut = FXCollections.observableArrayList(
                HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Checked In")));
        ObservableList<HotelBookings> filteredCheckedIn = FXCollections.observableArrayList(
                HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Not Checked In")));
        upcomingCheckedOut.setItems(filteredCheckedOut);
        upcomingCheckedIn.setItems(filteredCheckedIn);
    }


    public void bookingsUpdate() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Booking Details");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/illustration/roomy-no-background.png")).toExternalForm());
        stage.getIcons().add(icon);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Update.fxml"));
        Parent root = fxmlLoader.load();
        dialog.getDialogPane().setContent(root);

        Update controller = fxmlLoader.getController();
        controller.initialize(customerName.getText(), customerIC.getText(),customerEmail.getText(), customerPhone.getText(), bookingDate.getText(), status.getText(), "Managed By: " + managedBy.getText(), roomNumber.getValue(), startDate.getValue(), checkInTime.getValue(), checkOutTime.getValue(), endDate.getText());


        dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.FINISH ) {
            controller.processUpdate();
        }
    }

    public void bookingDelete() throws IOException {
        Alert warningDialog = new Alert(Alert.AlertType.WARNING);
        warningDialog.setTitle("Warning");
        warningDialog.setHeaderText("Delete Data?");
        warningDialog.setContentText("Are you sure if you want to delete this booking?");

        Optional<ButtonType> result = warningDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
                if (i.getRoomNumber().equals(roomNumber.getValue()) && i.getCustomerIC().equals(customerIC.getText())) {
                    HotelBookingData.getInstance().getHotelBookings().remove(i);
                    break;
                }
            }
            ObservableList<HotelBookings> filteredCheckedOut = FXCollections.observableArrayList(
                    HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Checked In")));
            ObservableList<HotelBookings> filteredCheckedIn = FXCollections.observableArrayList(
                    HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Not Checked In")));

            upcomingCheckedOut.setItems(filteredCheckedOut);
            upcomingCheckedIn.setItems(filteredCheckedIn);
        }
    }
}
