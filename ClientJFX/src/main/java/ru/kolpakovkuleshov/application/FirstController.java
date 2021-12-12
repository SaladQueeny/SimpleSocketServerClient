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
import ru.kolpakovkuleshov.helpfulClasses.Generalities;
import ru.kolpakovkuleshov.helpfulClasses.Logs;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;

public class FirstController {

    @FXML
    private TextField textFieldClassName;

    @FXML
    private TextField IPtextField;

    @FXML
    private TextField PORTtextField;

    @FXML
    private Button start_path;

    @FXML
    private TextField textFieldPath;

    @FXML
    private Button exit_path;

    @FXML
    void initialize() {
        start_path.setOnAction(event -> {
            File f = new File(textFieldPath.getText());
            StringBuilder classstr = new StringBuilder();
            try {
                Scanner scaner = new Scanner(f);
                while (scaner.hasNextLine()) {
                    String line = scaner.nextLine();
                    classstr.append(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            boolean isValidTextFieldPath = !(textFieldPath.getText().trim().equals(""));
            boolean isValidTextFieldClassName = !(textFieldClassName.getText().trim().equals(""));
            String className = textFieldClassName.getText().trim();
            boolean isValidClass = ProcessData.IsValidClassFromFile(classstr.toString(), className);
            boolean isValidServer = sendRequestToServer();

            if (isValidClass && isValidTextFieldClassName && isValidTextFieldPath) {

                if(isValidServer){
                    ProcessData.f = f;
                    ProcessData.className = className;
                    System.out.println("File good");
                    openNewScene(start_path, "primary");
                } else{
                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setTitle("ERROR");
                    a1.setContentText("Server unavailable");
                    a1.setHeaderText("Problem with connection!");
                    a1.show();
                }

            } else {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERROR");
                a1.setContentText("File or ClassName is not good!");
                a1.setHeaderText("Problem with file!");
                a1.show();
            }
        });

        exit_path.setOnAction(event -> {
            try {
                Files.walk(Paths.get(""), 1)
                        .forEach(file -> {
                            if (file.toFile().isFile() && file.toFile().getPath().endsWith(".class")) {
                                System.out.println(file.getFileName());
                                file.toFile().delete();
                                System.out.println("deleted");
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
    }

    private boolean sendRequestToServer() {
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "Send request to server", Level.INFO, true);

        try (Generalities generalities = new Generalities("127.0.0.1", 8000)) {
            generalities.writeLine("check connection");
            String response = generalities.readLine();
            if (response.equals("connection good")) {
                return true;
            }
            Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                    "Get correct data from server", Level.INFO, true);
        } catch (IOException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setTitle("ERROR");
            a1.setContentText("Can't join to server!");
            a1.setHeaderText("Server error!");
            a1.show();
            e.printStackTrace();
            Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                    "Can't join to server", Level.SEVERE, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    protected void openNewScene(Button button, String scene) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(scene + ".fxml"));
            Parent root = null;
            root = fxmlLoader.load();
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
