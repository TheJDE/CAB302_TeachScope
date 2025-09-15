package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbStudentDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.services.StudentService;
import com.cab302.teachscope.util.NavigationUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class StudentController {

    @FXML private Button logoutButton;
    @FXML private Button newStudentButton;
    @FXML private Button studentNav;

    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> gradeColumn;
    @FXML private TableColumn<Student, String> classColumn;
    @FXML private TableColumn<Student, String> statusColumn;
    @FXML private TableColumn<Student, String> genderColumn;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField classField;

    @FXML private ComboBox<String> gender;
    @FXML private ComboBox<String> gradeLevel;
    @FXML private ComboBox<String> studentStatus;

    @FXML private Label formTitle;
    @FXML private Button addStudentButton;
    @FXML private Hyperlink deleteLink;

    private final StudentService studentService = new StudentService(new DbStudentDao());
    private Optional<Student> editingStudent = Optional.empty(); //tracks if we're editing

    @FXML protected void initialize() {
        if (studentsTable != null) {
            // If this is the students list page
            setupTable();
        }

        if (addStudentButton != null) {
            // If this is the add/edit form page
            setupForm();
        }
    }

    @FXML
    protected void newStudentClick() {
        Stage stage = (Stage) newStudentButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "newstudent", "Add New Student");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the new student page.");
        }
    }

    @FXML
    protected void onLogoutClick() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "login", "Login");
        } catch (IOException e) {
            showAlert("Navigation Error", "Unable to open login page.");
        }
    }

    @FXML
    protected void onStudentClick() {
        Stage stage = (Stage) studentNav.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open dashboard.");
        }
    }

    private void setupTable() {
        nameColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setGraphic(null);
                } else {
                    Student student = getTableView().getItems().get(getIndex());
                    Hyperlink link = new Hyperlink(student.getFirstName() + " " + student.getLastName());
                    link.setOnAction(e -> openEditPage(student));
                    setGraphic(link);
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

    private void refreshStudentTable() {
        studentsTable.getItems().setAll(studentService.getAllStudents());
    }

    private void setupForm() {
        deleteLink.setVisible(false);
        addStudentButton.setOnAction(e -> handleSave());

        editingStudent.ifPresent(this::populateForm);
    }

    private void populateForm(Student student) {
        formTitle.setText("Edit Student");
        addStudentButton.setText("Update Student");
        deleteLink.setVisible(true);

        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        classField.setText(student.getClassCode());
        gender.setValue(student.getGender().name());
        gradeLevel.setValue(student.getGradeLevel().name());
        studentStatus.setValue(student.getEnrolmentStatus().name());
    }

    @FXML
    private void handleSave() {
        try {
            String fName = firstName.getText();
            String lName = lastName.getText();
            String cls = classField.getText();

            //convert combobox selections to enums
            Student.Gender g = getEnumFromComboBox(gender, Student.Gender.class);
            Student.GradeLevel gl = getEnumFromComboBox(gradeLevel, Student.GradeLevel.class);
            Student.EnrolmentStatus status = getEnumFromComboBox(studentStatus, Student.EnrolmentStatus.class);

            if (editingStudent.isPresent()) {
                studentService.updateStudent(
                        editingStudent.get().getId(),
                        fName, lName, g, gl, cls, status
                );
            } else {
                studentService.registerStudent(fName, lName, g, gl, cls, status);
            }

            Stage stage = (Stage) addStudentButton.getScene().getWindow();
            try {
                NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
            } catch (IOException e) {
                showAlert("Navigation Error", "Failed to open the dashboard.");
            }

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }


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

    @FXML
    protected void onDeleteLinkClick() {
        editingStudent.ifPresent(student -> {
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
        });
    }

    private void openEditPage(Student student) {
        try {
            Stage stage = (Stage) studentsTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cab302/teachscope/views/newstudent.fxml"));
            VBox root = loader.load();

            StudentController formController = loader.getController();
            formController.setEditingStudent(student); // populate form
            formController.initialize();

            stage.getScene().setRoot(root);
            stage.setTitle("Edit Student");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setEditingStudent(Student student) {
        this.editingStudent = Optional.ofNullable(student);
    }
}
