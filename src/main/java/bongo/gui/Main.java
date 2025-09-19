package bongo.gui;

import bongo.Bongo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Bongo using FXML.
 */
public class Main extends Application {

    private Bongo bongo = new Bongo();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBongo(bongo);  // inject the Bongo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
