package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbStudentDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.services.StudentService;
import com.cab302.teachscope.util.NavigationUtils;
import javafx.scene.Parent;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.Scene;

import com.cab302.teachscope.controllers.FormController;

public class StudentController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button newStudentButton;
    @FXML
    private Button studentNav;
    @FXML
    private Button knowledgeBaseButton;

    @FXML
    private Button viewFormsButton;

    @FXML
    private Button timelineButton;


    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> gradeColumn;
    @FXML
    private TableColumn<Student, String> classColumn;
    @FXML
    private TableColumn<Student, String> statusColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField classField;

    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> gradeLevel;
    @FXML
    private ComboBox<String> studentStatus;

    @FXML
    private Label formTitle;
    @FXML
    private Button addStudentButton;
    @FXML
    private Hyperlink deleteLink;

    @FXML
    private Label firstNameError;
    @FXML
    private Label lastNameError;
    @FXML
    private Label classError;
    @FXML
    private Label genderError;
    @FXML
    private Label gradeLevelError;
    @FXML
    private Label statusError;

    private final StudentService studentService = new StudentService(new DbStudentDao());
    private Optional<Student> editingStudent = Optional.empty(); //tracks if we're editing

    /**
     * Initializes the controller after the FXML is loaded
     * Sets up the student table on the dashboard or the form when on the add/edit student page.
     */
    @FXML
    protected void initialize() {
        if (studentsTable != null) {
            // If this is the students list page
            setupTable();
        }

        if (addStudentButton != null) {
            // If this is the add/edit form page
            setupForm();
        }
    }

    /**
     * Handles the click event for the Knowledge Base button
     * (currently unimplemented)
     */

    @FXML
    protected void onKnowledgeBaseClick() {
        NavigationUtils.openKnowledgeBasePDF();
    }

    @FXML
    protected void onIntroductoryTutorialClick() {
        NavigationUtils.openIntroductoryTutorial();
    }

    /**
     * Navigates to the new student form page
     */
    @FXML
    protected void newStudentClick() {
        Stage stage = (Stage) newStudentButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "newstudent", "Add New Student");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the new student page.");
        }
    }

    /**
     * Logs the user out of the system and takes them to the login page
     */
    @FXML
    protected void onLogoutClick() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "login", "Login");
        } catch (IOException e) {
            showAlert("Navigation Error", "Unable to open login page.");
        }
    }

    /**
     * Navigates to the dashboard page
     */
    @FXML
    protected void onStudentClick() {
        Stage stage = (Stage) studentNav.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open dashboard.");
        }
    }

    @FXML
    protected void onTimelineClick() {
        Stage stage = (Stage) timelineButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "timeline", "Timeline");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open timeline page.");
        }
    }

    /**
     * Sets up the table so that the student's name is displayed as a clickable hyperlink
     * When clicked the hyperlink will open the edit page for that student
     */
    private void setupTable() {
        // Configure the name column to display custom cells
        nameColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                //if the cell is empty (no data), clear its content
                if (empty || getTableRow() == null) {
                    setGraphic(null);
                } else {
                    //get the student object for the current row
                    Student student = getTableView().getItems().get(getIndex());
                    //create a clickable hyperlink with the students full name
                    Hyperlink link = new Hyperlink(student.getFirstName() + " " + student.getLastName());
                    //set the action for when the hyperlink is clicked then open the edit page
                    link.setOnAction(e -> openEditPage(student));
                    setGraphic(link); // add the hyperlink to the table cell
                }
            }
        });

        //other columns
        gradeColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGradeLevel().name()));
        classColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClassCode()));
        statusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEnrolmentStatus().name()));
        genderColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGender().name()));

        refreshStudentTable();
    }

    /**
     * Refreshes the student table by reloading all students from the database
     */
    private void refreshStudentTable() {
        studentsTable.getItems().setAll(studentService.getAllStudents());
    }

    /**
     * Configures the student form for adding or updating students
     */
    private void setupForm() {
        deleteLink.setVisible(false);
        viewFormsButton.setVisible(false);
        addStudentButton.setOnAction(e -> handleSave());
    }

    /**
     * Populates the student form fields when editing a student
     *
     * @param student the student to populate the form with
     */
    private void populateForm(Student student) {
        formTitle.setText("Edit Student");
        addStudentButton.setText("Update Student");
        deleteLink.setVisible(true);
        viewFormsButton.setVisible(true);

        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        classField.setText(student.getClassCode());
        gender.setValue(student.getGender().name());
        gradeLevel.setValue(student.getGradeLevel().name());
        studentStatus.setValue(student.getEnrolmentStatus().name());
    }

    /**
     * Handles saving a new or updated student when the save button is clicked
     */
    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {

            String fName = firstName.getText();
            String lName = lastName.getText();
            String cls = classField.getText();

            //convert combobox selections to enums
            Student.Gender g = getEnumFromComboBox(gender, Student.Gender.class);
            Student.GradeLevel gl = getEnumFromComboBox(gradeLevel, Student.GradeLevel.class);
            Student.EnrolmentStatus status = getEnumFromComboBox(studentStatus, Student.EnrolmentStatus.class);

            if (editingStudent.isPresent()) {
                //editing an existing student
                Student student = editingStudent.get();
                student.setFirstName(fName);
                student.setLastName(lName);
                student.setClassCode(cls);
                student.setGender(g);
                student.setGradeLevel(gl);
                student.setEnrolmentStatus(status);

                studentService.updateStudent(student.getId(), fName, lName, g, gl, cls, status);
            } else {
                //adding a new student
                studentService.registerStudent(fName, lName, g, gl, cls, status);
            }

            Stage stage = (Stage) addStudentButton.getScene().getWindow();
            try {
                NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
            } catch (IOException e) {
                showAlert("Navigation Error", "Failed to open the dashboard.");
            }

        } catch (IllegalArgumentException e) {
            // Map the exception message to the correct field
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("first")) displayFieldError("firstName", e.getMessage());
            else if (msg.contains("last")) displayFieldError("lastName", e.getMessage());
            else if (msg.contains("class")) displayFieldError("class", e.getMessage());
            else if (msg.contains("gender")) displayFieldError("gender", e.getMessage());
            else if (msg.contains("grade")) displayFieldError("gradeLevel", e.getMessage());
            else if (msg.contains("status")) displayFieldError("status", e.getMessage());
            else System.err.println("Unhandled error: " + e.getMessage());
        }
    }

    /**
     * Converts the selected ComboBox value into an enum constant.
     *
     * @param comboBox  the ComboBox containing the value
     * @param enumClass the enum type to convert too
     * @param <T>       the enum type
     * @return the matching enum constant
     */
    private <T extends Enum<T>> T getEnumFromComboBox(ComboBox<String> comboBox, Class<T> enumClass) {
        if (comboBox.getValue() == null) return null;
        String selected = comboBox.getValue().trim();

        for (T constant : enumClass.getEnumConstants()) {
            //replace spaces and ignore case
            if (constant.name().equalsIgnoreCase(selected.replace(" ", "_"))) {
                return constant;
            }
        }

        return null;
    }

    /**
     * Deletes the currently selected student if editing mode is active
     */
    @FXML
    protected void onDeleteLinkClick() {
        editingStudent.ifPresent(student -> {
            //Confirmation alert before deleting
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete Student");
            confirmAlert.setContentText("Are you sure you want to delete " +
                    student.getFirstName() + " " + student.getLastName() + "?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmAlert.getButtonTypes().setAll(yesButton, cancelButton);

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == yesButton) {
                try {
                    studentService.deleteStudent(student.getId());
                    Stage stage = (Stage) deleteLink.getScene().getWindow();
                    try {
                        NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
                    } catch (IOException e) {
                        showAlert("Navigation Error", "Failed to open the dashboard.");
                    }
                } catch (IllegalArgumentException e) {
                    showAlert("Error", e.getMessage());
                }
            }
        });
    }

    @FXML
    protected void viewFormsClick() throws IOException {
        // go to weeklyforms.fxml
        Stage stage = (Stage) viewFormsButton.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/weeklyforms.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            Student student = editingStudent.get();
            controller.setStudent(student.getId(), student.getFirstName() + " " + student.getLastName());

            stage.setScene(new Scene(root));
            stage.setTitle("View Weekly Forms");
            stage.show();

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    /**
     * Opens the student edit form for the given student.
     *
     * @param student the student to edit
     */
    private void openEditPage(Student student) {
        Stage stage = (Stage) studentsTable.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "newstudent", "Edit Student");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/newstudent.fxml"));
            Parent root = loader.load();

            StudentController formController = loader.getController();
            formController.setEditingStudent(student); // populate form

            stage.getScene().setRoot(root);
            stage.setTitle("Edit Student");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert with the given title and message
     *
     * @param title   the alert title
     * @param message the alert message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the current editing student and updates the form
     *
     * @param student the student being edited
     */
    public void setEditingStudent(Student student) {

        this.editingStudent = Optional.ofNullable(student);
        editingStudent.ifPresent(this::populateForm);
    }

    /**
     * Validates all fields individually, and display inline errors.
     */
    private boolean validateForm() {
        boolean valid = true;

        // Clear previous errors
        firstNameError.setText("");
        lastNameError.setText("");
        classError.setText("");
        genderError.setText("");
        gradeLevelError.setText("");
        statusError.setText("");

        // FIRST NAME
        if (firstName.getText().trim().isEmpty()) {
            firstNameError.setText("First name is required");
            valid = false;
        }
        if (!firstName.getText().trim().isEmpty() && !firstName.getText().matches("[A-Za-z]+")) {
            firstNameError.setText("First name must only contain letters");
            valid = false;
        }
        // LAST NAME
        if (lastName.getText().trim().isEmpty()) {
            lastNameError.setText("Last name is required");
            valid = false;
        }
        if (!lastName.getText().trim().isEmpty() && !lastName.getText().matches("[A-Za-z]+")) {
            lastNameError.setText("Last name must only contain letters");
            valid = false;
        }
        // CLASS
        if (classField.getText().trim().isEmpty()) {
            classError.setText("Class is required");
            valid = false;
        }
        if (!classField.getText().trim().isEmpty() && !classField.getText().matches("^[A-Za-z]$+")) {
            classError.setText("Class must be only a single letter");
            valid = false;
        }
        // GENDER
        if (gender.getValue() == null) {
            genderError.setText("Gender must be selected");
            valid = false;
        }
        // GRADE LEVEL
        if (gradeLevel.getValue() == null) {
            gradeLevelError.setText("Grade level must be selected");
            valid = false;
        }
        // STATUS
        if (studentStatus.getValue() == null) {
            statusError.setText("Enrolment status must be selected");
            valid = false;
        }
        return valid;
    }

    /**
     * Maps exception messages to individual field errorLabels
     */
    private void displayFieldError(String field, String message) {
        switch (field) {
            case "firstName" -> firstNameError.setText(message);
            case "lastName" -> lastNameError.setText(message);
            case "class" -> classError.setText(message);
            case "gender" -> genderError.setText(message);
            case "gradeLevel" -> gradeLevelError.setText(message);
            case "status" -> statusError.setText(message);
            default -> System.err.println("Unknown field error: " + message);
        }
    }

}
