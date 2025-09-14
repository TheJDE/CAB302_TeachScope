package com.cab302.teachscope.controllers;

import com.cab302.teachscope.Main;
import com.cab302.teachscope.util.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import com.cab302.teachscope.models.dao.DbUserDao;
import com.cab302.teachscope.models.services.UserService;

import java.io.IOException;

public class SignupController {


    @FXML
    private TextField fullNameField;

    /**
     * Email address text box.
     */
    @FXML
    private TextField emailField;

    /**
     * Password text box.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Confirmed password text box.
     */
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Sign up button.
     */
    @FXML
    private Button signUpButton;

    /**
     * Log in page button.
     */
    @FXML
    private Hyperlink loginLink;

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
     * Method to sign the user up. Sets error label to relevant values on exceptions.
     */
    @FXML
    protected void onSignUpClick() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        errorLabel.setText("");

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        try {
            userService.registerUser(email, password);

            Stage stage = (Stage) signUpButton.getScene().getWindow();

            NavigationUtils.navigateTo(stage, "login", "Login");

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load login.fxml", e);
        }
    }

    /**
     * Redirects to log in page.
     * @throws IOException On failed redirect.
     */
    @FXML
    protected void onLoginLinkClick() throws IOException {
        // Switch back to login.fxml
        Stage stage = (Stage) loginLink.getScene().getWindow();

        NavigationUtils.navigateTo(stage, "login", "Login");
    }
}