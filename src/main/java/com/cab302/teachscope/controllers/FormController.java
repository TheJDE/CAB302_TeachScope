package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.services.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;

import com.cab302.teachscope.util.NavigationUtils;

public class FormController {

    private final FormService formService = new FormService(new DbFormDao());

    private String studentId;

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @FXML private Button logoutButton, newFormButton, generatePDF, createFormButton;

    @FXML private ComboBox<String> term;
    @FXML private ComboBox<String> week;
    @FXML private ComboBox<String> attendancedays;
    @FXML private ComboBox<String> dayslate;
    @FXML private ComboBox<String> attention;
    @FXML private ComboBox<String> participation;
    @FXML private ComboBox<String> literacy;
    @FXML private ComboBox<String> numeracy;
    @FXML private ComboBox<String> understanding;
    @FXML private ComboBox<String> behaviour;
    @FXML private ComboBox<String> peerInteraction;
    @FXML private ComboBox<String> respectRules;

    @FXML private TextArea concernsText;

    @FXML private RadioButton homeworkNo, homeworkYes;
    @FXML private RadioButton happyRadio, neutralRadio, withdrawnRadio, anxiousRadio;
    @FXML private ToggleGroup homeworkGroup, emotionalGroup;

    @FXML
    protected void onLogoutClick() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "login", "Login");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the login page.");
        }
    }

    @FXML
    protected void newFormClick() {
        if (studentId == null) {
            showAlert("Error", "No student selected.");
            return;
        }

        Stage stage = (Stage) newFormButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addnewform.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setStudentId(this.studentId);

            stage.setScene(new Scene(root));
            stage.setTitle("Add New Form");
            stage.show();

        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the add new form page.");
        }
    }



    @FXML
    protected void generatePDFClick() {
        Stage stage = (Stage) generatePDF.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "generatepdf", "Generate PDF");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the generate PDF page.");
        }
    }

    @FXML
    protected void onSubmitFormClick() {
        try {
            if (studentId == null) {
                showAlert("Form Submission Error", "No student selected.");
                return;
            }

            int termVal = term.getSelectionModel().getSelectedIndex() + 1;
            int weekVal = week.getSelectionModel().getSelectedIndex() + 1;
            int attendance = Integer.parseInt(attendancedays.getValue());
            int late = Integer.parseInt(dayslate.getValue());
            int attentionVal = attention.getSelectionModel().getSelectedIndex();
            boolean homeworkDone = homeworkYes.isSelected();
            int participationVal = participation.getSelectionModel().getSelectedIndex();
            int literacyVal = literacy.getSelectionModel().getSelectedIndex();
            int numeracyVal = numeracy.getSelectionModel().getSelectedIndex();
            int understandingVal = understanding.getSelectionModel().getSelectedIndex();
            int behaviourVal = behaviour.getSelectionModel().getSelectedIndex();
            int peerVal = peerInteraction.getSelectionModel().getSelectedIndex();
            int respectVal = respectRules.getSelectionModel().getSelectedIndex();

            String emotionalState;
            if (happyRadio.isSelected()) emotionalState = "Happy";
            else if (neutralRadio.isSelected()) emotionalState = "Neutral";
            else if (withdrawnRadio.isSelected()) emotionalState = "Withdrawn";
            else emotionalState = "Anxious";

            String teacherConcerns = concernsText.getText();

            formService.createForm(
                    studentId,
                    termVal,
                    weekVal,
                    attendance,
                    late,
                    attentionVal,
                    homeworkDone,
                    participationVal,
                    literacyVal,
                    numeracyVal,
                    understandingVal,
                    behaviourVal,
                    peerVal,
                    respectVal,
                    emotionalState,
                    teacherConcerns
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Form submitted successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            showAlert("Form Submission Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
