package com.studentmanager.studentmanagementgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    // 1. Hooking into the Login FXML Visuals
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // 2. What happens when you click "Secure Login"
    @FXML
    protected void onLoginClick(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // Check the credentials
        if (user.equals("admin") && pass.equals("pupbsit")) {
            try {
                // SUCCESS: Grab the Dashboard blueprint (currently named hello-view.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();

                // Find the physical window (Stage) that the user is currently looking at
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Swap the old login scene for the new spacious Dashboard scene
                Scene scene = new Scene(root, 800, 600);
                stage.setTitle("Student Management Dashboard");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("System Error: Could not load dashboard.");
            }
        } else {
            // FAILED: Show red error text
            errorLabel.setText("Invalid username or password.");
        }
    }
}