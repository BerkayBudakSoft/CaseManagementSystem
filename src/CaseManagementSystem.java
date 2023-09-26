import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * this JavaFX application serves as a case management system, allowing users to record and manage legal cases efficiently.
 */
public class CaseManagementSystem extends Application {
    private TextField caseNumberTextField, plaintiffTextField, defendantsTextField, courtTextField, ticketNumberTextField;
    private ComboBox<String> caseTypeComboBox, lawyerComboBox, timeComboBox;
    private ListView<String> caseListView;
    private TextArea detailsTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Case Management Application");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // labels and TextFields for Case Information
        Label caseNumberLabel = new Label("Case Number:");
        GridPane.setConstraints(caseNumberLabel, 0, 0);
        caseNumberTextField = new TextField();
        GridPane.setConstraints(caseNumberTextField, 1, 0);

        Label plaintiffLabel = new Label("Plaintiff:");
        GridPane.setConstraints(plaintiffLabel, 0, 1);
        plaintiffTextField = new TextField();
        GridPane.setConstraints(plaintiffTextField, 1, 1);

        Label defendantsLabel = new Label("Defendants:");
        GridPane.setConstraints(defendantsLabel, 0, 2);
        defendantsTextField = new TextField();
        GridPane.setConstraints(defendantsTextField, 1, 2);

        Label courtLabel = new Label("Court:");
        GridPane.setConstraints(courtLabel, 0, 3);
        courtTextField = new TextField();
        GridPane.setConstraints(courtTextField, 1, 3);

        // comboBox for Case Type
        Label caseTypeLabel = new Label("Case Type:");
        GridPane.setConstraints(caseTypeLabel, 0, 4);
        caseTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Criminal Case",
                "Divorce Case",
                "Compensation Case",
                "Employment Case",
                "Real Estate Case",
                "Inheritance Case",
                "Execution and Bankruptcy Case",
                "Intellectual Property Case"
        ));
        caseTypeComboBox.setPromptText("Select Case Type");
        GridPane.setConstraints(caseTypeComboBox, 1, 4);

        // comboBox for Lawyers
        Label lawyerLabel = new Label("Lawyer:");
        GridPane.setConstraints(lawyerLabel, 0, 5);
        lawyerComboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Criminal Defense Lawyer",
                "Employment Lawyer",
                "Real Estate Lawyer",
                "Commercial Lawyer",
                "Execution and Bankruptcy Lawyer",
                "Intellectual Property Lawyer",
                "Family Lawyer",
                "Administrative Lawyer"
        ));
        lawyerComboBox.setPromptText("Select Lawyer");
        GridPane.setConstraints(lawyerComboBox, 1, 5);

        // comboBox for Time Slots
        Label timeLabel = new Label("Time:");
        GridPane.setConstraints(timeLabel, 0, 6);
        timeComboBox = new ComboBox<>(FXCollections.observableArrayList(generateTimeSlots()));
        timeComboBox.setPromptText("Select Time");
        GridPane.setConstraints(timeComboBox, 1, 6);

        // textField for Ticket Number
        Label ticketNumberLabel = new Label("Ticket Number:");
        GridPane.setConstraints(ticketNumberLabel, 0, 7);
        ticketNumberTextField = new TextField();
        GridPane.setConstraints(ticketNumberTextField, 1, 7);

        // add, delete  and clear Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String caseNumber = caseNumberTextField.getText();
            String plaintiff = plaintiffTextField.getText();
            String defendants = defendantsTextField.getText();
            String court = courtTextField.getText();
            String caseType = caseTypeComboBox.getValue();
            String lawyer = lawyerComboBox.getValue();
            String time = timeComboBox.getValue();
            String ticketNumber = ticketNumberTextField.getText();

            CaseObject caseObject = new CaseObject(caseNumber, plaintiff, defendants, court, caseType, lawyer, time, ticketNumber);
            caseListView.getItems().add(caseObject.toString());

            clearFields();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String selectedCase = caseListView.getSelectionModel().getSelectedItem();
            if (selectedCase != null) {
                caseListView.getItems().remove(selectedCase);
            }
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearFields());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton, deleteButton, clearButton);
        GridPane.setConstraints(buttonBox, 1, 8);

        // add UI elements to the gridPane
        gridPane.getChildren().addAll(
                caseNumberLabel, caseNumberTextField,
                plaintiffLabel, plaintiffTextField,
                defendantsLabel, defendantsTextField,
                courtLabel, courtTextField,
                caseTypeLabel, caseTypeComboBox,
                lawyerLabel, lawyerComboBox,
                timeLabel, timeComboBox,
                ticketNumberLabel, ticketNumberTextField,
                buttonBox
        );

        // listview for cases and details textarea
        caseListView = new ListView<>();
        detailsTextArea = new TextArea();
        detailsTextArea.setPrefHeight(100);

        // show case details when an item is selected in the ListView
        caseListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            detailsTextArea.setText(newValue);
        });

        // create a vbox to display the listview and textarea
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(caseListView, detailsTextArea);

        GridPane.setConstraints(vBox, 2, 0, 1, 9);

        gridPane.getChildren().add(vBox);

        Scene scene = new Scene(gridPane, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // method to clear all input fields
    private void clearFields() {
        caseNumberTextField.clear();
        plaintiffTextField.clear();
        defendantsTextField.clear();
        courtTextField.clear();
        caseTypeComboBox.getSelectionModel().clearSelection();
        lawyerComboBox.getSelectionModel().clearSelection();
        timeComboBox.getSelectionModel().clearSelection();
        ticketNumberTextField.clear();
    }

    // method to generate time slots for the ComboBox
    private List<String> generateTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(9, 0);

        for (int i = 0; i < 10; i++) {
            timeSlots.add(time.toString());
            time = time.plusMinutes(30);
        }

        return timeSlots;
    }

    // nested class to represent a legal case
    private static class CaseObject {
        private String caseNumber;
        private String plaintiff;
        private String defendants;
        private String court;
        private String caseType;
        private String lawyer;
        private String time;
        private String ticketNumber;

        public CaseObject(String caseNumber, String plaintiff, String defendants, String court, String caseType, String lawyer, String time, String ticketNumber) {
            this.caseNumber = caseNumber;
            this.plaintiff = plaintiff;
            this.defendants = defendants;
            this.court = court;
            this.caseType = caseType;
            this.lawyer = lawyer;
            this.time = time;
            this.ticketNumber = ticketNumber;
        }

        @Override
        public String toString() {
            return "Case Number: " + caseNumber + "\n"
                    + "Plaintiff: " + plaintiff + "\n"
                    + "Defendants: " + defendants + "\n"
                    + "Court: " + court + "\n"
                    + "Case Type: " + caseType + "\n"
                    + "Lawyer: " + lawyer + "\n"
                    + "Time: " + time + "\n"
                    + "Ticket Number: " + ticketNumber;
        }
    }
}
