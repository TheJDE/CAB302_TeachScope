package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.dao.DbStudentDao;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.services.GenerateReportsService;
import com.cab302.teachscope.util.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GeneratePDFController {

    @FXML
    private ComboBox<String> term;

    @FXML
    private ComboBox<String> fromWeek;

    @FXML
    private ComboBox<String> toWeek;

    @FXML
    private Label PDFTitle;

    @FXML
    private Button logoutButton;

    @FXML
    private Button studentNav;

    @FXML
    private Button timelineButton, reportButton;

    @FXML
    private Button weeklyformsButton;

    private String studentId;
    private String studentName;

    private final GenerateReportsService reportsService;

    /**
     * Navigates back to the Weekly Forms page for the current student.
     * Loads the weekly forms view and passes the student context to the new controller.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    protected void weeklyFormsClick() throws IOException {
        Stage stage = (Stage) weeklyformsButton.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/weeklyforms.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setStudent(studentId, studentName);

            stage.setScene(new Scene(root));
            stage.setTitle("View Weekly Forms");
            stage.show();

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    /**
     * Handles navigation to the knowledge base PDF document.
     */
    @FXML
    protected void onKnowledgeBaseClick() {
        NavigationUtils.openPDF("/src/main/resources/images/user_introductory_tutorial.pdf");
    }

    /**
     * Handles navigation to the introductory tutorial PDF document.
     */
    @FXML
    protected void onIntroductoryTutorialClick() {
        NavigationUtils.openPDF("/src/main/resources/images/user_introductory_tutorial.pdf");
    }

    /**
     * Handles user logout action.
     * Navigates the user back to the login page and clears the current session.
     */
    @FXML
    protected void onLogoutClick() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "login", "Login");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open login page.");
        }
    }

    /**
     * Handles navigation to the student dashboard.
     * Returns the user to the main dashboard view.
     */
    @FXML
    protected void onStudentClick() {
        Stage stage = (Stage) studentNav.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open dashboard.");
        }
    }

    /**
     * Handles navigation to the PDF generation page for all students.
     */
    @FXML
    protected void onGeneratePDFAllStudentsClick() {
        Stage stage = (Stage) reportButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "allstudentsgeneratepdf", "Generate PDF for All Students");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open Class Report Page.");
        }
    }

    /**
     * Handles navigation to the timeline view.
     * Opens the timeline page showing form completion status across all students.
     */
    @FXML
    protected void onTimelineClick() {
        Stage stage = (Stage) timelineButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "timeline", "Timeline");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open timeline.");
        }
    }


    /**
     * Constructs a new GeneratePDFController.
     * Initializes the reports service with required data access objects.
     */
    public GeneratePDFController() {
        this.reportsService = new GenerateReportsService(new DbFormDao(), new DbStudentDao());
    }


    /**
     * Sets the selected student for generating reports.
     * Updates the PDF title to display the student's name.
     *
     * @param studentId   The unique identifier of the student
     * @param studentName The full name of the student
     */
    public void setStudent(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;

        // Update the PDF title if the FXML element is loaded
        if (PDFTitle != null) {
            PDFTitle.setText("Generate PDF for " + studentName);
        }
    }

    /**
     * Called when "Generate Report" is clicked.
     */
    @FXML
    protected void onGenerateReportClick() {
        if (studentId == null) {
            showAlert("No student selected", "Please select a student first.");
            return;
        }


        String termValue = term.getValue();
        String fromValue = fromWeek.getValue();
        String toValue = toWeek.getValue();

        if (termValue == null || fromValue == null || toValue == null) {
            showAlert("Missing Selection", "Please select a term and week range.");
            return;
        }

        int termNumber = parseTermNumber(termValue);
        int from = parseWeekNumber(fromValue);
        int to = parseWeekNumber(toValue);

        if (from > to) {
            showAlert("Invalid Range", "'From Week' cannot be after 'To Week'.");
            return;
        }

        String userHome = System.getProperty("user.home");

        File pdfDir = new File(userHome, "/Documents/pdfs");

        try {
            reportsService.createReport(studentId, termNumber, from, to);
            showAlert("Success", "PDF report generated at " + pdfDir);
            Desktop.getDesktop().open(pdfDir);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error Generating Report", e.getMessage());
        }
    }

    /**
     * Handles the generation of PDF reports for all students in the class.
     * Validates all required selections (term, from week, to week) before generation.
     * Creates individual PDF reports for each student containing their data
     **/
    @FXML
    protected void onGenerateAllReportsClick() {
        String termValue = term.getValue();
        String fromValue = fromWeek.getValue();
        String toValue = toWeek.getValue();

        if (termValue == null || fromValue == null || toValue == null) {
            showAlert("Missing Selection", "Please select a term and week range.");
            return;
        }

        int termNumber = parseTermNumber(termValue);
        int from = parseWeekNumber(fromValue);
        int to = parseWeekNumber(toValue);

        if (from > to) {
            showAlert("Invalid Range", "'From Week' cannot be after 'To Week'.");
            return;
        }

        String userHome = System.getProperty("user.home");

        File pdfDir = new File(userHome, "/Documents/pdfs");

        try {
            reportsService.generateAll(termNumber, from, to);
            showAlert("Success", "All reports for students within week range generated at " + pdfDir);
            Desktop.getDesktop().open(pdfDir);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error Generating Report", e.getMessage());
        }
    }

    private int parseTermNumber(String termText) {
        return Integer.parseInt(termText.replaceAll("\\D+", ""));
    }

    private int parseWeekNumber(String weekText) {
        return Integer.parseInt(weekText.replaceAll("\\D+", ""));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
