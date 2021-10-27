package ru.kolpakovkuleshov.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kolpakovkuleshov.App;
import ru.kolpakovkuleshov.helpfulClasses.Logs;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class FirstController {


    @FXML
    private Button start_path;

    @FXML
    private TextField textFieldPath;

    @FXML
    private Button exit_path;

    @FXML
    void initialize() {

        start_path.setOnAction(event->{
            File f = new File(textFieldPath.getText());
            Scanner scaner = null;
            StringBuilder classstr=new StringBuilder();
            try {
                scaner = new Scanner(f);
                while(scaner.hasNextLine()){
                    String line = scaner.nextLine();
                    classstr.append(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(ProcessData.IsValidClassFromFile(classstr.toString()) && !(textFieldPath.getText().trim().equals(""))){
                ProcessData.f = f;
                openNewScene(start_path,"primary");
                System.out.println("File good");
            }else{
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERROR");
                a1.setContentText("File not good!");
                a1.setHeaderText("Problem with file!");
                a1.show();
                //System.out.println("File not good");
            }
        });

        exit_path.setOnAction(event -> {
            System.exit(0);
        });
    }

    protected void openNewScene(Button button, String scene){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(scene+ ".fxml"));
            Parent root = null;
            root = fxmlLoader.load();
            Stage window=(Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
