package com.cab302.teachscope.util;

import com.cab302.teachscope.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

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
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle(title);
        stage.show();
    }
}
