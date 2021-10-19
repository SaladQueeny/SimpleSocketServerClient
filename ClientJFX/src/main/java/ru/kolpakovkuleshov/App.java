package ru.kolpakovkuleshov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 650);
        //stage.getIcons().add(new Image("src/main/java/ru/kolpakovkuleshov/icon/icon.png"));
        stage.setTitle("Killer");
        stage.setScene(scene);
        String path = "src/main/resources/icon/icon.png";//icon
        File fileIcon = new File(path);//icon
        Image applicationIcon = new Image(fileIcon.toURI().toString());//icon
        stage.getIcons().add(applicationIcon);//icon
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
        launch();
    }

}