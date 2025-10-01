package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.services.FormService;
import com.cab302.teachscope.models.entities.WeeklyForm;
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

/**
 * Controller for managing weekly forms for a student.
 * Handles creating, editing, deleting forms and navigation between views.
 */
public class FormController {

    @FXML private TableView<WeeklyForm> formsTableTerm1;
    @FXML private TableView<WeeklyForm> formsTableTerm2;
    @FXML private TableView<WeeklyForm> formsTableTerm3;
    @FXML private TableView<WeeklyForm> formsTableTerm4;

    @FXML private TableColumn<WeeklyForm, Integer> weekColTerm1, termColTerm1;
    @FXML private TableColumn<WeeklyForm, Integer> weekColTerm2, termColTerm2;
    @FXML private TableColumn<WeeklyForm, Integer> weekColTerm3, termColTerm3;
    @FXML private TableColumn<WeeklyForm, Integer> weekColTerm4, termColTerm4;

    @FXML private TableColumn<WeeklyForm, Void> actionsColTerm1, actionsColTerm2, actionsColTerm3, actionsColTerm4;

    @FXML private ComboBox<String> term, week, attendancedays, dayslate, attention,
            participation, literacy, numeracy, understanding, behaviour,
            peerInteraction, respectRules;

    @FXML private TextArea concernsText;
    @FXML private RadioButton homeworkNo, homeworkYes, happyRadio, neutralRadio, withdrawnRadio, anxiousRadio;
    @FXML private ToggleGroup homeworkGroup, emotionalGroup;

    @FXML private Button newFormButton, saveFormButton, logoutButton, studentNav, timelineButton, generatePDF, addNewFormButton, weeklyformsButton;
    @FXML private Label weeklyFormsTitle, formTitle, PDFTitle;

    @FXML private Hyperlink deleteFormLink;

    private final FormService formService = new FormService(new DbFormDao());
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
        try { NavigationUtils.navigateTo(stage, "login", "Login"); }
        catch (IOException e) { showAlert("Navigation Error", "Cannot open login page."); }
    }

    @FXML
    protected void onStudentClick() {
        Stage stage = (Stage) studentNav.getScene().getWindow();
        try { NavigationUtils.navigateTo(stage, "dashboard", "Dashboard"); }
        catch (IOException e) { showAlert("Navigation Error", "Cannot open dashboard."); }
    }

    @FXML
    protected void onTimelineClick() {
        Stage stage = (Stage) timelineButton.getScene().getWindow();
        try { NavigationUtils.navigateTo(stage, "timeline", "Timeline"); }
        catch (IOException e) { showAlert("Navigation Error", "Cannot open timeline."); }
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

            // Get the controller and pass the student info
            FormController controller = loader.getController();
            controller.setStudent(studentId, studentName);

            stage.setTitle("Generate PDF");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Navigation Error", "Could not open the new form page.");
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
        this.editingForm = Optional.of(form);
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

        if(PDFTitle != null) {
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
            homeworkYes.setSelected(form.isHomeworkDone());
            homeworkNo.setSelected(!form.isHomeworkDone());
            participation.getSelectionModel().select(form.getParticipationScore());
            literacy.getSelectionModel().select(form.getLiteracyScore());
            numeracy.getSelectionModel().select(form.getNumeracyScore());
            understanding.getSelectionModel().select(form.getUnderstandingScore());
            behaviour.getSelectionModel().select(form.getBehaviourScore());
            peerInteraction.getSelectionModel().select(form.getPeerInteractionScore());
            respectRules.getSelectionModel().select(form.getRespectForRulesScore());

            switch (form.getEmotionalState()) {
                case "Happy" -> happyRadio.setSelected(true);
                case "Neutral" -> neutralRadio.setSelected(true);
                case "Withdrawn" -> withdrawnRadio.setSelected(true);
                default -> anxiousRadio.setSelected(true);
            }

            concernsText.setText(form.getTeacherConcerns());
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
     * Handles the submission of the form (creating or updating).
     */
    @FXML
    protected void onSubmitFormClick() {
        try {
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

            String emotionalState = happyRadio.isSelected() ? "Happy" :
                    neutralRadio.isSelected() ? "Neutral" :
                            withdrawnRadio.isSelected() ? "Withdrawn" : "Anxious";

            String teacherConcerns = concernsText.getText();

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
                        emotionalState,
                        teacherConcerns
                ));
                editingForm = Optional.empty();
            } else {
                formService.createForm(studentId, termVal, weekVal, attendance, late,
                        attentionVal, homeworkDone, participationVal, literacyVal, numeracyVal,
                        understandingVal, behaviourVal, peerVal, respectVal, emotionalState, teacherConcerns);
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
}