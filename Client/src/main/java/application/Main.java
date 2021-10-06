package application;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(new Scene(new Pane(), 800, 600));
            primaryStage.show();
//            AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
//            Scene scene = new Scene(root,1000,600);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//            primaryStage.getIcons().add(new Image("application/MainIcon.png"));
//            primaryStage.setTitle("Access control system");
//            primaryStage.setScene(scene);
//            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}