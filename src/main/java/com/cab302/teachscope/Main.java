package com.cab302.teachscope;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
//import java.sql.Connection;

import org.kordamp.bootstrapfx.BootstrapFX;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

        public static void main(String[] args) {
        Connection connection = DatabaseConnection.getInstance();
        launch();
    }
}