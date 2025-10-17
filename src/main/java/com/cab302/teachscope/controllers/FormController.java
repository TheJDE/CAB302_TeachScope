package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.services.FormService;
import com.cab302.teachscope.models.entities.WeeklyForm;
import com.cab302.teachscope.models.dao.DbStudentDao;
import com.cab302.teachscope.models.services.StudentService;
import com.cab302.teachscope.controllers.GeneratePDFController;
import com.cab302.teachscope.util.LoggedInUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.Optional;

import javafx.scene.layout.HBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

import java.io.IOException;

import com.cab302.teachscope.util.NavigationUtils;

import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;


/**
 * Controller for managing weekly forms for a student.
 * Handles creating, editing, deleting forms and navigation between views.
 */
public class FormController {

    @FXML
    private TableView<WeeklyForm> formsTableTerm1;
    @FXML
    private TableView<WeeklyForm> formsTableTerm2;
    @FXML
    private TableView<WeeklyForm> formsTableTerm3;
    @FXML
    private TableView<WeeklyForm> formsTableTerm4;

    @FXML
    private TableColumn<WeeklyForm, Integer> weekColTerm1, termColTerm1;
    @FXML
    private TableColumn<WeeklyForm, Integer> weekColTerm2, termColTerm2;
    @FXML
    private TableColumn<WeeklyForm, Integer> weekColTerm3, termColTerm3;
    @FXML
    private TableColumn<WeeklyForm, Integer> weekColTerm4, termColTerm4;

    @FXML
    private TableColumn<WeeklyForm, Void> actionsColTerm1, actionsColTerm2, actionsColTerm3, actionsColTerm4;

    @FXML
    private TableView<Map<String, String>> timelineTable;
    @FXML
    private TableColumn<Map<String, String>, String> nameColumn;
    @FXML
    private TableColumn<Map<String, String>, String> statusColumn;
    @FXML
    private TableColumn<Map<String, String>, String> formColumn;

    @FXML
    private ComboBox<String> term, week, attendancedays, dayslate, attention,
            participation, literacy, numeracy, understanding, behaviour,
            peerInteraction, respectRules, timelineTerm, timelineWeek, homework, emotionalState, teacherConcern;

    @FXML
    private TextArea concernsText;

    @FXML
    private Button newFormButton, saveFormButton, logoutButton, studentNav, timelineButton, generatePDF, addNewFormButton, weeklyformsButton, viewStudents;
    @FXML
    private Label weeklyFormsTitle, formTitle, PDFTitle;

    @FXML
    private Label termError;
    @FXML
    private Label weekError;
    @FXML
    private Label attendanceError;
    @FXML
    private Label daysLateError;
    @FXML
    private Label attentionError;
    @FXML
    private Label homeworkError;
    @FXML
    private Label participationError;
    @FXML
    private Label literacyError;
    @FXML
    private Label numeracyError;
    @FXML
    private Label understandingError;
    @FXML
    private Label behaviourError;
    @FXML
    private Label peerInteractionError;
    @FXML
    private Label respectRulesError;
    @FXML
    private Label emotionalStateError;
    @FXML
    private Label teacherConcernsError;

    @FXML
    private Hyperlink deleteFormLink;

    private final FormService formService = new FormService(new DbFormDao());
    private final StudentService studentService = new StudentService(new DbStudentDao());
    private String studentId;
    private String studentName;
    private Optional<WeeklyForm> editingForm = Optional.empty();

    //Navigation Bar Functions
    @FXML
    protected void onKnowledgeBaseClick() {
        NavigationUtils.openKnowledgeBasePDF();
    }

    @FXML
    protected void onIntroductoryTutorialClick() {
        NavigationUtils.openIntroductoryTutorial();
    }

    @FXML
    protected void onLogoutClick() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "login", "Login");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open login page.");
        }
    }

    @FXML
    protected void onStudentClick() {
        Stage stage = (Stage) studentNav.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open dashboard.");
        }
    }

    @FXML
    protected void onTimelineClick() {
        Stage stage = (Stage) timelineButton.getScene().getWindow();
        try {
            NavigationUtils.navigateTo(stage, "timeline", "Timeline");
        } catch (IOException e) {
            showAlert("Navigation Error", "Cannot open timeline.");
        }
    }


    @FXML
    protected void weeklyFormsClick() throws IOException {
        // go to weeklyforms.fxml
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
     * Opens the "Add New Form" page for creating a new weekly form.
     */
    @FXML
    protected void newFormClick() {
        Stage stage = (Stage) addNewFormButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addnewform.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the student info
            FormController controller = loader.getController();
            controller.setStudent(studentId, studentName);

            stage.setTitle("Add New Form");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the new form page.");
        }
    }

    @FXML
    protected void generatePDFClick() {
        Stage stage = (Stage) generatePDF.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/generatepdf.fxml"));
            Parent root = loader.load();

            // Get the correct controller type
            GeneratePDFController controller = loader.getController();
            controller.setStudent(studentId, studentName);

            stage.setTitle("Generate PDF");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the Generate PDF page.");
        }
    }


    /**
     * Initializes the controller after the FXML has been loaded.
     * Sets up the table and populates the form if editing an existing one.
     */
    @FXML
    protected void initialize() {
        setupTable(formsTableTerm1, weekColTerm1, termColTerm1, actionsColTerm1);
        setupTable(formsTableTerm2, weekColTerm2, termColTerm2, actionsColTerm2);
        setupTable(formsTableTerm3, weekColTerm3, termColTerm3, actionsColTerm3);
        setupTable(formsTableTerm4, weekColTerm4, termColTerm4, actionsColTerm4);
        setupTimelineTable(timelineTable, nameColumn, statusColumn, formColumn);

        populateFormIfEditing();

    }

    /**
     * Sets up the weekly forms table with columns and action buttons.
     */
    private void setupTable(TableView<WeeklyForm> table,
                            TableColumn<WeeklyForm, Integer> weekCol,
                            TableColumn<WeeklyForm, Integer> termCol,
                            TableColumn<WeeklyForm, Void> actionsCol) {

        if (table == null) return;

        weekCol.setCellValueFactory(new PropertyValueFactory<>("week"));
        termCol.setCellValueFactory(new PropertyValueFactory<>("term"));

        weekCol.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getWeek()));
        termCol.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTerm()));

        // Reuse action buttons
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink editLink = new Hyperlink("Edit Form");
            private final HBox container = new HBox(5, editLink);

            {
                editLink.setOnAction(e -> {
                    WeeklyForm form = getTableView().getItems().get(getIndex());
                    openFormEditor(form);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
    }


    private void setupTimelineTable(TableView<Map<String, String>> timelineTable,
                                    TableColumn<Map<String, String>, String> nameColumn,
                                    TableColumn<Map<String, String>, String> statusColumn,
                                    TableColumn<Map<String, String>, String> formColumn) {
        if (timelineTable == null) return;

        //display student name
        nameColumn.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getOrDefault("studentName", "Unknown")));

        //display completion status
        statusColumn.setCellValueFactory(cell ->
                new ReadOnlyObjectWrapper<>(cell.getValue().getOrDefault("status", "Incomplete")));

        //create clickable hyperlink for form column
        formColumn.setCellFactory(col -> new TableCell<Map<String, String>, String>() {
            private final Hyperlink link = new Hyperlink();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Map<String, String> rowData = getTableView().getItems().get(getIndex());
                    String term = rowData.getOrDefault("term", "?");
                    String week = rowData.getOrDefault("week", "?");
                    String formId = rowData.get("formId");
                    String status = rowData.getOrDefault("status", "Incomplete");


                    if ("Completed".equals(status) && formId != null && !formId.isEmpty()) {
                        // completed means open existing form
                        link.setText("View form for Term " + term + " - Week " + week);
                        link.setOnAction(e -> openFormFromTimeline(rowData));
                    } else {
                        // incomplete means open new form
                        link.setText("Create form for Term " + term + " - Week " + week);
                        link.setOnAction(e -> openNewFormFromTimeline(rowData));
                    }

                    setGraphic(link);
                }
            }
        });

        //initially empty
        timelineTable.setItems(FXCollections.observableArrayList());
    }

    private void openNewFormFromTimeline(Map<String, String> formData) {
        try {
            String studentIdFromTimeline = formData.get("studentId");
            String studentNameFromTimeline = formData.getOrDefault("studentName", "Student");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addnewform.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setStudent(studentIdFromTimeline, studentNameFromTimeline);
            controller.setEditingForm(null);

            Stage stage = (Stage) timelineTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Form - " + studentNameFromTimeline);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open new form page: " + e.getMessage());
        }
    }

    /**
     * Opens the form editor from the timeline view
     */
    private void openFormFromTimeline(Map<String, String> formData) {
        try {
            String formId = formData.get("formId");
            String studentIdFromTimeline = formData.get("studentId");
            String studentNameFromTimeline = formData.getOrDefault("studentName", "Student");

            if (formId == null || formId.isEmpty()) {
                showAlert("Error", "Form ID not found");
                return;
            }

            //fetch the actual form using the ID
            WeeklyForm form = formService.getForm(formId);

            if (form == null) {
                showAlert("Error", "Form not found");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addnewform.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setStudent(studentIdFromTimeline, studentNameFromTimeline);
            controller.setEditingForm(form);

            Stage stage = (Stage) timelineTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Form - " + studentNameFromTimeline);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open form: " + e.getMessage());
        }
    }

    /**
     * Opens the editor page for the specified form.
     *
     * @param form The weekly form to edit.
     */
    private void openFormEditor(WeeklyForm form) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addnewform.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setStudent(studentId, studentName); // pass student context
            controller.setEditingForm(form);               // pass form to edit

            Stage stage = (Stage) addNewFormButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open form editor.");
        }
    }

    /**
     * Sets the form currently being edited.
     *
     * @param form The form to edit.
     */
    public void setEditingForm(WeeklyForm form) {
        this.editingForm = Optional.ofNullable(form); // allow null
        populateFormIfEditing();
    }


    /**
     * Loads all weekly forms for the current student into the table.
     */
    private void loadForms() {
        if (studentId == null) return;
        try {
            var forms = formService.getAllFormsForStudent(studentId);

            formsTableTerm1.setItems(FXCollections.observableArrayList(
                    forms.stream().filter(f -> f.getTerm() == 1).collect(Collectors.toList())
            ));
            formsTableTerm2.setItems(FXCollections.observableArrayList(
                    forms.stream().filter(f -> f.getTerm() == 2).collect(Collectors.toList())
            ));
            formsTableTerm3.setItems(FXCollections.observableArrayList(
                    forms.stream().filter(f -> f.getTerm() == 3).collect(Collectors.toList())
            ));
            formsTableTerm4.setItems(FXCollections.observableArrayList(
                    forms.stream().filter(f -> f.getTerm() == 4).collect(Collectors.toList())
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the student context for this controller.
     *
     * @param studentId   ID of the student.
     * @param studentName Name of the student.
     */
    public void setStudent(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;

        if (weeklyFormsTitle != null) {
            weeklyFormsTitle.setText(studentName + "'s Weekly Forms");
        }
        if (formTitle != null) {
            formTitle.setText("Add New Form (" + studentName + ")");
        }

        if (PDFTitle != null) {
            PDFTitle.setText("Generate PDF for " + studentName);
        }

        if (formsTableTerm1 != null && formsTableTerm2 != null &&
                formsTableTerm3 != null && formsTableTerm4 != null) {
            loadForms();
        }
    }

    /**
     * Populates form fields if editing an existing form.
     * Also handles visibility of the delete hyperlink.
     */
    private void populateFormIfEditing() {
        if (editingForm.isPresent()) {
            WeeklyForm form = editingForm.get();

            term.getSelectionModel().select(form.getTerm() - 1);
            week.getSelectionModel().select(form.getWeek() - 1);
            attendancedays.setValue(String.valueOf(form.getAttendanceDays()));
            dayslate.setValue(String.valueOf(form.getDaysLate()));
            attention.getSelectionModel().select(form.getAttentionScore());
            participation.getSelectionModel().select(form.getParticipationScore());
            literacy.getSelectionModel().select(form.getLiteracyScore());
            numeracy.getSelectionModel().select(form.getNumeracyScore());
            understanding.getSelectionModel().select(form.getUnderstandingScore());
            behaviour.getSelectionModel().select(form.getBehaviourScore());
            peerInteraction.getSelectionModel().select(form.getPeerInteractionScore());
            respectRules.getSelectionModel().select(form.getRespectForRulesScore());

            if (form.isHomeworkDone()) {
                homework.setValue("Yes");
            } else {
                homework.setValue("No");
            }

            String emotionalValue = switch (form.getEmotionalState()) {
                case "Happy" -> "Happy 🙂";
                case "Neutral" -> "Neutral 😐";
                case "Withdrawn" -> "Withdrawn 😕";
                case "Anxious" -> "Anxious 😟";
                default -> null;
            };
            emotionalState.setValue(emotionalValue);

            if (form.getTeacherConcerns() != null && !form.getTeacherConcerns().isBlank()) {
                teacherConcern.setValue("Yes");
                concernsText.setText(form.getTeacherConcerns());
            } else {
                teacherConcern.setValue("No");
                concernsText.clear();
            }

            formTitle.setText("Edit Form (" + studentName + ")");
            saveFormButton.setText("Update Form");

            if (deleteFormLink != null) {
                deleteFormLink.setVisible(true);
            }

        } else {
            if (deleteFormLink != null) {
                deleteFormLink.setVisible(false); // hide when adding new form
            }
        }
    }

    /**
     * Timeline Page
     */

    @FXML
    protected void viewTimelineStudents() {
        try {
            if (timelineTerm.getSelectionModel().getSelectedIndex() < 0 ||
                    timelineWeek.getSelectionModel().getSelectedIndex() < 0) {
                showAlert("Selection Error", "Please select both term and week");
                return;
            }

            int termTimeline = timelineTerm.getSelectionModel().getSelectedIndex() + 1;
            int weekTimeline = timelineWeek.getSelectionModel().getSelectedIndex() + 1;

            //this gets the forms that exist for this week
            var formsForWeek = formService.getAllFormsForGivenWeek(termTimeline, weekTimeline);

            //create a map of studentId to formId for quick lookup
            Map<String, String> formIdMap = new HashMap<>();
            for (Map<String, String> form : formsForWeek) {
                formIdMap.put(form.get("studentID"), form.get("id"));
            }

            //get all students using StudentService
            String currentTeacherEmail = LoggedInUser.getEmail();
            var allStudents = studentService.getAllStudents(currentTeacherEmail);

            //build the timeline data
            var timelineData = allStudents.stream().map(student -> {
                Map<String, String> row = new HashMap<>();
                row.put("studentId", student.getId());
                row.put("studentName", student.getFirstName() + " " + student.getLastName());
                row.put("term", String.valueOf(termTimeline));
                row.put("week", String.valueOf(weekTimeline));

                String formId = formIdMap.get(student.getId());
                if (formId != null && !formId.isEmpty()) {
                    row.put("status", "Completed");
                    row.put("formId", formId);
                } else {
                    row.put("status", "Incomplete");
                    row.put("formId", "");
                }

                return row;
            }).collect(Collectors.toList());

            timelineTable.setItems(FXCollections.observableArrayList(timelineData));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Timeline Error", "Error loading timeline: " + e.getMessage());
        }
    }


    /**
     * Handles the submission of the form (creating or updating).
     */
    @FXML
    protected void onSubmitFormClick() {
        if (!validateAddNewForm()) {
            return; // Stop submission if there are errors
        }
        try {
            int termVal = term.getSelectionModel().getSelectedIndex() + 1;
            int weekVal = week.getSelectionModel().getSelectedIndex() + 1;
            int attendance = Integer.parseInt(attendancedays.getValue());
            int late = Integer.parseInt(dayslate.getValue());
            int attentionVal = attention.getSelectionModel().getSelectedIndex();
            int participationVal = participation.getSelectionModel().getSelectedIndex();
            int literacyVal = literacy.getSelectionModel().getSelectedIndex();
            int numeracyVal = numeracy.getSelectionModel().getSelectedIndex();
            int understandingVal = understanding.getSelectionModel().getSelectedIndex();
            int behaviourVal = behaviour.getSelectionModel().getSelectedIndex();
            int peerVal = peerInteraction.getSelectionModel().getSelectedIndex();
            int respectVal = respectRules.getSelectionModel().getSelectedIndex();

            boolean homeworkDone = "Yes".equals(homework.getValue());

            String emotionalSelection = emotionalState.getValue();
            String emotionalStateValue = null;
            if (emotionalSelection != null) {
                if (emotionalSelection.contains("Happy")) emotionalStateValue = "Happy";
                else if (emotionalSelection.contains("Neutral")) emotionalStateValue = "Neutral";
                else if (emotionalSelection.contains("Withdrawn")) emotionalStateValue = "Withdrawn";
                else if (emotionalSelection.contains("Anxious")) emotionalStateValue = "Anxious";
            }

            String teacherConcernSelection = teacherConcern.getValue();
            String teacherConcerns = "Yes".equals(teacherConcernSelection)
                    ? concernsText.getText()
                    : "";

            if (editingForm.isPresent()) {
                WeeklyForm form = editingForm.get();
                formService.updateForm(new WeeklyForm(
                        Optional.ofNullable(form.getId()),
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
                        emotionalStateValue,
                        teacherConcerns
                ));
                editingForm = Optional.empty();
            } else {
                formService.createForm(studentId, termVal, weekVal, attendance, late,
                        attentionVal, homeworkDone, participationVal, literacyVal, numeracyVal,
                        understandingVal, behaviourVal, peerVal, respectVal, emotionalStateValue, teacherConcerns);
            }
            if (formsTableTerm1 != null && formsTableTerm2 != null &&
                    formsTableTerm3 != null && formsTableTerm4 != null) {
                loadForms();
            }

            Stage stage = (Stage) saveFormButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/weeklyforms.fxml"));
            Parent root = loader.load();

            FormController controller = loader.getController();
            controller.setStudent(studentId, studentName);

            stage.setScene(new Scene(root));
            stage.setTitle(studentName + "'s Weekly Forms");
            stage.show();


        } catch (Exception e) {
            showAlert("Form Error", e.getMessage());
        }
    }

    /**
     * Deletes the currently edited form after user confirmation.
     */
    @FXML
    protected void onDeleteForm() {
        if (editingForm.isEmpty()) {
            showAlert("Delete Error", "No form selected for deletion.");
            return;
        }

        WeeklyForm form = editingForm.get();

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete form for Term " + form.getTerm() + ", Week " + form.getWeek() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);

        confirm.showAndWait().ifPresent(button -> {
            if (button == ButtonType.YES) {
                try {
                    formService.deleteForm(form.getId());

                    Stage stage = (Stage) saveFormButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/weeklyforms.fxml"));
                    Parent root = loader.load();

                    FormController controller = loader.getController();
                    controller.setStudent(studentId, studentName);

                    stage.setScene(new Scene(root));
                    stage.setTitle(studentName + "'s Weekly Forms");
                    stage.show();

                } catch (Exception e) {
                    showAlert("Delete Error", "Could not delete form: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Displays an error alert with the given title and message.
     *
     * @param title   The alert title.
     * @param message The alert message.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Validates the Add New Form inputs and displays error messages under each field.
     *
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateAddNewForm() {
        boolean isValid = true;

        termError.setText("");
        weekError.setText("");
        attendanceError.setText("");
        daysLateError.setText("");
        attentionError.setText("");
        homeworkError.setText("");
        participationError.setText("");
        literacyError.setText("");
        numeracyError.setText("");
        understandingError.setText("");
        behaviourError.setText("");
        peerInteractionError.setText("");
        respectRulesError.setText("");
        emotionalStateError.setText("");
        teacherConcernsError.setText("");

        // Term
        if (term.getSelectionModel().getSelectedIndex() < 0) {
            termError.setText("Term is required.");
            isValid = false;
        }

        // Week
        if (week.getSelectionModel().getSelectedIndex() < 0) {
            weekError.setText("Week is required.");
            isValid = false;
        }

        // Attendance Days
        int daysAttended = 0;
        if (attendancedays.getValue() == null || attendancedays.getValue().isBlank()) {
            attendanceError.setText("Number of days attended is required.");
            isValid = false;
        } else {
            try {
                daysAttended = Integer.parseInt(attendancedays.getValue());
                if (daysAttended < 0 || daysAttended > 5) {
                    attendanceError.setText("Attendance must be between 0-5.");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                attendanceError.setText("Invalid number.");
                isValid = false;
            }
        }

        // Days Late
        if (dayslate.getValue() == null || dayslate.getValue().isBlank()) {
            daysLateError.setText("Please select days late.");
            isValid = false;
        } else {
            try {
                int late = Integer.parseInt(dayslate.getValue());
                if (late < 0 || late > 5) {
                    daysLateError.setText("Days late must be between 0-5.");
                    isValid = false;
                } else if (late > daysAttended) {
                    daysLateError.setText("Days late cannot exceed days attended.");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                daysLateError.setText("Invalid number of days late.");
                isValid = false;
            }
        }

        // Attention
        if (attention.getSelectionModel().getSelectedIndex() < 0) {
            attentionError.setText("Attention rating is required.");
            isValid = false;
        }

        // Homework
        if (homework.getValue() == null || homework.getValue().isBlank()) {
            homeworkError.setText("Please select if homework is done.");
            isValid = false;
        }

        // Participation
        if (participation.getSelectionModel().getSelectedIndex() < 0) {
            participationError.setText("Participation rating is required.");
            isValid = false;
        }

        // Literacy
        if (literacy.getSelectionModel().getSelectedIndex() < 0) {
            literacyError.setText("Literacy rating is required.");
            isValid = false;
        }

        // Numeracy
        if (numeracy.getSelectionModel().getSelectedIndex() < 0) {
            numeracyError.setText("Numeracy rating is required.");
            isValid = false;
        }

        // Understanding
        if (understanding.getSelectionModel().getSelectedIndex() < 0) {
            understandingError.setText("Understanding rating is required.");
            isValid = false;
        }

        // Behaviour
        if (behaviour.getSelectionModel().getSelectedIndex() < 0) {
            behaviourError.setText("Behaviour rating is required.");
            isValid = false;
        }

        // Peer Interaction
        if (peerInteraction.getSelectionModel().getSelectedIndex() < 0) {
            peerInteractionError.setText("Peer interaction rating is required.");
            isValid = false;
        }

        // Respect Rules
        if (respectRules.getSelectionModel().getSelectedIndex() < 0) {
            respectRulesError.setText("Respect for rules rating is required.");
            isValid = false;
        }

        // Emotional State
        if (emotionalState.getValue() == null || emotionalState.getValue().isBlank()) {
            emotionalStateError.setText("Emotional state is required.");
            isValid = false;
        }

        //Teacher Concerns
        if (teacherConcern.getValue() == null || teacherConcern.getValue().isBlank()) {
            teacherConcernsError.setText("Please indicate if there are any teacher concerns.");
            isValid = false;
        } else if ("Yes".equals(teacherConcern.getValue())) {
            if (concernsText.getText() == null || concernsText.getText().isBlank()) {
                teacherConcernsError.setText("Please provide details for teacher concerns.");
                isValid = false;
            }
        }

        return isValid;
    }

}