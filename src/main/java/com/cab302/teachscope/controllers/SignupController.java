package com.cab302.teachscope.controllers;

import com.cab302.teachscope.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Hyperlink loginLink;

    @FXML
    protected void onSignUpClick() {
        if (!passwordField.equals(confirmPasswordField)) {
            throw new IllegalArgumentException("Passwords must match");
        }
    }

    @FXML
    protected void onLoginLinkClick() throws IOException {
        // Switch back to login.fxml
        Stage stage = (Stage) loginLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login");
        stage.show();
    }
}