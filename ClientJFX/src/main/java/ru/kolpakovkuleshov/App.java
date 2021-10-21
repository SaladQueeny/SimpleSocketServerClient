package ru.kolpakovkuleshov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.kolpakovkuleshov.helpfulClasses.Logs;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 650);
        stage.setTitle("Killer");
        stage.setScene(scene);
        String path = "src/main/resources/icon/icon.png";
        File fileIcon = new File(path);
        Image applicationIcon = new Image(fileIcon.toURI().toString());
        stage.getIcons().add(applicationIcon);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Logs.writeLog(App.class, new Throwable().getStackTrace()[0].getMethodName(),
                "launch application", Level.INFO, true);
        launch();
    }

}