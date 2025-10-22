package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.dao.DbUserDao;
import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.models.services.UserService;
import com.cab302.teachscope.util.NavigationUtils;
import com.cab302.teachscope.util.PasswordUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NewPasswordController {

    /**
     * New Password Text Field.
     */
    @FXML
    private TextField newPasswordField;

    /**
     * Confirm Password Text Field.
     */
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Reset Password Button.
     */
    @FXML
    private Button resetPasswordButton;

    /**
     * Error label.
     */
    @FXML
    private Label errorLabel;

    /**
     * Selected user service and DAO.
     */
    private final UserService userService = new UserService(new DbUserDao());

    /**
     * User Email variable, passed on from sendresetcode page, via the onResetPasswordClick() function in the ResetCodeController.
     */
    private String userEmail;

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    /**
     * Method to update the Users Password, via UserService.updatePassword(). Method then takes user back to Login Page.
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    @FXML
    protected void onResetPasswordClick() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        // Check to see if the text fields match
        if (!newPassword.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }
        try {
            userService.updatePassword(userEmail, newPassword);
            errorLabel.setText("Password updated successfully!");
            Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
            NavigationUtils.navigateTo(stage, "login", "Login");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error updating password.");
        }
    }
}