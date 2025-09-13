package com.cab302.teachscope.controllers;

import com.cab302.teachscope.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class FormController {
    @FXML
    private Button logoutButton;

    @FXML private ToggleGroup lateGroup;
    @FXML private RadioButton lateNo;
    @FXML private RadioButton lateYes;

    @FXML private ToggleGroup emotionalGroup;
    @FXML private RadioButton happyRadio;
    @FXML private RadioButton neutralRadio;
    @FXML private RadioButton withdrawnRadio;
    @FXML private RadioButton anxiousRadio;

    @FXML
    protected void onLogoutClick() throws IOException {
        // go back to login.fxml
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Login");
        stage.show();
    }


}
