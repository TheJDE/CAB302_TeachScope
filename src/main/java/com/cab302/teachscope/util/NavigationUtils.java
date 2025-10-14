package com.cab302.teachscope.util;

import com.cab302.teachscope.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

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
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setTitle(title);
        stage.show();
    }

    public static void openKnowledgeBasePDF(){
            try {
                // Load the KnowledgeBase PDF, located in the resources folder of the application
                URL resource = com.cab302.teachscope.util.NavigationUtils.class.getResource("/images/knowledge_base_final.pdf");
                if (resource == null) {
                    System.out.println("PDF not found in resources folder!");
                    return;
                }
                // Create a temporary file, from the pdf in our resources folder to open
                File knowledgeBaseTempFile = File.createTempFile("knowledgeBase", ".pdf");
                try (InputStream in = resource.openStream()) {
                    Files.copy(in, knowledgeBaseTempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                // open this temporary file
                Desktop.getDesktop().open(knowledgeBaseTempFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void openIntroductoryTutorial() {
        try {
            // Load the Introductory Tutorial PDF, located in the resources folder of the application
            URL resource = com.cab302.teachscope.util.NavigationUtils.class.getResource("/images/user_introductory_tutorial.pdf");
            if (resource == null) {
                System.out.println("PDF not found in resources folder!");
                return;
            }
            // Create a temporary file, from the pdf in our resources folder to open
            File introductoryTutorialTempFile = File.createTempFile("introductoryTutorial", ".pdf");
            try (InputStream in = resource.openStream()) {
                Files.copy(in, introductoryTutorialTempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            // open this temporary file
            Desktop.getDesktop().open(introductoryTutorialTempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
