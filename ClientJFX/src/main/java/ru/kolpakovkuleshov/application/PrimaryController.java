package ru.kolpakovkuleshov.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import ru.kolpakovkuleshov.App;
import ru.kolpakovkuleshov.helpfulClasses.Generalities;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

public class PrimaryController implements Initializable {
    @FXML
    private BubbleChart bubbleChart;

    @FXML
    private Button exit_button;

    @FXML
    private ScrollBar scrollbar;

    @FXML
    private Button start_button;

    @FXML
    private TextField tchange;

    @FXML
    private TextField tend;

    @FXML
    private TextField tstart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private TextField xchange;

    @FXML
    private TextField xend;

    @FXML
    private TextField xstart;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField ychange;

    @FXML
    private TextField yend;

    @FXML
    private TextField ystart;
    private void sendRequestToServer() throws IOException {
        try(Generalities generalities = new Generalities("127.0.0.1", 8080)){
            System.out.println("Connected to server");
            generalities.writeLine(ProcessData.createRequest());
            String response = generalities.readLine();
            System.out.println("Response: "+response);
            ProcessData.getDataFromJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.setRoot("secondary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<Integer, Double> series2020 = new XYChart.Series<>();
        series2020.setName("2020");

        series2020.getData().add(new XYChart.Data<>(14, 12.2, 1.5));



        XYChart.Series<Integer, Double> series2021 = new XYChart.Series<>();
        series2021.setName("2021");

        series2021.getData().add(new XYChart.Data<>(4, 1.2, 8.5));

        XYChart.Series<Integer, Double> series2022 = new XYChart.Series<>();
        series2022.setName("2022");

        series2022.getData().add(new XYChart.Data<>(1, 16.2, 2.5));

        bubbleChart.getData().addAll(series2020, series2021, series2022);
    }
}
