package com.example.javaassignment_yongsiongyue_tp065548;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogInPage {
    @FXML
    private TextField usernameLogIn;
    @FXML
    private PasswordField passwordLogIn;
    @FXML
    private Button logInButton;
    @FXML
    private Label logInError, logInError2;


    public void initialize() {
        logInError.setVisible(false);
        logInError2.setVisible(false);

        logInButton.setDisable(true);

        usernameLogIn.textProperty().addListener((observable, oldValue, newValue) -> checkLogInFields());
        passwordLogIn.textProperty().addListener(((observableValue, oldValue, newValue) -> checkLogInFields() ));
    }

    public void checkLogInFields() {
        logInButton.setDisable(usernameLogIn.getText().trim().isEmpty() || passwordLogIn.getText().trim().isEmpty());

        logInError.setVisible(false);
        logInError2.setVisible(false);
    }

    public void logIn() {
        String logInUsername = usernameLogIn.getText();
        String logInPassword = passwordLogIn.getText();

        Path adminPage = Paths.get("src/main/resources/TextFile-Datas/user-data.txt");
        try {
            BufferedReader logInReader = Files.newBufferedReader(adminPage);
            String input;
            boolean logInValidity = false;
            while ((input = logInReader.readLine()) != null) {
                String[] userData = input.split(",");

                if ((logInUsername.equals(userData[0])) && (logInPassword.equals(userData[1]))) {
                    try {
                        Session session = Session.getInstance();
                        System.out.println(session);
                        session.set("username", logInUsername);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
                        Parent root = loader.load();
                        MainFrame mainFrame = loader.getController();
                        mainFrame.currentUser.setText(session.get("username").toString());

                        Scene currentScene = logInButton.getScene();
                        Stage currentStage = (Stage) currentScene.getWindow();

                        Scene newScene = new Scene(root);
                        currentStage.setScene(newScene);

                        currentStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logInValidity = true;
                    break;
                }}
            if (!logInValidity) {
                logInError.setVisible(true);
                logInError2.setVisible(true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
