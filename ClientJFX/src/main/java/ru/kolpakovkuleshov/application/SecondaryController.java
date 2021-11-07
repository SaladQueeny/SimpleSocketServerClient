package ru.kolpakovkuleshov.application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SecondaryController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button change_path_button;

    @FXML
    private Pane chart_pain;

    @FXML
    private Button exit_button;

    @FXML
    private Slider slider_chart;

    @FXML
    private Button start_button;

    @FXML
    private TextField tchange;

    @FXML
    private TextField tend;

    @FXML
    private TextField tstart;

    @FXML
    private Button update_button;

    @FXML
    private TextField xchange;

    @FXML
    private TextField xend;

    @FXML
    private TextField xstart;

    @FXML
    private TextField ychange;

    @FXML
    private TextField yend;

    @FXML
    private TextField ystart;

    @FXML
    void initialize() {
        start_button.setDisable(false);

    }

}