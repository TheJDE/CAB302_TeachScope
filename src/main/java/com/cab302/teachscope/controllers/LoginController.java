package com.cab302.teachscope.controllers;

import com.cab302.teachscope.util.NavigationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

import com.cab302.teachscope.models.dao.DbUserDao;
import com.cab302.teachscope.models.services.UserService;

public class LoginController {

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
     * Login button.
     */
    @FXML
    private Button loginButton;

    /**
     * Sign up page button.
     */
    @FXML
    private Hyperlink signUpLink;

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
    protected void onLoginClick() throws IOException {
        errorLabel.setText("");
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            userService.login(email, password);

            Stage stage = (Stage) loginButton.getScene().getWindow();

            NavigationUtils.navigateTo(stage, "dashboard", "Dashboard");

        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    /**
     * Redirects to sign-up page.
     * @throws IOException On failed redirect.
     */
    @FXML
    protected void onSignUpClick() throws IOException {
        Stage stage = (Stage) signUpLink.getScene().getWindow();

        NavigationUtils.navigateTo(stage, "signup", "Sign Up");
    }
}