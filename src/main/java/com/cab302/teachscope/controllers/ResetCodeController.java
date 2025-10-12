package com.cab302.teachscope.controllers;

import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.util.NavigationUtils;
import com.cab302.teachscope.util.PasswordUtils;
import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
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
     * Selected user service and DAO.
     */
    private final UserService userService = new UserService(new DbUserDao());

    /**
     * Method to log the user in. Sets error label to relevant values on exceptions.
     * @throws IOException On failed redirect.
     */

    @FXML
    protected void onSendResetCodeClick() throws IOException {

        String userEmail = emailField.getText().trim();

        if (userEmail.isEmpty()) {
            errorLabel.setText("Please enter your email address.");
            return;
        }
        try {
            String resetCode = PasswordUtils.generatePasswordResetCode(6);
            String hashedResetCode = PasswordUtils.hashResetCode(resetCode);

            DbUserDao userDao = new DbUserDao();
            User user = userDao.getUser(userEmail);

            if (user != null) {
                user.setResetCodeHash(hashedResetCode);
                userDao.updateUserResetCode(user);

                PasswordUtils.sendResetCode(userEmail, resetCode);

                errorLabel.setText("A reset code has been sent to your email.");
            } else {
                errorLabel.setText("No user found with that email.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database error. Please try again.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            errorLabel.setText("Invalid email address.");
        } catch (MessagingException e) {
            e.printStackTrace();
            errorLabel.setText("Failed to send email. Check the address or your SMTP settings.");
        } catch (Exception e) {
            e.printStackTrace(); // this will print the full stack trace
            System.out.println("Exception type: " + e.getClass().getName());
            System.out.println("Exception message: " + e.getMessage());
            errorLabel.setText("An unexpected error occurred. Please try again.");
        }
    }

    @FXML
    protected void onResetPasswordClick() throws IOException {
        String email = emailField.getText().trim();
        String resetCodeEntered = resetCodeField.getText().trim();

        if (email.isEmpty() || resetCodeEntered.isEmpty()) {
            errorLabel.setText("Please enter both email and reset code.");
            return;
        }

        try {
            userService.validateResetCode(email, resetCodeEntered);

            Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
            NavigationUtils.navigateTo(stage, "forgotpassword", "Set New Password");

        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("An unexpected error occurred. Please try again.");
        }
    }

}