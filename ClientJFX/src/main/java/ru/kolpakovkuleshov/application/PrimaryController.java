package ru.kolpakovkuleshov.application;

import java.io.IOException;


import javafx.fxml.FXML;
import ru.kolpakovkuleshov.App;
import ru.kolpakovkuleshov.helpfulClasses.Generalities;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        try(Generalities generalities = new Generalities("127.0.0.1", 8080)){
            System.out.println("Connected to server");
            generalities.writeLine(ProcessData.createRequest());
            String response = generalities.readLine();
            System.out.println("Response: "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.setRoot("secondary");
    }
}
