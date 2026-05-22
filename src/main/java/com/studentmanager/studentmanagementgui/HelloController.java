package com.studentmanager.studentmanagementgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML private Label statusLabel;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colProgram;
    @FXML private TableColumn<Student, Integer> colYear;

    private StudentManager engine;

    @FXML
    public void initialize() {
        // Link the columns to the Student object
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));

        // Start the engine and fill the table
        engine = new StudentManager();
        refreshTable();
    }

    @FXML
    protected void onRefreshClick() {
        refreshTable();
        statusLabel.setText("System: Cloud data refreshed.");
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML
    protected void onOpenRegistrationClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-student-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Open the form in a slightly smaller window
            Scene scene = new Scene(root, 600, 500);
            stage.setTitle("Register New Student");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("System Error: Could not load registration form.");
        }
    }

    // THIS IS NEW: Handles the Sign Out process
    @FXML
    protected void onSignOutClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Shrink the window back to the compact login size
            Scene scene = new Scene(root, 400, 450);
            stage.setTitle("PUP IT Portal - Login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("System Error: Could not sign out.");
        }
    }

    private void refreshTable() {
        List<Student> list = engine.getAllStudentsForGUI();
        ObservableList<Student> guiData = FXCollections.observableArrayList(list);
        studentTable.setItems(guiData);
    }
}