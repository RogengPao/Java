package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import Rooms_and_Booking.HotelRooms;
import Rooms_and_Booking.HotelRoomsData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ManageRooms {
    @FXML
    public Label detailNumber, detailType, detailOccupancy, detailBedType, detailAvailability, detailPrice,  floorNumber, warning;
    @FXML
    public TextField searhBar;
    @FXML
    private Button Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10;
    @FXML
    private Button checkIn, checkOut;

    public void initialize() {
        checkIn.setDisable(true);
        checkOut.setDisable(true);
        warning.setVisible(false);

        List<Button> roomButtons = Arrays.asList(Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10);

        loadRooms(roomButtons, 1);

        searhBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String searchQuery = searhBar.getText();
                loadRoomDetails(searchQuery);
                warning.setVisible(!detailNumber.getText().equals(searchQuery));
            }
        });
    }

    public void prevFloor() {
        List<Button> roomButtons = Arrays.asList(Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10);

        Integer floorLevel = Integer.parseInt(floorNumber.getText());
        if (floorLevel > 1) {
            floorLevel--;
            floorNumber.setText(String.valueOf(floorLevel));
            loadRooms(roomButtons, floorLevel);
        }
    }

    public void nextFloor() {
        List<Button> roomButtons = Arrays.asList(Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10);

        Integer floorLevel = Integer.parseInt(floorNumber.getText());
        int totalFloor = 2;
        if (floorLevel < totalFloor) {
            floorLevel++;
            floorNumber.setText(String.valueOf(floorLevel));
            loadRooms(roomButtons, floorLevel);
        }
    }

    public void loadRooms(List<Button> roomButtons, int floorNumber) {
        int x = (floorNumber == 1) ? 0 : 10;
        for (Button i: roomButtons) {
            i.setText(HotelRoomsData.getInstance().getHotelRooms().get(x).getRoomNumber());
            OccupancyChecker(i, HotelRoomsData.getInstance().getHotelRooms().get(x).getAvailability());
            x++;
        }
    }

    public void OccupancyChecker(Button roomName, String availability) {
        if (roomName != null && availability != null) {
            boolean hasBooking = false; // Flag to track if a matching booking is found
            LocalDate existingEndDate;
            for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
                if (roomName.getText().equals(i.getRoomNumber()) && availability.equals("Unavailable") && i.getStatus().equals("Checked In")) {
                    String[] bookingEndDate = i.getEndDate().split("-");
                    existingEndDate = LocalDate.of(Integer.parseInt(bookingEndDate[0]), Integer.parseInt(bookingEndDate[1]), Integer.parseInt(bookingEndDate[2]));
                    if (existingEndDate.isEqual(LocalDate.now())) {
                        System.out.println("hi");
                        roomName.setStyle("-fx-background-color: #c4787c;");
                    } else if (existingEndDate.minusDays(1).equals(LocalDate.now())) {
                        roomName.setStyle("-fx-background-color: #fdcb6f;");
                    } else {
                        roomName.setStyle("-fx-background-color: #02a2d3;");
                    }
                    hasBooking = true; // Set the flag to true since a matching booking is found
                    break; // Exit the loop as a matching booking is already found
                }
            }

            if (!hasBooking) {
                roomName.setStyle("-fx-background-color: #48b7ae;");
            }
        }
    }

    public void Room1Clicked(MouseEvent mouseEvent) {
        String buttonText = ((Button) mouseEvent.getSource()).getText();
        loadRoomDetails(buttonText);
    }

    public void loadRoomDetails(String roomNumber) {
        for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
            if (roomNumber.equals(i.getRoomNumber())) {
                detailNumber.setText(i.getRoomNumber());
                detailType.setText(i.getRoomTYpe());
                detailOccupancy.setText(i.getMaxOccupancy());
                detailAvailability.setText(i.getAvailability());
                detailBedType.setText(i.getBedType());
                detailPrice.setText(i.getPrice());
                if (i.getAvailability().equals("Available")) {
                    checkIn.setDisable(false);
                    checkOut.setDisable(true);
                } else {
                    checkIn.setDisable(true);
                    checkOut.setDisable(false);
                }
            }
        }
    }

    @FXML
    protected void checkIn(String title, Boolean disableDate, Boolean roomNumberSpecific, Boolean checkedIn) throws IOException {
//      --------------------------------------------Load File
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/illustration/roomy-no-background.png")).toExternalForm());
        stage.getIcons().add(icon);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckInDialog.fxml"));
        Parent root = fxmlLoader.load();
        dialog.getDialogPane().setContent(root);
//      --------------------------------------------Edit The Form Start Date
        CheckInDialog checkInDialog = fxmlLoader.getController();
        checkInDialog.startDate.setDisable(disableDate);
//      --------------------------------------------Display Room Number Clicked and set Disable
        if (roomNumberSpecific) {
            checkInDialog.roomNumber.setValue(detailNumber.getText());
            checkInDialog.roomNumber.setDisable(true);
        }
//      --------------------------------------------Dialog Box button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//      --------------------------------------------Check Validation and don't close if the validation fails
        Button finishButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
        finishButton.addEventFilter(ActionEvent.ACTION, event -> {
            CheckInDialog controller = fxmlLoader.getController();
            if(controller.validateData()) {
                event.consume();
            }
        });
//      --------------------------------------------Display Dialog Box
        Optional<ButtonType> result = dialog.showAndWait();
//      --------------------------------------------Execution after finish button is clicked
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.FINISH ) {

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
//      --------------------------------------------Edit Alert Title
            if (checkedIn) {
                confirmationDialog.setTitle("Confirm Check In");
                confirmationDialog.setContentText("Confirm Check In?");
            } else {
                confirmationDialog.setTitle("Confirm to Add Booking");
                confirmationDialog.setContentText("Confirm Booking?");
            }
//      --------------------------------------------Execution after alert button is clicked
            ButtonType confirmResult = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);
            if (confirmResult == ButtonType.OK) {
                CheckInDialog controller = fxmlLoader.getController();

                if (controller.checkDateValidation(checkedIn)) {
                    controller.processData(checkedIn);
                }

                if (checkedIn) {
                    for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
                        if (detailNumber.getText().equals(i.getRoomNumber())) {
                            i.setAvailability("Unavailable");
                            List<Button> roomButtons = Arrays.asList(Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10);
                            loadRooms(roomButtons, Integer.parseInt(floorNumber.getText()));
                        }
                    }
                    detailAvailability.setText("Unavailable");
                    checkIn.setDisable(true);
                    checkOut.setDisable(false);
                }
            }
        }
    }

    public void loadDialog() throws IOException {
        checkIn("Check In", true, true, true);
    }


    public void makeCheckOut() throws IOException {
        loadReceipt(detailNumber.getText(), detailType.getText(), true);
    }

    public void loadReceipt(String roomNumber, String roomType, Boolean loadRooms) throws IOException {
//      --------------------------------------------Load File
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Check Out");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/illustration/roomy-no-background.png")).toExternalForm());
        stage.getIcons().add(icon);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
        Parent root = fxmlLoader.load();
        dialog.getDialogPane().setContent(root);
//      --------------------------------------------Add Button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

//      --------------------------------------------Use Receipt Method
        Receipt receipt = fxmlLoader.getController();
        receipt.initialize(roomNumber, roomType);

//      --------------------------------------------Wait for user choice for code execution
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.FINISH ) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirm Check Out");
            confirmationDialog.setContentText("Confirm Check Out? (Payment have to be done)");


//      --------------------------------------------Wait for user choice for code execution
            ButtonType confirmResult = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);
            if (confirmResult == ButtonType.OK) {
                for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
                    if (i.getRoomNumber().equals(roomNumber) && i.getStatus().equals("Checked In")) {
                        i.setStatus("Checked Out");

                        int totalPrice = Integer.parseInt(i.getTotalPrice());
                        int totalFinalPrice = (int) Math.floor(totalPrice * 1.1);


                        Path path = Paths.get("src/main/resources/TextFile-Datas/transaction_history.txt");
                        BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

                        writer.write(i.getRoomNumber() + "," + i.getCustomerName() + "," +i.getCustomerIC() + "," + i.getTotalDays() + "," + totalFinalPrice + "," + LocalDate.now());
                        writer.newLine();
                        writer.close();
                    }
                }
                for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
                    if (i.getRoomNumber().equals(roomNumber)) {
                        i.setAvailability("Available");
                    }
                }

                if (loadRooms) {
                    checkOut.setDisable(true);
                    detailAvailability.setText("Available");
                    checkIn.setDisable(false);
                    List<Button> roomButtons = Arrays.asList(Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10);
                    loadRooms(roomButtons, Integer.parseInt(floorNumber.getText()));
                }

            }
        }
    }
}
