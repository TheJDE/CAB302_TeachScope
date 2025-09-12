package com.cab302.teachscope.controllers;

import com.cab302.teachscope.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    protected void onLoginClick() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    protected void onSignUpClick() throws IOException {
        Stage stage = (Stage) signUpLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Sign Up");
        stage.show();
    }
}