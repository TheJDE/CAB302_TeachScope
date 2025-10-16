package com.cab302.teachscope;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.services.GenerateReportsService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
//import java.sql.Connection;

import org.kordamp.bootstrapfx.BootstrapFX;

public class Main extends Application {

    private FormDao formDao;
    private GenerateReportsService generateReportsService;

    @Override
    public void start(Stage stage) throws IOException {
        formDao = new DbFormDao();
        generateReportsService = new GenerateReportsService(formDao);
        generateReportsService.createReport("a2c17e0f-28c6-44c9-9a82-61904304b18e");

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Login");
        stage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setMaximized(true);
        stage.show();
    }

        public static void main(String[] args) {
        Connection connection = DatabaseConnection.getInstance();
        launch();
    }
}