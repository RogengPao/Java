package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainFrame {
    @FXML
    public Label currentUser;
    @FXML
    public LineChart<String, Number> occupancyLineChart;
    @FXML
    public PieChart floor1Chart, floor2Chart, toatalFloorChart;
    @FXML
    public ListView<HotelBookings> listViewCheckOut;
    @FXML
    private Button logOut, profileButton, settingButton;
    @FXML
    private BorderPane bp;

    public void initialize() {
        profileButton.setDisable(true);
        settingButton.setDisable(true);

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @FXML
    public void home() {loadPage("Dashboard"); }

    @FXML
    public void manageRooms() {
        loadPage("manageRooms");
    }

    @FXML
    public void manageBookings() {
        loadPage("manageBookings");
    }

    @FXML
    public void checkAvailability() {
        loadPage("CheckAvailability");
    }

    @FXML
    public void Setting() {
        loadPage("Setting");
    }

    @FXML
    public void profile() {
        loadPage("Profile");
    }

    public void logOut() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Log Out");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/illustration/roomy-no-background.png")).toExternalForm());
        stage.getIcons().add(icon);

        DialogPane dialogPane = new DialogPane();
        dialog.setDialogPane(dialogPane);

        dialogPane.setContentText("Are you sure you want to log out?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        dialogPane.getButtonTypes().addAll(yesButton,noButton);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Log-In-Page.fxml")));
                Parent root = loader.load();

                Scene currentScene = logOut.getScene();
                Stage currentStage = (Stage) currentScene.getWindow();

                Scene newScene = new Scene(root);
                currentStage.setScene(newScene);
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }
}
