package com.cab302.teachscope;

import com.cab302.teachscope.models.dao.DbFormDao;
import com.cab302.teachscope.models.dao.DbStudentDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.StudentDao;
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
    private StudentDao studentDao;
    private GenerateReportsService generateReportsService;

    @Override
    public void start(Stage stage) throws IOException {
        formDao = new DbFormDao();
        studentDao = new DbStudentDao();
        generateReportsService = new GenerateReportsService(formDao, studentDao);
        generateReportsService.createReport("e215947f-4d73-4726-baaf-dbec6258968f", 1, 1,10);

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