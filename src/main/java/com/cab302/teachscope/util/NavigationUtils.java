package com.cab302.teachscope.util;

import com.cab302.teachscope.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class NavigationUtils {
    /**
     * Method to redirect to a new page in the application.
     * @param stage The application window.
     * @param page The page to redirect to.
     * @param title The value to set the title of the page.
     * @throws IOException On failed redirect.
     */
    public static void navigateTo(Stage stage, String page, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/" + page + ".fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        Platform.runLater(() -> root.requestFocus());
    }

    /**
     * Open a PDF at a specified path
     * @param path Path to the PDF file
     */
    public static void openPDF(String path) {
        try {
            // Load the PDF
            String projectRoot = System.getProperty("user.dir");
            File resource = new File(projectRoot, path);

            if (!resource.exists()) {
                System.out.println("PDF not found at: " + path);
                return;
            }

            Desktop.getDesktop().open(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
