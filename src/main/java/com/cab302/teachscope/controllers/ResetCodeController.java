package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.util.NavigationUtils;
import com.cab302.teachscope.util.PasswordUtils;
import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import com.cab302.teachscope.models.dao.DbUserDao;
import com.cab302.teachscope.models.services.UserService;


public class ResetCodeController {

    public Button sendResetCodeButton;
    /**
     * Email address text box.
     */
    @FXML
    private TextField emailField;

    /**
     * Sign up page button.
     */
    @FXML
    private Button resetPasswordButton;

    /**
     * Reset Code text box.
     */
    @FXML
    private TextField resetCodeField;

    /**
     * Error label.
     */
    @FXML
    private Label errorLabel;

    /**
     * Login page link.
     */
    @FXML
    private Hyperlink loginPageLink;


    /**
     * Selected user service and DAO.
     */
    private final UserService userService = new UserService(new DbUserDao());

    /**
     * Method to trigger UserService.sendPasswordResetCode, on the click of a button
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    @FXML
    protected void onSendResetCodeClick() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            errorLabel.setText("Please enter your email address.");
            return;
        }
        try {
            userService.sendPasswordResetCode(email);
            errorLabel.setText("Reset code sent to your email!");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Something went wrong. Please try again.");
            e.printStackTrace();
        }
    }

    /**
     * Method to trigger UserService.validateResetCode, on the click of a button
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    @FXML
    protected void onResetPasswordClick() throws IOException {
        String email = emailField.getText().trim();
        String resetCodeEntered = resetCodeField.getText().trim();
        if (email.isEmpty() || resetCodeEntered.isEmpty()) {
            errorLabel.setText("Please enter both email and reset code.");
            return;
        }
        try {
            // Verify reset code using UserService
            userService.validateResetCode(email, resetCodeEntered);
            // If valid, navigate to NewPassword page, PASSING the email entered
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/newpassword.fxml"));
            Parent root = loader.load();
            // Get the NewPasswordController and pass the email
            NewPasswordController controller = loader.getController();
            controller.setUserEmail(email);
            // Switch scene
            Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Set New Password");
            stage.show();
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("An unexpected error occurred. Please try again.");
        }
    }
    @FXML
    protected void loginPageClick() throws IOException {
        Stage stage = (Stage) loginPageLink.getScene().getWindow();

        NavigationUtils.navigateTo(stage, "login", "login");
    }

}