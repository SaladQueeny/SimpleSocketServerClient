package ru.kolpakovkuleshov.application;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import ru.kolpakovkuleshov.App;
import ru.kolpakovkuleshov.helpfulClasses.Generalities;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

public class PrimaryController  {
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

    private boolean isGoodData(){
        Pattern p = Pattern.compile("(\\d+)([.]*)(\\d*)");
        Matcher match = p.matcher(xstart.getText().trim());
        int check =0;
        if(match.matches()){
            check++;
            match = p.matcher(xend.getText().trim());
            if(match.matches()){
                check++;
                match = p.matcher(xchange.getText().trim());
                if(match.matches()){
                    check++;
                    match = p.matcher(ystart.getText().trim());
                    if(match.matches()){
                        check++;
                        match = p.matcher(yend.getText().trim());
                        if(match.matches()){
                            check++;
                            match = p.matcher(ychange.getText().trim());
                            if(match.matches()){
                                check++;
                                match = p.matcher(tstart.getText().trim());
                                if(match.matches()){
                                    check++;
                                    match = p.matcher(tend.getText().trim());
                                    if(match.matches()){
                                        check++;
                                        match = p.matcher(ychange.getText().trim());
                                        if(match.matches()){
                                            check++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(check==9){
            return true;
        }else{
            return false;
        }
    }

    private void getStartData(){
        ProcessData.getStartData(Double.parseDouble(xstart.getText().trim()), Double.parseDouble(xend.getText().trim()), Double.parseDouble(xchange.getText().trim()),
                                Double.parseDouble(ystart.getText().trim()), Double.parseDouble(yend.getText().trim()), Double.parseDouble(ychange.getText().trim()),
                                Double.parseDouble(tstart.getText().trim()), Double.parseDouble(tend.getText().trim()), Double.parseDouble(tchange.getText().trim()));
    }

    @FXML
    void initialize() {
        ////////////////////////////////////////////////////////////////////
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
        ////////////////////////////////////////////////////////////////////

        start_button.setOnAction(event->{

            if(isGoodData()){
                getStartData();
                try {
                    sendRequestToServer();
                } catch (IOException e) {
                    System.out.println("Something wrong with server");
                    e.printStackTrace();
                }
                System.out.println("We got uor data");
            }else{
                System.out.println("Null");
            }

        });

        exit_button.setOnAction(event->{
            System.exit(0);
        });


    }

}
