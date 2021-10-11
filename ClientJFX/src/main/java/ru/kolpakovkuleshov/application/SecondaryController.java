package ru.kolpakovkuleshov.application;

import java.io.IOException;
import javafx.fxml.FXML;
import ru.kolpakovkuleshov.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}