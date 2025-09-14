package com.cab302.teachscope.controllers;

import com.cab302.teachscope.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class StudentController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button newStudentButton;

    @FXML
    private Hyperlink deleteLink;

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

    @FXML
    protected void newStudentClick() throws IOException {
        // go to newstudent.fxml
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/newstudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Add New Student");
        stage.show();
    }
    @FXML
    protected void onDeleteLinkClick() throws IOException {
       //delete student
        // hide and show this link based on edit page
    }

}
