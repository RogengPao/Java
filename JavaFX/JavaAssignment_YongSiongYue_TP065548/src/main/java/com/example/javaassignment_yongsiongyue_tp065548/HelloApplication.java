package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelRoomsData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private static final int DELAY_SECOND = 3;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Loading-Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);

//      ICON SETTER
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/illustration/roomy-no-background.png")).toExternalForm());
        stage.getIcons().add(icon);

        stage.setTitle("ROOMY");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(DELAY_SECOND), event -> {
            try {
                Parent loginPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Log-In-Page.fxml")));
                Scene loginScene = new Scene(loginPage);
                stage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.play();

        HotelRoomsData.getInstance().loadHotelRoomsData();
        HotelBookingData.getInstance().loadHotelBookingsData();
    }





    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        try {
            HotelBookingData.getInstance().storeHotelBooking();
            HotelRoomsData.getInstance().storeHotelRoomData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}