package com.studentmanager.studentmanagementgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddStudentController {

    @FXML private TextField idField;
    @FXML private TextField nameField;

    // Now hooked to the ComboBoxes from FXML
    @FXML private ComboBox<String> programComboBox;
    @FXML private ComboBox<String> yearComboBox;

    @FXML private Label statusLabel;

    private StudentManager engine;

    @FXML
    public void initialize() {
        engine = new StudentManager();

        // Populate the Program Dropdown
        programComboBox.getItems().addAll(
                "Bachelor of Technology and Livelihood Education major in Home Economics (BTLEd-HE)",
                "Bachelor of Science in Accountancy (BSA)",
                "Bachelor of Science in Management Accounting (BSMA)",
                "Bachelor of Science in Business Administration – major in Human Resource Management (BSBA-HRM)",
                "Bachelor of Science in Business Administration – major in Marketing Management (BSBA-MM)",
                "Bachelor of Science in Business Administration – major in Financial Management (BSBA-FM)",
                "Bachelor of Science in Electronics Engineering (BSECE)",
                "Bachelor of Science in Industrial Engineering (BSIE)",
                "Bachelor of Science in Information Technology (BSIT)",
                "Bachelor of Science in Psychology (BSPsych)",
                "Bachelor of Science in Education – major in English (BSEd-English)",
                "Bachelor of Science in Education – major in Filipino (BSEd-Filipino)",
                "Bachelor of Science in Education – major in Mathematics (BSEd-Math)"
        );

        // Populate the Year Dropdown
        yearComboBox.getItems().addAll("1st Year", "2nd Year", "3rd Year", "4th Year");
    }

    @FXML
    protected void onSubmitClick(ActionEvent event) {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            String selectedProgram = programComboBox.getValue();
            String selectedYear = yearComboBox.getValue();

            // Safety check: Make sure nothing is left blank
            if (id.isEmpty() || name.isEmpty() || selectedProgram == null || selectedYear == null) {
                statusLabel.setText("Error: All fields and dropdowns must be filled.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Extract the acronym from the program (e.g., gets "BSIT" from the parentheses)
            String programAcronym = selectedProgram.substring(selectedProgram.lastIndexOf("(") + 1, selectedProgram.lastIndexOf(")"));

            // Extract the number from the year string (e.g., gets 1 from "1st Year")
            int year = Integer.parseInt(selectedYear.substring(0, 1));

            // Save to Supabase and check if it worked
            Student newStudent = new Student(id, name, programAcronym, year);
            boolean isSaved = engine.addStudent(newStudent);

            if (isSaved) {
                returnToDashboard(event);
            } else {
                // The exact requested error message for duplicate IDs
                statusLabel.setText("Database Error: Student ID already exists.");
                statusLabel.setStyle("-fx-text-fill: #800000; -fx-font-weight: bold;");
            }

        } catch (Exception e) {
            statusLabel.setText("System Error: Please check your inputs.");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCancelClick(ActionEvent event) {
        returnToDashboard(event);
    }

    private void returnToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("PUP Student Management Portal");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}